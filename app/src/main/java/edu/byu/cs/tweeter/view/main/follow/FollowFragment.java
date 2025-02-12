package edu.byu.cs.tweeter.view.main.follow;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Follow;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.FollowRequest;
import edu.byu.cs.tweeter.net.response.FollowResponse;
import edu.byu.cs.tweeter.presenter.follow.FollowPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.PostFollowTask;

public class FollowFragment extends Fragment implements FollowPresenter.View, PostFollowTask.PostFollowObserver {

    private Button followButton;
    private FollowPresenter presenter;
    private User user;

    private boolean isFollowing;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_follow, container, false);

        presenter = new FollowPresenter(this);

        user = presenter.getViewingUser();

        followButton = view.findViewById(R.id.followButton);

        if(presenter.getCurrentUser() == null || presenter.getCurrentUser().equals(user)){
            followButton.setVisibility(View.INVISIBLE);
        }else{
            followButton.setVisibility(View.VISIBLE);
        }

        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postFollow();
            }
        });

        isFollowing = presenter.following;

        if(isFollowing){
            followButton.setText(R.string.unfollow);
        } else{
            followButton.setText(R.string.follow);
        }

        return view;
    }



    @Override
    public void followRetrieved(FollowResponse response) {

        if(response.isSuccess()){
            presenter.updateFollowing();
        } else {
            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void updateFollowButton(String text){
        followButton.setText(text);
    }

    private void postFollow(){
        User follower = presenter.getCurrentUser();
        User followee = presenter.getViewingUser();
        Follow follow = new Follow(follower, followee);
        boolean isFollowRequest = presenter.isFollowingRequest();
        //If they are already following, then it is an unfollow request;
        //If they aren't following, then it is a follow request;

        PostFollowTask task = new PostFollowTask(presenter, this);
        FollowRequest request = new FollowRequest(follow, isFollowRequest);
        task.execute(request);
    }
}
