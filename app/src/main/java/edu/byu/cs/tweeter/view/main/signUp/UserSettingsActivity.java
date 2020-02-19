package edu.byu.cs.tweeter.view.main.signUp;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import edu.byu.cs.tweeter.R;

public class UserSettingsActivity extends AppCompatActivity {

    private ImageView profileImage;
    private static String alias;
    private static String password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        Bundle extras = getIntent().getExtras();
        alias = extras.getString("ALIAS");
        password = extras.getString("PASSWORD");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        profileImage = findViewById(R.id.profileImage);
        profileImage.setImageResource(R.drawable.twitter);

    }
}
