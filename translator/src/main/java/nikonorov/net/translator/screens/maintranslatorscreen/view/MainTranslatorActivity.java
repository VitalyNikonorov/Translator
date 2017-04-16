package nikonorov.net.translator.screens.maintranslatorscreen.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private DrawerLayout rootDrawerLayout;

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
        drawerToggle.setToolbarNavigationClickListener(this);
        drawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
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
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void showError(@StringRes int msgId) {
        showToast(getString(msgId));
    }

    @Override
    public void showRetryError(@StringRes int msgId) {
        new AlertDialog.Builder(this)
                .setMessage(msgId)
                .setTitle(R.string.error_caption)
                .setPositiveButton(R.string.action_retry, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            presenter.onRetryClick();
                        }
                })
                .setNegativeButton(R.string.action_cancel, null)
                .create()
                .show();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
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
        if (rootDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            rootDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        presenter.onNavigationItemClick(item.getItemId());
        rootDrawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }
}
