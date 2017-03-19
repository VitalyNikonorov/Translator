package nikonorov.net.translator.di;

import javax.inject.Singleton;

import dagger.Component;
import nikonorov.net.translator.data.di.DataBaseModule;
import nikonorov.net.translator.screens.historyscreen.presenter.HistoryScreenPresenterImpl;
import nikonorov.net.translator.screens.historyscreen.presenter.SavedTranslationsPresenterImpl;
import nikonorov.net.translator.screens.listscreen.presenter.BaseListScreenPresenter;
import nikonorov.net.translator.screens.listscreen.view.ListFragment;
import nikonorov.net.translator.screens.maintranslatorscreen.model.MainTranslatorModelImpl;
import nikonorov.net.translator.network.di.NetworkModule;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, DataBaseModule.class})
public interface AppComponent {
    void inject(MainTranslatorModelImpl translatorModel);
}
