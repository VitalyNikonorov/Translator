package nikonorov.net.translator.screens.maintranslatorscreen.model;

import java.util.List;

import nikonorov.net.translator.data.model.Language;
import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.network.model.GetLangsResult;
import nikonorov.net.translator.network.model.TranslationResult;
import rx.Observable;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public interface MainTranslatorModel {

    Observable<TranslationResult> translate(String text);
    Observable<List<Language>> requestLanguages(String locale);
    void saveTranslation(TranslationResult translationResult);
    void setCurrentTranslation(TranslationPair translation);
    void handleError(TranslationResult result);
    void handleError(Throwable error);
    void setLanguages(List<Language> languages, String locale);
    List<String> getFromLanguages();
    List<String> getToLanguages();
    void setLangFrom(int position);
    void setLangTo(int position);
    boolean changeBookmarkStatus();
    Observable<TranslationPair> getTranslationFromDB(TranslationResult translationResult);
    boolean isInternetAvailable();
    String getTextForTranslation();
    void setTextForTranslation(String text);
}
