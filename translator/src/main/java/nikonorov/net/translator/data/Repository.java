package nikonorov.net.translator.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import nikonorov.net.translator.R;
import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.data.model.Language;
import nikonorov.net.translator.data.model.TranslationPair;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Vitaly Nikonorov on 08.04.17.
 * email@nikonorov.net
 */

public class Repository {

    private BriteDatabase db;

    @Inject
    public Repository(Context context) {
        TranslatorApplication.component.inject(this);
        /**
         * Creating DB connection
         */

        SqlBrite sqlBrite = SqlBrite.create();
        SQLiteOpenHelper dbHelper = new SQLiteOpenHelper(context, DBHelper.DB_NAME, null, DBHelper.DB_VERSION) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(DBHelper.DB_CREATE_HISTORY);
                db.execSQL(DBHelper.DB_CREATE_LANGUAGES);
            }

            @Override
            public void onOpen(SQLiteDatabase db) {
//                db.execSQL("DROP TABLE IF EXISTS " + DBHelper.HISTORY_TABLE + ";");
//                onCreate(db);
                super.onOpen(db);
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + DBHelper.HISTORY_TABLE + ";");
                db.execSQL("DROP TABLE IF EXISTS " + DBHelper.LANGUAGES_TABLE + ";");
                onCreate(db);
            }

            @Override
            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                db.execSQL("DROP TABLE IF EXISTS " + DBHelper.HISTORY_TABLE + ";");
                db.execSQL("DROP TABLE IF EXISTS " + DBHelper.LANGUAGES_TABLE + ";");
                onCreate(db);
            }
        };
        db = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
    }

    public Observable<List<TranslationPair>> getHistory() {
        Observable<List<TranslationPair>> observable = Observable.just(
                db.query(String.format("SELECT * FROM %s WHERE %s=%d", DBHelper.HISTORY_TABLE, DBHelper.IS_HISTORY, DBHelper.TRUE)))
                .map(new Func1<Cursor, List<TranslationPair>>() {
                    @Override
                    public List<TranslationPair> call(Cursor cursor) {
                        List<TranslationPair> translations = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            String originalText = cursor.getString(1);
                            String translatedText = cursor.getString(2);
                            String language = cursor.getString(3);
                            boolean isHistory = cursor.getInt(4) == DBHelper.TRUE;
                            boolean isBookmark = cursor.getInt(5) == DBHelper.TRUE;

                            TranslationPair translation = new TranslationPair(
                                    originalText,
                                    translatedText,
                                    language,
                                    isHistory,
                                    isBookmark
                            );
                            translations.add(translation);
                        }
                        return translations;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    public Observable<List<TranslationPair>> getBookmarks() {
        Observable<List<TranslationPair>> observable = Observable.just(
                db.query(String.format("SELECT * FROM %s WHERE %s=%d", DBHelper.HISTORY_TABLE, DBHelper.IS_BOOKMARK, DBHelper.TRUE)))
                .map(new Func1<Cursor, List<TranslationPair>>() {
                    @Override
                    public List<TranslationPair> call(Cursor cursor) {
                        List<TranslationPair> translations = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            String originalText = cursor.getString(1);
                            String translatedText = cursor.getString(2);
                            String language = cursor.getString(3);
                            boolean isHistory = cursor.getInt(4) == DBHelper.TRUE;
                            boolean isBookmark = cursor.getInt(5) == DBHelper.TRUE;

                            TranslationPair translation = new TranslationPair(
                                    originalText,
                                    translatedText,
                                    language,
                                    isHistory,
                                    isBookmark
                            );
                            translations.add(translation);
                        }
                        return translations;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    public void saveTranslation(final TranslationPair translation) {
        Observable.just(translation)
                .map(new Func1<TranslationPair, Void>() {
                    @Override
                    public Void call(TranslationPair translation) {
                        db.insert(DBHelper.HISTORY_TABLE, translation.getCV());
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public void saveLanguage(final Language language) {
        Observable.just(language)
                .map(new Func1<Language, Void>() {
                    @Override
                    public Void call(Language language) {
                        db.insert(DBHelper.LANGUAGES_TABLE, language.getCV());
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    public void clearHistory() {
        getHistory().map(new Func1<List<TranslationPair>, Void>() {
            @Override
            public Void call(List<TranslationPair> translationPairs) {
                for (TranslationPair translation : translationPairs) {
                    if (translation.isBookmark()) {
                        translation.setHistory(false);
                        db.update(DBHelper.HISTORY_TABLE, translation.getCV(), null);
                    } else {
                        db.delete(DBHelper.HISTORY_TABLE,
                                String.format("%s = \'%s\'", DBHelper.ORIGINAL_TEXT, translation.originalText),
                                String.format("%s = \'%s\'", DBHelper.TRANSLATED_TEXT, translation.translatedText),
                                String.format("%s = \'%s\'", DBHelper.LANGUAGE_DIRECTION, translation.lang));
                    }
                }
                return null;
            }
        });
    }

    public void clearBookmarks() {
        getBookmarks().map(new Func1<List<TranslationPair>, Void>() {
            @Override
            public Void call(List<TranslationPair> translationPairs) {
                for (TranslationPair translation : translationPairs) {
                    if (translation.isHistory()) {
                        translation.setBookmark(false);
                        db.update(DBHelper.HISTORY_TABLE, translation.getCV(), null);
                    } else {
                        db.delete(DBHelper.HISTORY_TABLE,
                                String.format("%s = \'%s\'", DBHelper.ORIGINAL_TEXT, translation.originalText),
                                String.format("%s = \'%s\'", DBHelper.TRANSLATED_TEXT, translation.translatedText),
                                String.format("%s = \'%s\'", DBHelper.LANGUAGE_DIRECTION, translation.lang));
                    }
                }
                return null;
            }
        });
    }

    public Observable<List<Language>> getLanguages(final String locale) {
        Observable<List<Language>> observable = Observable.just(
                db.query(String.format("SELECT * FROM %s WHERE %s='%s'", DBHelper.LANGUAGES_TABLE, DBHelper.LOCALE, locale)))
                .map(new Func1<Cursor, List<Language>>() {
                    @Override
                    public List<Language> call(Cursor cursor) {
                        List<Language> languages = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            String description = cursor.getString(1);
                            String symbol = cursor.getString(2);
                            String loc = cursor.getString(3);
                            Language language = new Language(
                                    description,
                                    symbol,
                                    loc
                            );
                            languages.add(language);
                        }
                        return languages;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }
}
