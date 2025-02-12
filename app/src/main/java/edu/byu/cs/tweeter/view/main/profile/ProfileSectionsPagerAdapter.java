package edu.byu.cs.tweeter.view.main.profile;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.view.main.PlaceholderFragment;
import edu.byu.cs.tweeter.view.main.feed.FeedFragment;
import edu.byu.cs.tweeter.view.main.follower.FollowerFragment;
import edu.byu.cs.tweeter.view.main.following.FollowingFragment;
import edu.byu.cs.tweeter.view.main.story.StoryFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class ProfileSectionsPagerAdapter extends FragmentPagerAdapter {
    private static final int STORY_FRAGMENT_POSITION = 0;
    private static final int FOLLOWING_FRAGMENT_POSITION = 1;
    private static final int FOLLOWER_FRAGMENT_POSITION = 2;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{ R.string.storyTabTitle, R.string.followingTabTitle, R.string.followersTabTitle};
    private final Context mContext;

    public ProfileSectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if(position == STORY_FRAGMENT_POSITION){
            return new StoryFragment();
        }
        else if (position == FOLLOWING_FRAGMENT_POSITION) {
            return new FollowingFragment();
        } else if (position == FOLLOWER_FRAGMENT_POSITION){
            return new FollowerFragment();
        } else {
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
        // Show 3 total pages.
        return 3;
    }
}