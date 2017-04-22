package nikonorov.net.translator.mvp.presenter;

import android.support.annotation.IdRes;

import java.lang.ref.WeakReference;

import nikonorov.net.translator.R;
import nikonorov.net.translator.mvp.view.MVPView;

/**
 * Created by Vitaly Nikonorov on 23.04.17.
 * email@nikonorov.net
 */

public abstract class MVPPresenterImpl<T extends MVPView> implements MVPPresenter {
    protected WeakReference<T> viewReference;

    @Override
    public void onNavigationItemClick(@IdRes int id) {
        T view = viewReference.get();
        if (view == null) {
            return;
        }
        switch (id) {
            case R.id.nav_main_screen:
                view.startMainTranslatorScreen();
                break;
            case R.id.nav_history:
                view.startHistoryScreen();
                break;
            case R.id.nav_info:
                view.startInfoScreen();
                break;
            default:
                break;
        }
    }
}
