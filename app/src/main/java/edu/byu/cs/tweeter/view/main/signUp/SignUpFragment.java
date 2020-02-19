package edu.byu.cs.tweeter.view.main.signUp;

import android.content.Intent;
import android.os.Bundle;
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
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        presenter = new SignUpPresenter(this);

        editHandle = view.findViewById(R.id.input_alias);

        editPassword = view.findViewById(R.id.input_password);

        showPasswordSwitch = view.findViewById(R.id.showPassword);

        signUpButton = view.findViewById(R.id.btn_login);

        signUpButton.setEnabled(true); //FIXME:

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

}
