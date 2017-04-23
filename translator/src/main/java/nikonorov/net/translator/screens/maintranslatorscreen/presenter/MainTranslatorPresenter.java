package nikonorov.net.translator.screens.maintranslatorscreen.presenter;

import nikonorov.net.translator.mvp.presenter.MVPPresenter;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public interface MainTranslatorPresenter extends MVPPresenter {
    void onTranslateBtnClick(String text);
    void onStart();
    void onStop();
    void onRetryClick();
    void onLangFromSelected(int position);
    void onLangToSelected(int position);
    void onAddBookmarkClick();
}
