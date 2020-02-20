package edu.byu.cs.tweeter.view.main.status;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.net.request.StatusRequest;
import edu.byu.cs.tweeter.net.response.StatusResponse;
import edu.byu.cs.tweeter.presenter.StatusPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.LoadImageTask;
import edu.byu.cs.tweeter.view.asyncTasks.PostStatusTask;
import edu.byu.cs.tweeter.view.cache.ImageCache;

public class StatusActivity extends Activity implements  LoadImageTask.LoadImageObserver, StatusPresenter.View, PostStatusTask.PostStatusObserver {

    private User user;
    private Button shareButton;
    private ImageView userImage;
    private TextView userName;
    private ImageButton closeButton;
    private TextView count;
    private TextView warning;
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

        getWindow().setLayout((int)(width *.8), (int)( height*.75));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        count = findViewById(R.id.count);
        count.setText("0");

        warning = findViewById(R.id.warning);

        shareButton = findViewById(R.id.share);
        shareButton.setText(R.string.share);
        shareButton.setEnabled(false);

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
        editMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.checkStatus(s.toString());
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
            userImage.setImageDrawable(drawables[0]);
        }
    }

    private void share(){
        Status status = new Status(user, editMessage.getText().toString());
        PostStatusTask postStatusTask = new PostStatusTask(presenter, this);
        StatusRequest request = new StatusRequest(user, status);
        postStatusTask.execute(request);
    }

    @Override
    public void statusRetrieved(StatusResponse response) {
        if(response.getMessage().equals("Status posted")){
            finish();
        }
        else{
            Toast.makeText(getBaseContext(), "Could not share post.", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void updateCounts(String chars) {
        count.setText(chars);
    }

    @Override
    public void handleEditing(String error) {
        if(error == null){
            warning.setText("");
        } else {
            warning.setText(error);
        }
    }

    @Override
    public void enableShare(boolean enable) {
        shareButton.setEnabled(enable);
    }
}
