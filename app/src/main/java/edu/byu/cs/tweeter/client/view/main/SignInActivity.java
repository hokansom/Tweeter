package edu.byu.cs.tweeter.client.view.main;

import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import edu.byu.cs.tweeter.R;

public class SignInActivity extends AppCompatActivity {

    private ImageView tweeterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        SignInPagerAdapter pagerAdapter = new SignInPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(pagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        tweeterImage = findViewById(R.id.tweeterImage);
        tweeterImage.setImageResource(R.drawable.twitter);
    }

}
