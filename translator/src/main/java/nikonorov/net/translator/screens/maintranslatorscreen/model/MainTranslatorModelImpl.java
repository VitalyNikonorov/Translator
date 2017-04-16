package nikonorov.net.translator.screens.maintranslatorscreen.model;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.data.Repository;
import nikonorov.net.translator.data.model.Language;
import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.network.YandexTranslatorAPI;
import nikonorov.net.translator.network.model.GetLangsResult;
import nikonorov.net.translator.network.model.TranslationResult;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
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
    public Observable<List<Language>> getLangs(String locale) {
        repository.getLangs()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Language>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Language> languages) {
                        if (languages.size() == 0) {
                            repository.initLangs();
                        }
                    }
                });

        return translatorAPI
                .getLangs(key, locale)
                .map(new Func1<GetLangsResult, List<Language>>() {
                    @Override
                    public List<Language> call(GetLangsResult getLangsResult) {
                        List<Language> languages = new ArrayList<>();
                        for (String lang: getLangsResult.langs.keySet()) {
                            languages.add(new Language(getLangsResult.langs.get(lang), lang));
                        }
                        return languages;
                    }
                });
    }

    @Override
    public void saveTranslation(TranslationResult translationResult) {
        TranslationPair translation = new TranslationPair(textForTranslation, TextUtils.join(", ", translationResult.text), translationResult.lang);
        repository.saveTranslation(translation);
    }

    @Override
    public void handleError(TranslationResult result) {
        //TODO send msg about error
    }

    @Override
    public void handleError(Throwable error) {
        //TODO send msd about error
    }
}
