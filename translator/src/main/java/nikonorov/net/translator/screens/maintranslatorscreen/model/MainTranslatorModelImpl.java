package nikonorov.net.translator.screens.maintranslatorscreen.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import nikonorov.net.translator.R;
import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.data.JniManager;
import nikonorov.net.translator.data.Repository;
import nikonorov.net.translator.data.model.Language;
import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.mvp.model.MVPModel;
import nikonorov.net.translator.mvp.model.MVPModelImpl;
import nikonorov.net.translator.network.YandexTranslatorAPI;
import nikonorov.net.translator.network.model.GetLangsResult;
import nikonorov.net.translator.network.model.TranslationResult;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorModelImpl extends MVPModelImpl implements MainTranslatorModel {

    @Inject
    YandexTranslatorAPI translatorAPI;
    @Inject
    Repository repository;
    @Inject
    Context context;

    private final String key;
    private String textForTranslation;
    private TranslationPair currentTranslation;
    private final String autoDetectLang = "";

    private final ArrayList<Language> langsFrom = new ArrayList<>();
    private final ArrayList<Language> langsTo = new ArrayList<>();
    private int langFromPosition;
    private int langToPosition;
    private final ConnectivityManager cm;

    public MainTranslatorModelImpl() {
        key = JniManager.getAPIKey();
        TranslatorApplication.component.inject(this);
        cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public Observable<TranslationResult> translate(String text) {
        textForTranslation = text;
        StringBuilder langBuilder = new StringBuilder();
        if (!langsFrom.get(langFromPosition).symbol.equals("")){
            langBuilder.append(langsFrom.get(langFromPosition).symbol)
                    .append("-");
        }
        langBuilder.append(langsTo.get(langToPosition).symbol);
        return translatorAPI
                .translate(key, text, langBuilder.toString())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Language>> requestLanguages(String locale) {
        Observable<List<Language>> observable = Observable
                .concat(getLangsFromDB(locale), getLangsFromAPI(locale))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Observable<List<Language>> getLangsFromDB(final String locale) {
        Observable<List<Language>> observable = repository.getLanguages(locale)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    private Observable<List<Language>> getLangsFromAPI(final String locale) {
        Observable<List<Language>> observable = translatorAPI
                .getLangs(key, locale)
                .map(new Func1<GetLangsResult, List<Language>>() {
                    @Override
                    public List<Language> call(GetLangsResult getLangsResult) {
                        ArrayList<Language> languages = new ArrayList<>();
                        for (String lang : getLangsResult.langs.keySet()) {
                            Language language = new Language(getLangsResult.langs.get(lang), lang, locale);
                            languages.add(language);
                        }
                        Collections.sort(languages, new Comparator<Language>() {
                            @Override
                            public int compare(Language o1, Language o2) {
                                return o1.description.compareTo(o2.description);
                            }
                        });
                        if (!langsTo.equals(languages)){
                            repository.saveLanguages(languages);
                        }
                        return languages;
                    }
                })
                .subscribeOn(Schedulers.io());
        return observable;
    }

    @Override
    public void saveTranslation(TranslationResult translationResult) {
        TranslationPair translation = new TranslationPair(textForTranslation, TextUtils.join(", ", translationResult.text), translationResult.lang);
        setCurrentTranslation(translation);
        repository.saveTranslation(translation);
    }

    @Override
    public void setCurrentTranslation(TranslationPair translation) {
        currentTranslation = translation;
    }

    @Override
    public void handleError(TranslationResult result) {
        //TODO send msg about error
    }

    @Override
    public void setLanguages(List<Language> languages, String locale) {
        if (!langsTo.equals(languages)) {
            langsTo.addAll(languages);
            langsFrom.add(new Language(context.getString(R.string.detect_language), autoDetectLang, locale));
            langsFrom.addAll(languages);
        }
    }

    @Override
    public List<String> getFromLanguages() {
        ArrayList<String> langs = new ArrayList<>();
        for (Language lang : langsFrom) {
            langs.add(lang.description);
        }
        return langs;
    }

    @Override
    public List<String> getToLanguages() {
        ArrayList<String> langs = new ArrayList<>();
        for (Language lang : langsTo) {
            langs.add(lang.description);
        }
        return langs;
    }

    @Override
    public void setLangFrom(int position) {
        langFromPosition = position;
    }

    @Override
    public void setLangTo(int position) {
        langToPosition = position;
    }

    @Override
    public boolean changeBookmarkStatus() {
        boolean newStatus = !currentTranslation.isBookmark();
        currentTranslation.setBookmark(newStatus);
        currentTranslation.setHistory(true);
        repository.saveTranslation(currentTranslation);
        return newStatus;
    }

    @Override
    public Observable<TranslationPair> getTranslationFromDB(TranslationResult translationResult) {
        TranslationPair translation = new TranslationPair(textForTranslation, TextUtils.join(", ", translationResult.text), translationResult.lang);
        return repository.getTranslationFromDB(translation);
    }

    @Override
    public boolean isInternetAvailable() {
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public String getTextForTranslation() {
        return textForTranslation;
    }

    @Override
    public void setTextForTranslation(String text) {
        textForTranslation = text;
    }
}
