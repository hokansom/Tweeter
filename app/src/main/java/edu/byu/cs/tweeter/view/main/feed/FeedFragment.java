package edu.byu.cs.tweeter.view.main.feed;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FeedRequest;
import edu.byu.cs.tweeter.model.service.response.FeedResponse;
import edu.byu.cs.tweeter.presenter.feed.FeedPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetFeedTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.profile.ProfileActivity;

public class FeedFragment extends Fragment implements FeedPresenter.View{
    private static final int LOADING_DATA_VIEW = 0;

    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private FeedPresenter presenter;

    private FeedRecyclerViewAdapter feedRecyclerViewAdapter;

    private TextView noData;

    private SwipeRefreshLayout swipeContainer;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        presenter = new FeedPresenter(this);

        RecyclerView feedRecyclerView = view.findViewById(R.id.feedRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        feedRecyclerView.setLayoutManager(layoutManager);

        feedRecyclerViewAdapter = new FeedRecyclerViewAdapter();
        feedRecyclerView.setAdapter(feedRecyclerViewAdapter);

        feedRecyclerView.addOnScrollListener(new FeedRecyclerViewPaginationScrollListener(layoutManager));

        noData = view.findViewById(R.id.noData);

        swipeContainer = view.findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                feedRecyclerViewAdapter.lastStatus = null;
                feedRecyclerViewAdapter.removeAll();
                feedRecyclerViewAdapter.loadMoreItems();
            }
        });

        return view;
    }

    @Override
    public void displayNoData(int visible) {
        noData.setVisibility(visible);
    }

    private class FeedHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private final TextView statusMessage;
        private final TextView statusDate;

        FeedHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);
            statusMessage = itemView.findViewById(R.id.statusMessage);
            statusDate = itemView.findViewById(R.id.statusDate);
        }

        void bindStatus(Status status) {
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(status.getAuthor()));
            userAlias.setText(addSingleSpan(status.getAuthor().getAlias()));
            userAlias.setMovementMethod(LinkMovementMethod.getInstance());
            userName.setText(status.getAuthor().getName());
            statusDate.setText(status.getPublishDate());
            statusMessage.setText(addSpans(status.getMessage(), status));
            statusMessage.setMovementMethod(LinkMovementMethod.getInstance());

        }

        SpannableString addSpans(String message, Status status){
            SpannableString string = new SpannableString(message);
            List<String> aliases =  status.getMentions().getUserMentions();
            for(String s: aliases){
                int start = findStartingIndex(message, s);
                if(start != -1){
                    int end = start + s.length();
                    string.setSpan(new AliasClickableSpan(s), start, end , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }


            List<String> urls = status.getUrls().getUris();
            for(String s: urls){
                int start = findStartingIndex(message, s);
                if(start != -1){
                    int end = start + s.length();
                    string.setSpan( new UrlClickableSpan(s), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
            return string;
        }

        SpannableString addSingleSpan(String s){
            SpannableString string = new SpannableString(s);
            string.setSpan(new AliasClickableSpan(s), 0, s.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return string;
        }

        int findStartingIndex(String s, String substring){
            return s.indexOf(substring);
        }

    }


    private class AliasClickableSpan extends ClickableSpan{
        String text;

        public AliasClickableSpan(String text){
            super();
            this.text = text;
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra("ALIAS", text);
            startActivity(intent);
        }
    }

    private class UrlClickableSpan extends ClickableSpan{
        String text;

        public UrlClickableSpan(String text){
            super();
            this.text = text;
        }

        @Override
        public void onClick(View widget) {
            try{
                Uri uri = Uri.parse(text);
                Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(launchBrowser);
            } catch (ActivityNotFoundException ex){
                /*If it fails at first, try prepending http:// to the call*/
                String updated = "http://" + text;
                try{
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(updated));
                    startActivity(launchBrowser);
                } catch (ActivityNotFoundException exception){
                    String message = String.format("Could not open url $s", text);
                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                }

            }

        }
    }


    private class FeedRecyclerViewAdapter extends RecyclerView.Adapter<FeedHolder> implements GetFeedTask.GetFeedObserver {

        private final List<Status> statuses = new ArrayList<>();

        private Status lastStatus;

        private boolean hasMorePages;
        private boolean isLoading = false;

        FeedRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<Status> newStatuses) {
            int startInsertPosition = statuses.size();
            statuses.addAll(newStatuses);
            this.notifyItemRangeInserted(startInsertPosition, newStatuses.size());
        }

        void addItem(Status status) {
            statuses.add(status);
            this.notifyItemInserted(statuses.size() - 1);
        }

        void removeItem(Status status) {
            int position = statuses.indexOf(status);
            statuses.remove(position);
            this.notifyItemRemoved(position);
        }

        void removeAll(){
            statuses.clear();
            this.notifyDataSetChanged();
        }

        @NonNull
        @Override
        public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FeedFragment.this.getContext());
            View view;

            if(isLoading) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else if(statuses.size() == 0){
                view = layoutInflater.inflate(R.layout.no_data_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.status_row, parent, false);
            }

            return new FeedHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FeedHolder followingHolder, int position) {
            if(!isLoading) {
                followingHolder.bindStatus(statuses.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return statuses.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == statuses.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }


        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFeedTask getFeedTask = new GetFeedTask(presenter, this);
            FeedRequest request = new FeedRequest(presenter.getViewingUser(), PAGE_SIZE, lastStatus);
            getFeedTask.execute(request);
        }


        @Override
        public void feedRetrieved(FeedResponse feedResponse) {
            swipeContainer.setRefreshing(false);
            List<Status> statuses = feedResponse.getFeed().getFeed();
            presenter.updateNumStatuses(statuses.size());

            lastStatus = (statuses.size() > 0) ? statuses.get(statuses.size() -1) : null;
            hasMorePages = feedResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            feedRecyclerViewAdapter.addItems(statuses);
        }

        private void addLoadingFooter() {
            User temp = new User("Dummy", "User", "");
            addItem(new Status(temp, "Dummy"));
        }

        private void removeLoadingFooter() {
            removeItem(statuses.get(statuses.size() - 1));
        }

        @Override
        public void handleException(Exception throwable) {
            Log.e("", throwable.getMessage(), throwable);
            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class FeedRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FeedRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!feedRecyclerViewAdapter.isLoading && feedRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    feedRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }



}
