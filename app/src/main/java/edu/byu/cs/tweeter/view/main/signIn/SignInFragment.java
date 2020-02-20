package edu.byu.cs.tweeter.view.main.signIn;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.net.request.SignInRequest;
import edu.byu.cs.tweeter.net.response.SignInResponse;
import edu.byu.cs.tweeter.presenter.SignInPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.PostSignInTask;
import edu.byu.cs.tweeter.view.main.MainActivity;

public class SignInFragment extends Fragment implements SignInPresenter.View, PostSignInTask.PostSignInObserver {

    private SignInPresenter presenter;
    private EditText editHandle;
    private EditText editPassword;
    private Button signInButton;
    private TextView noAccount;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        presenter = new SignInPresenter(this);

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


        signInButton = view.findViewById(R.id.btn_login);
        signInButton.setEnabled(false);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        noAccount = view.findViewById(R.id.link_signup);
        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: implement a method for this
            }
        });

        return view;
    }

    @Override
    public void signInPosted(SignInResponse response) {
        if(response.getUser() != null){
            Intent intent = new Intent(getActivity(), MainActivity.class);
            Bundle extras = new Bundle();
            extras.putSerializable("USER", response.getUser());
            intent.putExtras(extras);
            startActivity(intent);
        }
        else{
            Toast.makeText(getContext(), response.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void signIn(){
        PostSignInTask task = new PostSignInTask(presenter, this);
        String alias = editHandle.getText().toString();
        String password = editPassword.getText().toString();
        SignInRequest request = new SignInRequest(alias, password);
        task.execute(request);
    }

    @Override
    public void enableButton(Boolean enabled) {
        signInButton.setEnabled(enabled);
    }

    @Override
    public void setPasswordError(String error) {
        editPassword.setError(error);
    }

    @Override
    public void setHandleError(String error) {
        editHandle.setError(error);
    }
}
