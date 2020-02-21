package edu.byu.cs.tweeter.view.main.search;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.net.request.SearchRequest;
import edu.byu.cs.tweeter.net.response.SearchResponse;
import edu.byu.cs.tweeter.presenter.SearchPresenter;
import edu.byu.cs.tweeter.view.asyncTasks.GetUserTask;
import edu.byu.cs.tweeter.view.main.profile.ProfileActivity;

public class SearchActivity extends Activity implements GetUserTask.GetUserObserver, SearchPresenter.View {

    private EditText searchText;
    private SearchPresenter presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        presenter = new SearchPresenter(this);


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width *.8), (int)( height*.25));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        searchText = findViewById(R.id.searchText);

        Button searchButton = findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search();
            }
        });

    }

    @Override
    public void userRetrieved(SearchResponse response) {
        finish();
        if(response.getUser() != null){
//            presenter.setViewingUser(response.getUser());
            Intent intent = new Intent(this, ProfileActivity.class );
            startActivity(intent);
        } else{
            Toast.makeText(getBaseContext(), response.getMessage(), Toast.LENGTH_LONG ).show();
        }
    }

    private void search(){
        String alias = searchText.getText().toString();
        GetUserTask getUserTask = new GetUserTask(presenter, this);
        SearchRequest request = new SearchRequest(alias, presenter.getCurrentUser());
        getUserTask.execute(request);
    }
}
