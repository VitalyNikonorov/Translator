package nikonorov.net.translator.data.model;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public class TranslationPair {

    public final String originalText;
    public final String translatedText;
    public final String lang;

    private boolean isBookmark;

    public TranslationPair(String originalText, String translatedText, String lang) {
        this(originalText, translatedText, lang, false);
    }

    public TranslationPair(String originalText, String translatedText, String lang, boolean isBookmark) {
        this.originalText = originalText;
        this.translatedText = translatedText;
        this.lang = lang;
        this.isBookmark = isBookmark;
    }
}
