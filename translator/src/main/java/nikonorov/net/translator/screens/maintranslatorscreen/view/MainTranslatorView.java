package nikonorov.net.translator.screens.maintranslatorscreen.view;

import android.support.annotation.StringRes;

import java.util.List;

import nikonorov.net.translator.mvp.view.MVPView;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public interface MainTranslatorView extends MVPView {
    void showTranslatedResult(String text);
    void showError(String msg);
    void showNoInternetAlert();
    void showError(@StringRes int msgId);
    void showRetryError(@StringRes int msgId);
    void setLangsFrom(List<String> langsFrom);
    void setLangsTo(List<String> langsFrom);
    void setActiveBookmarkBtn(boolean isAlreadyBookmark);
    void hideBookMarkBtn();
}
