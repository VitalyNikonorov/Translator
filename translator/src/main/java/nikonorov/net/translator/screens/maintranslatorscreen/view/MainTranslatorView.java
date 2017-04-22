package nikonorov.net.translator.screens.maintranslatorscreen.view;

import android.support.annotation.StringRes;

import nikonorov.net.translator.mvp.view.MVPView;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public interface MainTranslatorView extends MVPView {
    void showTranslatedResult(String text);
    void showError(String msg);
    void showError(@StringRes int msgId);
    void showRetryError(@StringRes int msgId);
}
