package nikonorov.net.translator.di;

import javax.inject.Singleton;

import dagger.Component;
import nikonorov.net.translator.screens.maintranslatorscreen.model.MainTranslatorModelImpl;
import nikonorov.net.translator.network.di.NetworkModule;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class})
public interface AppComponent {
    void inject(MainTranslatorModelImpl translatorModel);
}
