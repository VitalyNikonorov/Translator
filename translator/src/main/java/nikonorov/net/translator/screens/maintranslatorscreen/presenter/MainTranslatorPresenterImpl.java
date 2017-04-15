package nikonorov.net.translator.screens.maintranslatorscreen.presenter;

import android.support.annotation.IdRes;
import android.text.TextUtils;

import java.lang.ref.WeakReference;

import nikonorov.net.translator.R;
import nikonorov.net.translator.network.model.TranslationResult;
import nikonorov.net.translator.screens.maintranslatorscreen.model.MainTranslatorModel;
import nikonorov.net.translator.screens.maintranslatorscreen.model.MainTranslatorModelImpl;
import nikonorov.net.translator.screens.maintranslatorscreen.view.MainTranslatorView;
import rx.Observer;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorPresenterImpl implements MainTranslatorPresenter {

    private final MainTranslatorModel model;
    private WeakReference<MainTranslatorView> viewRefference;

    public MainTranslatorPresenterImpl(MainTranslatorView view) {
        this.model = new MainTranslatorModelImpl();
        this.viewRefference = new WeakReference<>(view);
    }

    @Override
    public void onTranslateBtnClick(String text) {
        model.translate(text).subscribe(new Observer<TranslationResult>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(TranslationResult translationResult) {
                MainTranslatorView view = viewRefference.get();
                if (view != null) {
                    view.showTranslatedResult(TextUtils.join(", ", translationResult.text));
                    model.saveTranslation(translationResult);
                }
            }
        });
    }

    @Override
    public void onNavigationItemClick(@IdRes int id) {
        switch (id) {
            case R.id.nav_main_screen:
                break;
            case R.id.nav_history:
                MainTranslatorView view = viewRefference.get();
                if (view != null) {
                    view.startHistoryScreen();
                }
                break;
            case R.id.nav_about:
                break;
            default:
                break;
        }
    }
}
