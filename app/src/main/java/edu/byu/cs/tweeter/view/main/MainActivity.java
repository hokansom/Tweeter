package edu.byu.cs.tweeter.view.main;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.presenter.MainPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;
import edu.byu.cs.tweeter.view.main.profile.ProfileActivity;
import edu.byu.cs.tweeter.view.main.search.SearchActivity;
import edu.byu.cs.tweeter.view.main.status.StatusActivity;

public class MainActivity extends AppCompatActivity implements LoadImageTask.LoadImageObserver, MainPresenter.View {

    private MainPresenter presenter;
    private User user;
    private ImageView userImageView;
    private TextView userAlias;
    private TextView userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewStatus();
            }
        });

        userImageView = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        userAlias = findViewById(R.id.userAlias);

        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               showPopup(v);
            }
        });

        user = presenter.getCurrentUser();

        // Asynchronously load the user's image
        LoadImageTask loadImageTask = new LoadImageTask(this);
        if(presenter.getCurrentUser() != null){
            loadImageTask.execute(presenter.getCurrentUser().getImageUrl());
            userName.setText(user.getName());
            userAlias.setText(user.getAlias());
        }
        else{
            userImageView.setImageResource(R.drawable.profile_default);
            userName.setText("");
            userAlias.setText("");
        }

        ImageView searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToSearch();
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

    public void showPopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.main_menu, popup.getMenu());

        Menu menu = popup.getMenu();

        if(presenter.getCurrentUser() != null){
            menu.getItem(1).setTitle(R.string.menu_logout);
        }
        else{
            menu.getItem(1).setTitle(R.string.menu_login);
        }

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.menu_profile:
                        switchToProfile();

                        return true;
                    case R.id.menu_log:
                        if(presenter.getCurrentUser() != null){
                            presenter.signOutUser();
                        }
                        else{
                            switchToSignIn();
                        }
                        return true;
                    default:
                        return false;
                }
            }
        });

        popup.show();
    }


    private void createNewStatus(){
        if(presenter.getCurrentUser() !=  null){
            Intent intent = new Intent(this, StatusActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(getBaseContext(), R.string.mustLogIn, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void clearData(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void switchToSignIn(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

    private void switchToProfile(){
        if(presenter.getCurrentUser() != null){
            presenter.setViewingUser(null);
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(getBaseContext(), R.string.mustLogInProfile, Toast.LENGTH_LONG).show();
        }

    }

    private void switchToSearch(){
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateUserData() {
        userAlias.setText("");
        userName.setText("");
        userImageView.setImageResource(R.drawable.profile_default);
    }
}