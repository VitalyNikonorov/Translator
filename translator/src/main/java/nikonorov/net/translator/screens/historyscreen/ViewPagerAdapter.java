package nikonorov.net.translator.screens.historyscreen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import nikonorov.net.translator.screens.listscreen.view.ListFragment;

/**
 * Created by Vitaly Nikonorov on 20.03.17.
 * email@nikonorov.net
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private ListFragment[] fragments;

    public ViewPagerAdapter(FragmentManager fm, ListFragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
