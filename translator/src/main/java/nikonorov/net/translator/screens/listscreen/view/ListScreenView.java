package nikonorov.net.translator.screens.listscreen.view;

import android.support.annotation.StringRes;

import java.util.List;

import nikonorov.net.translator.data.model.TranslationPair;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public interface ListScreenView {

    void showEmptyView(@StringRes int emptyTitle);
    void showPreloader();
    void showContent(List<TranslationPair> data);
    void changeBookmarkStatus(int position);
    void deleteItem(int position);
}
