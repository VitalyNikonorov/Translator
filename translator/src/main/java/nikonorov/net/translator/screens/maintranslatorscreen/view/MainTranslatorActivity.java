package nikonorov.net.translator.screens.maintranslatorscreen.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import nikonorov.net.translator.R;
import nikonorov.net.translator.screens.historyscreen.HistoryActivity;
import nikonorov.net.translator.screens.infoscreen.InfoActivity;
import nikonorov.net.translator.screens.maintranslatorscreen.presenter.MainTranslatorPresenter;
import nikonorov.net.translator.screens.maintranslatorscreen.presenter.MainTranslatorPresenterImpl;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorActivity extends AppCompatActivity implements
        MainTranslatorView,
        View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {

    private TextView translatedTV;
    private EditText translationField;
    private MainTranslatorPresenter presenter;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MainTranslatorPresenterImpl(this);
        setContentView(R.layout.main_translator_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        translatedTV = (TextView) findViewById(R.id.translated_text);
        translationField = (EditText) findViewById(R.id.translating_text);
        findViewById(R.id.translate_btn).setOnClickListener(this);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar ,  R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawer.addDrawerListener(drawerToggle);
        drawerToggle.setToolbarNavigationClickListener(this);
        drawerToggle.syncState();
    }

    @Override
    public void showTranslatedResult(String text) {
        translatedTV.setText(text);
    }

    @Override
    public void startHistoryScreen() {
        startActivity(new Intent(MainTranslatorActivity.this, HistoryActivity.class));
    }

    @Override
    public void startInfoScreen() {
        startActivity(new Intent(MainTranslatorActivity.this, InfoActivity.class));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.translate_btn:
                presenter.onTranslateBtnClick(translationField.getText().toString());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        presenter.onNavigationItemClick(item.getItemId());
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
