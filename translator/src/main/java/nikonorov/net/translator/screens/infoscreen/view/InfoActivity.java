package nikonorov.net.translator.screens.infoscreen.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import nikonorov.net.translator.BuildConfig;
import nikonorov.net.translator.R;
import nikonorov.net.translator.mvp.view.BaseActivity;
import nikonorov.net.translator.screens.infoscreen.presenter.InfoPresenter;
import nikonorov.net.translator.screens.infoscreen.presenter.InfoPresenterImpl;

/**
 * Created by Vitaly Nikonorov on 15.04.17.
 * email@nikonorov.net
 */

public class InfoActivity extends BaseActivity<InfoPresenter> implements InfoView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new InfoPresenterImpl(this);
        TextView versionInfoTV = (TextView) findViewById(R.id.info_version);
        versionInfoTV.setText(String.format("%s %s", getString(R.string.info_version), BuildConfig.VERSION_NAME));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.info_screen;
    }
}
