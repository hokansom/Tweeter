package edu.byu.cs.tweeter.view.main.profile;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.SearchRequest;
import edu.byu.cs.tweeter.net.response.SearchResponse;
import edu.byu.cs.tweeter.presenter.ProfilePresenter;
import edu.byu.cs.tweeter.presenter.search.SearchPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.follow.FollowFragment;
import edu.byu.cs.tweeter.view.main.status.StatusActivity;

public class ProfileActivity extends AppCompatActivity implements ProfilePresenter.View, LoadImageTask.LoadImageObserver, SearchPresenter.View, GetUserTask.GetUserObserver {

    private User user;
    private ImageView userImageView;
    private TextView userName;
    private TextView userAlias;

    private ProfilePresenter presenter;
    private SearchPresenter searchPresenter;

    private boolean isFollowing;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        presenter = new ProfilePresenter(this);
        searchPresenter = new SearchPresenter(this);


        ProfileSectionsPagerAdapter pagerAdapter = new ProfileSectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        userImageView = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        userAlias = findViewById(R.id.userAlias);


        try{
            Bundle extras = getIntent().getExtras();
            String alias = extras.getString("ALIAS");
            search(alias);
        }catch (Exception e){
            user = presenter.getViewingUser();
            updateUserData();
        }


//        if(presenter.getViewingUser() == null){
//            Bundle extras = getIntent().getExtras();
//            String alias = extras.getString("ALIAS");
//            search(alias);
//        } else {
//            user = presenter.getViewingUser();
//            updateUserData();
//        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewStatus();
            }
        });

        ImageView back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

    }

    private void goBack(){
        presenter.setViewingUser(null);
        finish();
    }



    private void createNewStatus(){
        if(presenter.getCurrentUser() != null){
            Intent intent = new Intent(this, StatusActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(getBaseContext(), R.string.mustLogIn, Toast.LENGTH_LONG).show();
        }

    }

    private void search(String alias){
        GetUserTask getUserTask = new GetUserTask(searchPresenter, this);
        SearchRequest request = new SearchRequest(alias, presenter.getCurrentUser());
        getUserTask.execute(request);
    }

    public void updateUserData(){
        loadImage();
        userName.setText(user.getName());
        userAlias.setText(user.getAlias());
        updateNumbers();
        initializeFragment();
    }

    private void loadImage(){
        // Asynchronously load the user's image
        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(presenter.getViewingUser().getImageUrl());
    }

    public void updateNumbers(){
        TextView numFolloweesText = findViewById(R.id.numFollowing);
        int numfollowees = presenter.getNumOfFollowees();
        numFolloweesText.setText(String.format("%d",numfollowees ));

        TextView numFollowersText = findViewById(R.id.numFollowers);
        int numFollowers = presenter.getNumOfFollowers();
        numFollowersText.setText(String.format("%d",numFollowers ));
    }


    @Override
    public void userRetrieved(SearchResponse response) {
        user = response.getUser();
        if(user == null){
            Toast.makeText(getBaseContext(), "User doesn't exist", Toast.LENGTH_LONG).show();
            kill_activity();
        } else {
            presenter.following = response.isFollowing();
            updateUserData();
        }
    }

    private void kill_activity(){
        finish();
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

    public boolean  isFollowing() { return isFollowing; }

    private void initializeFragment(){
        FollowFragment fragment = new FollowFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment).commit();
    }
}
