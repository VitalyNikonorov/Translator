package nikonorov.net.translator.di;

import javax.inject.Singleton;

import dagger.Component;
import nikonorov.net.translator.data.Repository;
import nikonorov.net.translator.data.di.DataBaseModule;
import nikonorov.net.translator.network.di.NetworkModule;
import nikonorov.net.translator.screens.maintranslatorscreen.model.MainTranslatorModelImpl;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, DataBaseModule.class})
public interface AppComponent {
    void inject(MainTranslatorModelImpl translatorModel);
    void inject(Repository repository);
    //inject listfragment just one
}
