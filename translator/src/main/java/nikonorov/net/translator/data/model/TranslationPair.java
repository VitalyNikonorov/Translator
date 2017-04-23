package nikonorov.net.translator.data.model;


import android.content.ContentValues;

import nikonorov.net.translator.data.DBHelper;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */
public class TranslationPair {

    public final String originalText;
    public final String translatedText;
    public final String lang;
    private boolean isBookmark;
    private boolean isHistory;

    public TranslationPair(String originalText, String translatedText, String lang) {
        this(originalText, translatedText, lang, true, false);
    }

    public TranslationPair(TranslationPair translationPair) {
        this.originalText = translationPair.originalText;
        this.translatedText = translationPair.translatedText;
        this.lang = translationPair.lang;
        this.isHistory = translationPair.isHistory();
        this.isBookmark = translationPair.isBookmark();
    }

    public TranslationPair(String originalText, String translatedText, String lang, boolean isHistory, boolean isBookmark) {
        this.originalText = originalText;
        this.translatedText = translatedText;
        this.lang = lang;
        this.isHistory = isHistory;
        this.isBookmark = isBookmark;
    }

    public ContentValues getCV(){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.ORIGINAL_TEXT, this.originalText);
        cv.put(DBHelper.TRANSLATED_TEXT, this.translatedText);
        cv.put(DBHelper.LANGUAGE_DIRECTION, this.lang);
        cv.put(DBHelper.IS_HISTORY, this.isHistory ? DBHelper.TRUE : DBHelper.FALSE);
        cv.put(DBHelper.IS_BOOKMARK, this.isBookmark ? DBHelper.TRUE : DBHelper.FALSE);
        return cv;
    }

    public boolean isBookmark() {
        return isBookmark;
    }

    public boolean isHistory() {
        return isHistory;
    }

    public void setBookmark(boolean bookmark) {
        isBookmark = bookmark;
    }

    public void setHistory(boolean history) {
        isHistory = history;
    }
}
