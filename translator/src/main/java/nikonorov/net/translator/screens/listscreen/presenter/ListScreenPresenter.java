package nikonorov.net.translator.screens.listscreen.presenter;

import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.screens.listscreen.view.ListScreenView;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public interface ListScreenPresenter {

    void setView(ListScreenView view);
    void onStart();
    void onListItemIconClick(TranslationPair translation);
}
