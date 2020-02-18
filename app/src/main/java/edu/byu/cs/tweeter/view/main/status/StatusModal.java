package edu.byu.cs.tweeter.view.main.status;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.presenter.StatusPresenter;

public class StatusModal implements StatusPresenter.View {

    private StatusPresenter presenter;
    private User user;



    public void showPopupWindow(final View view){
//        presenter = new StatusPresenter(view);

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View modalView = inflater.inflate(R.layout.fragment_new_status, null);



        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        boolean focusable = true;

        final PopupWindow popUpWindow = new PopupWindow(modalView, width, height, focusable);

        popUpWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }
}
