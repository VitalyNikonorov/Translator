package nikonorov.net.translator.screens.historyscreen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import nikonorov.net.translator.R;
import nikonorov.net.translator.mvp.view.BaseActivity;
import nikonorov.net.translator.screens.historyscreen.MainHistoryScreenPresenter;
import nikonorov.net.translator.screens.historyscreen.MainHistoryScreenPresenterImpl;
import nikonorov.net.translator.screens.historyscreen.ViewPagerAdapter;
import nikonorov.net.translator.screens.historyscreen.presenter.HistoryScreenPresenterImpl;
import nikonorov.net.translator.screens.historyscreen.presenter.SavedTranslationsPresenterImpl;
import nikonorov.net.translator.screens.listscreen.view.ListFragment;

/**
 * Created by Vitaly Nikonorov on 20.03.17.
 * email@nikonorov.net
 */

public class HistoryActivity extends BaseActivity<MainHistoryScreenPresenter>
        implements MainHistoryView, TabLayout.OnTabSelectedListener {

    private final int FRAGMENT_COUNT = 2;
    private ViewPager viewPager;
    private final ListFragment[] fragments = new ListFragment[FRAGMENT_COUNT];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainHistoryScreenPresenterImpl(this);

        viewPager = (ViewPager) findViewById(R.id.history_view_pager);
        fragments[0] = ListFragment.getInstance(new HistoryScreenPresenterImpl());
        fragments[1] = ListFragment.getInstance(new SavedTranslationsPresenterImpl());
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_caption_history)));
        tabLayout.addTab(tabLayout.newTab().setText(getString(R.string.tab_caption_bookmarks)));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.history_screen;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
        fragments[tab.getPosition()].onStart();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
