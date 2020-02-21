package edu.byu.cs.tweeter.presenter;


public class ProfilePresenter extends Presenter {
    private final View view;

    private static int numOfFollowers = 0;
    private static int numOfFollowees = 0;

    public void updateFollowers(int num){
        numOfFollowers = num;
        view.updateNumbers();
    }

    public void updateFollowees(int num){
        numOfFollowees = num;
        view.updateNumbers();
    }

    public interface View{
        void updateNumbers();
    }

    public ProfilePresenter(View view) { this.view = view;}

    public int getNumOfFollowers(){
        return numOfFollowers;
    }

    public int getNumOfFollowees(){
        return numOfFollowees;
    }

}
