package edu.byu.cs.tweeter.client.view.main.following;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.service.request.FollowingRequest;
import edu.byu.cs.tweeter.model.service.response.FollowingResponse;
import edu.byu.cs.tweeter.client.presenter.following.FollowingPresenter;
import edu.byu.cs.tweeter.client.view.asyncTasks.GetFollowingTask;
import edu.byu.cs.tweeter.client.view.cache.ImageCache;
import edu.byu.cs.tweeter.client.view.main.profile.ProfileActivity;

public class FollowingFragment extends Fragment implements FollowingPresenter.View {

    private static final int LOADING_DATA_VIEW = 0;
    private static final int ITEM_VIEW = 1;

    private static final int PAGE_SIZE = 10;

    private TextView noData;

    private SwipeRefreshLayout swipeContainer;

    private FollowingPresenter presenter;

    private FollowingRecyclerViewAdapter followingRecyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_following, container, false);

        presenter = new FollowingPresenter(this);

        RecyclerView followingRecyclerView = view.findViewById(R.id.followingRecyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        followingRecyclerView.setLayoutManager(layoutManager);

        followingRecyclerViewAdapter = new FollowingRecyclerViewAdapter();
        followingRecyclerView.setAdapter(followingRecyclerViewAdapter);

        followingRecyclerView.addOnScrollListener(new FollowRecyclerViewPaginationScrollListener(layoutManager));

        noData = view.findViewById(R.id.noData);

        swipeContainer = view.findViewById(R.id.swipe_container);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                followingRecyclerViewAdapter.lastFollowee = null;
                followingRecyclerViewAdapter.removeAll();
                followingRecyclerViewAdapter.loadMoreItems();
            }
        });

        return view;
    }

    @Override
    public void displayNoData(int visibility) {
        noData.setVisibility(visibility);
    }

    private class FollowingHolder extends RecyclerView.ViewHolder {

        private final ImageView userImage;
        private final TextView userAlias;
        private final TextView userName;
        private User tempUser;

        FollowingHolder(@NonNull View itemView) {
            super(itemView);

            userImage = itemView.findViewById(R.id.userImage);
            userAlias = itemView.findViewById(R.id.userAlias);
            userName = itemView.findViewById(R.id.userName);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    switchToProfile();
                }
            });
        }

        void bindUser(User user) {
            userImage.setImageDrawable(ImageCache.getInstance().getImageDrawable(user));
            String alias = "@" + user.getAlias();
            userAlias.setText(alias);
            userName.setText(user.getName());
            tempUser = user;
        }

        private void switchToProfile(){
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            intent.putExtra("ALIAS", tempUser.getAlias());
            startActivity(intent);
        }


    }

    private class FollowingRecyclerViewAdapter extends RecyclerView.Adapter<FollowingHolder> implements GetFollowingTask.GetFolloweesObserver {

        private final List<User> users = new ArrayList<>();

        private User lastFollowee;

        private boolean hasMorePages;
        private boolean isLoading = false;

        FollowingRecyclerViewAdapter() {
            loadMoreItems();
        }

        void addItems(List<User> newUsers) {
            int startInsertPosition = users.size();
            users.addAll(newUsers);
            this.notifyItemRangeInserted(startInsertPosition, newUsers.size());
        }

        void addItem(User user) {
            users.add(user);
            this.notifyItemInserted(users.size() - 1);
        }

        void removeItem(User user) {
            int position = users.indexOf(user);
            users.remove(position);
            this.notifyItemRemoved(position);
        }

        void removeAll(){
            users.clear();
            this.notifyDataSetChanged();
        }

        @NonNull
        @Override
        public FollowingHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(FollowingFragment.this.getContext());
            View view;

            if(isLoading) {
                view =layoutInflater.inflate(R.layout.loading_row, parent, false);

            } else if(users.size() == 0){
                view = layoutInflater.inflate(R.layout.no_data_row, parent, false);

            } else {
                view = layoutInflater.inflate(R.layout.user_row, parent, false);
            }

            return new FollowingHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FollowingHolder followingHolder, int position) {
            if(!isLoading) {
                followingHolder.bindUser(users.get(position));
            }
        }

        @Override
        public int getItemCount() {
            return users.size();
        }

        @Override
        public int getItemViewType(int position) {
            return (position == users.size() - 1 && isLoading) ? LOADING_DATA_VIEW : ITEM_VIEW;
        }


        void loadMoreItems() {
            isLoading = true;
            addLoadingFooter();

            GetFollowingTask getFollowingTask = new GetFollowingTask(presenter, this);
            FollowingRequest request = new FollowingRequest(presenter.getViewingUser(), PAGE_SIZE, lastFollowee);
            getFollowingTask.execute(request);
        }

        @Override
        public void followeesRetrieved(FollowingResponse followingResponse) {
            swipeContainer.setRefreshing(false);
            List<User> followees = new ArrayList<>();
            if(followingResponse.getFollowees() != null ){
                followees = followingResponse.getFollowees();
                presenter.updateNumFollowees(followingResponse.getFollowees().size());
            } else{
                presenter.updateNumFollowees(0);
            }


            lastFollowee = (followees.size() > 0) ? followees.get(followees.size() -1) : null;
            hasMorePages = followingResponse.getHasMorePages();

            isLoading = false;
            removeLoadingFooter();
            followingRecyclerViewAdapter.addItems(followees);
        }

        private void addLoadingFooter() {
            addItem(new User("Dummy", "User", ""));
        }

        private void removeLoadingFooter() {
            removeItem(users.get(users.size() - 1));
        }

        @Override
        public void handleException(Exception throwable) {
            Log.e("", throwable.getMessage(), throwable);
            Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private class FollowRecyclerViewPaginationScrollListener extends RecyclerView.OnScrollListener {

        private final LinearLayoutManager layoutManager;

        FollowRecyclerViewPaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(@NotNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!followingRecyclerViewAdapter.isLoading && followingRecyclerViewAdapter.hasMorePages) {
                if ((visibleItemCount + firstVisibleItemPosition) >=
                        totalItemCount && firstVisibleItemPosition >= 0) {
                    followingRecyclerViewAdapter.loadMoreItems();
                }
            }
        }
    }
}
