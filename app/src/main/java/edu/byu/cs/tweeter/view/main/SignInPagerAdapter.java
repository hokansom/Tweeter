package edu.byu.cs.tweeter.view.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.view.main.feed.FeedFragment;
import edu.byu.cs.tweeter.view.main.follower.FollowerFragment;
import edu.byu.cs.tweeter.view.main.following.FollowingFragment;
import edu.byu.cs.tweeter.view.main.story.StoryFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SignInPagerAdapter extends FragmentPagerAdapter {
    private static final int SIGNIN_FRAGMENT_POSITION = 0;
    private static final int SIGNUP_FRAGMENT_POSITION = 1;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.signInTabTitle, R.string.signUpTabTitle};
    private final Context mContext;

    public SignInPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == SIGNIN_FRAGMENT_POSITION){
//            return new SignInFragment();
            return PlaceholderFragment.newInstance(position + 1);
        }
        else if(position == SIGNUP_FRAGMENT_POSITION){
//            return new SignUpFragment();
            return PlaceholderFragment.newInstance(position + 1);
        }
        else{
            return PlaceholderFragment.newInstance(position + 1);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}