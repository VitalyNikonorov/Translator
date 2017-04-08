package nikonorov.net.translator.screens.maintranslatorscreen.presenter;

import java.lang.ref.WeakReference;
import java.util.Arrays;

import nikonorov.net.translator.screens.maintranslatorscreen.model.MainTranslatorModel;
import nikonorov.net.translator.screens.maintranslatorscreen.model.MainTranslatorModelImpl;
import nikonorov.net.translator.screens.maintranslatorscreen.view.MainTranslatorView;
import nikonorov.net.translator.network.model.TranslationResult;
import rx.Observer;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorPresenterImpl implements MainTranslatorPresenter {

    private final MainTranslatorModel model;
    private WeakReference<MainTranslatorView> view;

    public MainTranslatorPresenterImpl(MainTranslatorView view) {
        this.model = new MainTranslatorModelImpl();
        this.view = new WeakReference<>(view);
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
                view.get().showTranslatedResult(Arrays.toString(translationResult.text));
                model.saveTranslation(translationResult);
            }
        });
    }
}
