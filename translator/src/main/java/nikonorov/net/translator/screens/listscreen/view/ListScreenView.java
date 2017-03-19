package nikonorov.net.translator.screens.listscreen.view;

import java.util.List;

import nikonorov.net.translator.data.model.TranslationPair;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public interface ListScreenView {

    void showEmptyView();
    void showPreloader();
    void showContent(List<TranslationPair> data);

}
