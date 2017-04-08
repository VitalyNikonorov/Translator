package nikonorov.net.translator.data.model;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */
@RealmClass
public class TranslationPair extends RealmObject {

    private String originalText;
    private String translatedText;
    private String lang;
    private boolean isBookmark;

    public TranslationPair() {
    }

    public TranslationPair(String originalText, String translatedText, String lang) {
        this(originalText, translatedText, lang, false);
    }

    public TranslationPair(String originalText, String translatedText, String lang, boolean isBookmark) {
        this.originalText = originalText;
        this.translatedText = translatedText;
        this.lang = lang;
        this.isBookmark = isBookmark;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }

    public boolean isBookmark() {
        return isBookmark;
    }

    public String getOriginalText() {
        return originalText;
    }

    public String getTranslatedText() {
        return translatedText;
    }

    public String getLang() {
        return lang;
    }

    public void setOriginalText(String originalText) {
        this.originalText = originalText;
    }

    public void setTranslatedText(String translatedText) {
        this.translatedText = translatedText;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
