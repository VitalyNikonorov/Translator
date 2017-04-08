package nikonorov.net.translator.screens.historyscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import nikonorov.net.translator.R;
import nikonorov.net.translator.screens.historyscreen.presenter.HistoryScreenPresenterImpl;
import nikonorov.net.translator.screens.historyscreen.presenter.SavedTranslationsPresenterImpl;
import nikonorov.net.translator.screens.listscreen.view.ListFragment;

/**
 * Created by Vitaly Nikonorov on 20.03.17.
 * email@nikonorov.net
 */

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_screen);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.history_view_pager);
        final ListFragment[] fragments = new ListFragment[2];
        fragments[0] = ListFragment.getInstance(new HistoryScreenPresenterImpl());
        fragments[1] = ListFragment.getInstance(new SavedTranslationsPresenterImpl());
        final ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }
}
