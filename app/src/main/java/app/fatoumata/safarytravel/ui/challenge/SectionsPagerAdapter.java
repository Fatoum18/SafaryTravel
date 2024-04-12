package app.fatoumata.safarytravel.ui.challenge;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import app.fatoumata.safarytravel.R;
import app.fatoumata.safarytravel.ui.challenge.allphotos.FragmentChallengePhotos;

import app.fatoumata.safarytravel.ui.challenge.myphoto.FragmentChallengeMyPhotos;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{  R.string.tab_all_photos, R.string.tab_my_photos};
    private final Context mContext;
    private final String countryName;

    public SectionsPagerAdapter(Context context, FragmentManager fm, String countryName) {
        super(fm);
        mContext = context;
        this.countryName = countryName;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0)  return   new FragmentChallengePhotos();

        if(position == 1)  return  new FragmentChallengeMyPhotos();

        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return TAB_TITLES.length;
    }
}