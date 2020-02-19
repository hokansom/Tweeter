package edu.byu.cs.tweeter.view.main.profile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.presenter.ActivityPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.status.StatusActivity;

public class ProfileActivity extends AppCompatActivity implements ActivityPresenter.View, LoadImageTask.LoadImageObserver {

    private User user;
    private ImageView back;
    private ImageView userImageView;
    private Button followButton;

    private ActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        presenter = new ActivityPresenter(this);

        user = presenter.getViewingUser();

        ProfileSectionsPagerAdapter pagerAdapter = new ProfileSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        userImageView = findViewById(R.id.userImage);

        // Asynchronously load the user's image
        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(presenter.getCurrentUser().getImageUrl());

        TextView userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(user.getAlias());

        updateNumbers();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewStatus();
            }
        });

        followButton = findViewById(R.id.followButton);
        //Don't show button if it is the current user
        if(presenter.getCurrentUser().equals(user)){
            followButton.setVisibility(View.INVISIBLE);
        }else{
            followButton.setVisibility(View.VISIBLE);
            //TODO: change text to follow/unfollow;

            followButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO: add functionality
                    Toast.makeText(getBaseContext(), "Follow clicked", Toast.LENGTH_LONG).show();
                }
            });
        }

        back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    @Override
    public void imageLoadProgressUpdated(Integer progress) {
        // We're just loading one image. No need to indicate progress.
    }

    @Override
    public void imagesLoaded(Drawable[] drawables) {
        ImageCache.getInstance().cacheImage(user, drawables[0]);

        if(drawables[0] != null) {
            userImageView.setImageDrawable(drawables[0]);
        }
    }

    private void goBack(){
        presenter.setViewingUser(null);
        finish();
    }

    public void updateNumbers(){
        TextView numFolloweesText = findViewById(R.id.numFollowing);
        int numfollowees = presenter.getNumOfFollowees();
        numFolloweesText.setText(String.format("%d",numfollowees ));

        TextView numFollowersText = findViewById(R.id.numFollowers);
        int numFollowers = presenter.getNumOfFollowers();
        numFollowersText.setText(String.format("%d",numFollowers ));
    }

    private void createNewStatus(){
        Intent intent = new Intent(this, StatusActivity.class);
        startActivity(intent);
    }

}
