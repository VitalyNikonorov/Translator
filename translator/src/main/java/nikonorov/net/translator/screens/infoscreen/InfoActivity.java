package nikonorov.net.translator.screens.infoscreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import nikonorov.net.translator.BuildConfig;
import nikonorov.net.translator.R;

/**
 * Created by Vitaly Nikonorov on 15.04.17.
 * email@nikonorov.net
 */

public class InfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_screen);
        TextView versionInfoTV = (TextView) findViewById(R.id.info_version);
        versionInfoTV.setText(String.format("%s %s", getString(R.string.info_version), BuildConfig.VERSION_NAME));
    }
}
