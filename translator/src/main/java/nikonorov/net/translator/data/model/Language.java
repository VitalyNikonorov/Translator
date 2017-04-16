package nikonorov.net.translator.data.model;

import android.content.ContentValues;

import nikonorov.net.translator.data.DBHelper;

/**
 * Created by Vitaly Nikonorov on 16.04.17.
 * email@nikonorov.net
 */

public class Language {
    public final String description;
    public final String symbol;
    public final String locale;

    public Language(String description, String symbol, String locale) {
        this.description = description;
        this.symbol = symbol;
        this.locale = locale;
    }


    public ContentValues getCV(){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.DESCRIPTION, this.description);
        cv.put(DBHelper.SYMBOL, this.symbol);
        cv.put(DBHelper.LOCALE, this.locale);
        return cv;
    }
}
