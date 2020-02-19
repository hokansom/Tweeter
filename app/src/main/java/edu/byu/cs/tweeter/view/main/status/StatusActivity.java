package edu.byu.cs.tweeter.view.main.status;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.presenter.StatusPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;

public class StatusActivity extends Activity implements  LoadImageTask.LoadImageObserver,StatusPresenter.View {

    private User user;
    private TextView shareButton;
    private ImageView userImage;
    private TextView userName;
    private ImageButton closeButton;

    private EditText editMessage;

    private StatusPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_status);

        presenter = new StatusPresenter(this);

        user = presenter.getCurrentUser();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width *.8), (int)( height*.5));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        shareButton = findViewById(R.id.share);
        shareButton.setText(R.string.share);
        shareButton.setVisibility(View.VISIBLE);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        userImage = findViewById(R.id.userImage);

        // Asynchronously load the user's image
        LoadImageTask loadImageTask = new LoadImageTask(this);
        loadImageTask.execute(presenter.getCurrentUser().getImageUrl());

        userName = findViewById(R.id.userName);
        userName.setText(user.getName());

        closeButton = findViewById(R.id.closeButton);
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editMessage = findViewById(R.id.editMessage);
    }

    @Override
    public void imageLoadProgressUpdated(Integer progress) {
        // We're just loading one image. No need to indicate progress.
    }

    @Override
    public void imagesLoaded(Drawable[] drawables) {
        ImageCache.getInstance().cacheImage(user, drawables[0]);

        if(drawables[0] != null) {
            userImage.setImageDrawable(drawables[0]);
        }
    }

    private void share(){
        Toast.makeText(getBaseContext(), "Clicked share", Toast.LENGTH_LONG).show();
    }

}
