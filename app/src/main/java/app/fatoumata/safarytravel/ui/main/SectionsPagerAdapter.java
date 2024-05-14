package app.fatoumata.safarytravel.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import app.fatoumata.safarytravel.R;
import app.fatoumata.safarytravel.ui.main.safary.FragmentCountrySafaryPhoto;
import app.fatoumata.safarytravel.ui.main.info.FragmentCountryInfo;
import app.fatoumata.safarytravel.ui.main.challenge.FragmentCountryChallenge;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public static final String COUNTRY_NAME_KEY = "COUNTRY_NAME";
    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_country_info, R.string.tab_safary, R.string.tab_challenges};
    private final Context mContext;
    private final String countryName;

    public SectionsPagerAdapter(Context context, FragmentManager fm, String countryName) {
        super(fm);
        mContext = context;
        this.countryName = countryName;
    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0)  return   FragmentCountryInfo.newInstance(countryName);
        if(position == 1)  return  FragmentCountrySafaryPhoto.newInstance(countryName);
        if(position == 2)  return FragmentCountryChallenge.newInstance(countryName);

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