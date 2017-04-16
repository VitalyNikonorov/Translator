package nikonorov.net.translator.screens.maintranslatorscreen.view;

import android.support.annotation.StringRes;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public interface MainTranslatorView {
    void showTranslatedResult(String text);
    void startHistoryScreen();
    void startInfoScreen();
    void showError(String msg);
    void showError(@StringRes int msgId);
    void showRetryError(@StringRes int msgId);
}
