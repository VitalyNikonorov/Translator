package nikonorov.net.translator.screens.maintranslatorscreen.model;

import javax.inject.Inject;

import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.data.Repository;
import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.network.YandexTranslatorAPI;
import nikonorov.net.translator.network.model.TranslationResult;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorModelImpl implements MainTranslatorModel {

    @Inject
    YandexTranslatorAPI translatorAPI;
    @Inject
    Repository repository;
    private String lang = "en-ru"; //mock for test
    private String key = "trnsl.1.1.20170318T213150Z.d83d1acad689334f.b7f777dde4109491a144049dfce47051939c04a2"; //mock for test
    private String textForTranslation;

    public MainTranslatorModelImpl() {
        TranslatorApplication.component.inject(this);
    }

    @Override
    public Observable<TranslationResult> translate(String text) {
        textForTranslation = text;
        return translatorAPI
                .translate(key, text, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void saveTranslation(TranslationResult translationResult) {
        TranslationPair translation = new TranslationPair(textForTranslation, translationResult.text.toString(), translationResult.lang);
        repository.saveTranslation(translation);
    }
}
