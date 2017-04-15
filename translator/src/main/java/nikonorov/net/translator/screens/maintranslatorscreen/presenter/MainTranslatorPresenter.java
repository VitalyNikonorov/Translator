package nikonorov.net.translator.screens.maintranslatorscreen.presenter;

import android.support.annotation.IdRes;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public interface MainTranslatorPresenter {
    void onTranslateBtnClick(String text);
    void onNavigationItemClick(@IdRes int id);
    void onStart();
    void onStop();
}
