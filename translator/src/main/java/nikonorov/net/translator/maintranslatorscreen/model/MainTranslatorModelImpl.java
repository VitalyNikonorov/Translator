package nikonorov.net.translator.maintranslatorscreen.model;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.network.YandexTranslatorAPI;
import nikonorov.net.translator.network.model.TranslationResult;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorModelImpl implements MainTranslatorModel {

    @Inject
    YandexTranslatorAPI translatorAPI;
    private String lang = "en-ru"; //mock for test
    private String key = "trnsl.1.1.20170318T213150Z.d83d1acad689334f.b7f777dde4109491a144049dfce47051939c04a2"; //mock for test

    public MainTranslatorModelImpl() {
        TranslatorApplication.component.inject(this);
    }


    @Override
    public Observable<TranslationResult> translate(String text) {
        return translatorAPI
                .translate(key, text, lang)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
