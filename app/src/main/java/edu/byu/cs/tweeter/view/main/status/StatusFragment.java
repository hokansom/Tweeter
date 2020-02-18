package edu.byu.cs.tweeter.view.main.status;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import org.w3c.dom.Text;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.presenter.StatusPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.PostStatusTask;

public class StatusFragment extends DialogFragment implements StatusPresenter.View {

    private User user;
    private ImageView userImageView;
    private StatusPresenter presenter;

    private EditText editStatus;
    private TextView share;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_new_status, container, false);

        presenter = new StatusPresenter(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        editStatus = (EditText) view.findViewById(R.id.editMessage);
        editStatus.requestFocus();

        share = (TextView)  view.findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

    }

//    private void postStatus(){
//        PostStatusTask postStatusTask = new PostStatusTask(presenter, this);
//
//    }
}
