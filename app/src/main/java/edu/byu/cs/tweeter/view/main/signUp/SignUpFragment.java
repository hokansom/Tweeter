package edu.byu.cs.tweeter.view.main.signUp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.SignInPresenter;
import edu.byu.cs.tweeter.presenter.SignUpPresenter;

public class SignUpFragment extends Fragment implements SignUpPresenter.View {

    private SignUpPresenter presenter;
    private EditText editHandle;
    private EditText editPassword;
    private Button signUpButton;
    private Switch showPasswordSwitch;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        presenter = new SignUpPresenter(this);

        editHandle = view.findViewById(R.id.input_alias);
        editHandle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.updateHandle(s.toString());
            }
        });

        editPassword = view.findViewById(R.id.input_password);
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                presenter.updatePassword(s.toString());
            }
        });

        showPasswordSwitch = view.findViewById(R.id.switchPassword);
        showPasswordSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePasswordVisibility();
            }
        });

        signUpButton = view.findViewById(R.id.btn_login);
        signUpButton.setEnabled(false);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), UserSettingsActivity.class);
                intent.putExtra("ALIAS", editHandle.getText().toString());
                intent.putExtra("PASSWORD", editPassword.getText().toString());
                startActivity(intent);
            }
        });

        return view;
    }

    private void updatePasswordVisibility(){
        if(showPasswordSwitch.isChecked()){
            editPassword.setTransformationMethod(null);
        } else{
            editPassword.setTransformationMethod( new PasswordTransformationMethod());
        }
    }

    @Override
    public void enableButton(Boolean enabled) {
        signUpButton.setEnabled(enabled);
    }

    @Override
    public void setPasswordError(String error) {
        editPassword.setError(error);
    }

    @Override
    public void setHandleError(String error) {
        editHandle.setError(error);
    }

    @Override
    public void setHandleFirst(String error) {

    }

    @Override
    public void setHandleLast(String error) {

    }
}
