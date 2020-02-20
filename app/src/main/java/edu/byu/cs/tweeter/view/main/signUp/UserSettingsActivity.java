package edu.byu.cs.tweeter.view.main.signUp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.SignUpPresenter;

public class UserSettingsActivity extends AppCompatActivity implements SignUpPresenter.View {

    private ImageView profileImage;
    private EditText firstName;
    private EditText lastName;
    private Button signUpButton;

    private SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        presenter = new SignUpPresenter(this);

        profileImage = findViewById(R.id.profileImage);
        profileImage.setImageResource(R.drawable.twitter);

        firstName = findViewById(R.id.firstName);
        firstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.updateFirstName(s.toString());
            }
        });

        lastName = findViewById(R.id.lastName);
        lastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.updateLastName(s.toString());
            }
        });

        signUpButton = findViewById(R.id.signUpButton);
        signUpButton.setEnabled(false);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

    }

    private void signUp(){

    }

    @Override
    public void enableButton(Boolean enabled) {
        signUpButton.setEnabled(enabled);
    }

    @Override
    public void setPasswordError(String error) {

    }

    @Override
    public void setHandleError(String error) {

    }

    @Override
    public void setHandleFirst(String error) {
        firstName.setError(error);
    }

    @Override
    public void setHandleLast(String error) {
        lastName.setError(error);
    }
}
