package nikonorov.net.translator;

import android.app.Application;

import nikonorov.net.translator.di.AppComponent;
import nikonorov.net.translator.di.DaggerAppComponent;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class TranslatorApplication extends Application {
    public static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = buildComponent();
    }

    protected AppComponent buildComponent() {
        return DaggerAppComponent
                .builder()
                .build();
    }
}
