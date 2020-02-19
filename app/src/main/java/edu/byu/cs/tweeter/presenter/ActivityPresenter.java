package edu.byu.cs.tweeter.presenter;


public class ActivityPresenter extends Presenter {
    private final View view;

    public interface View{

    }

    public ActivityPresenter(View view) { this.view = view;}
}
