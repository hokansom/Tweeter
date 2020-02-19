package edu.byu.cs.tweeter.view.main.signIn;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.presenter.SignInPresenter;

public class SignInFragment extends Fragment implements SignInPresenter.View {

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

        editPassword = view.findViewById(R.id.input_password);

        signInButton = view.findViewById(R.id.btn_login);

        noAccount = view.findViewById(R.id.link_signup);

        noAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //FIXME: add switch to sign up
                Toast.makeText(getContext(), "Switch to sign up", Toast.LENGTH_SHORT);
            }
        });

        return view;
    }

}
