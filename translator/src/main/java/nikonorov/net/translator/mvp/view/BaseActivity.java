package nikonorov.net.translator.mvp.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import nikonorov.net.translator.R;
import nikonorov.net.translator.mvp.presenter.MVPPresenter;
import nikonorov.net.translator.screens.historyscreen.view.HistoryActivity;
import nikonorov.net.translator.screens.infoscreen.view.InfoActivity;
import nikonorov.net.translator.screens.maintranslatorscreen.view.MainTranslatorActivity;

/**
 * Created by Vitaly Nikonorov on 22.04.17.
 * email@nikonorov.net
 */

public abstract class BaseActivity<P extends MVPPresenter>
        extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MVPView {

    private DrawerLayout rootDrawerLayout;
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rootDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, rootDrawerLayout, toolbar ,  R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        rootDrawerLayout.addDrawerListener(drawerToggle);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerToggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        presenter.onNavigationItemClick(item.getItemId());
        rootDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    public void onBackPressed() {
        if (rootDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            rootDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void startHistoryScreen() {
        startScreen(HistoryActivity.class);
    }

    @Override
    public void startInfoScreen() {
        startScreen(InfoActivity.class);
    }

    @Override
    public void startMainTranslatorScreen() {
        startScreen(MainTranslatorActivity.class);
    }

    private void startScreen(Class<? extends Activity> clazz) {
        if (!this.getClass().isAssignableFrom(clazz)) {
            startActivity(new Intent(this, clazz).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
        }
    }

    protected abstract @LayoutRes int getLayoutId();
}
