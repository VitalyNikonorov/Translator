package nikonorov.net.translator.data;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import nikonorov.net.translator.R;
import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.data.model.Language;
import nikonorov.net.translator.data.model.TranslationPair;
import rx.Observable;

/**
 * Created by Vitaly Nikonorov on 08.04.17.
 * email@nikonorov.net
 */

public class Repository {
    private final Context context;
    @Inject
    public Repository(Context context) {
        TranslatorApplication.component.inject(this);
        Realm.init(context);
        this.context = context;
    }

    public Observable<List<TranslationPair>> getHistory() {
        Realm realm = null;
        RealmResults<TranslationPair> translations;
        List<TranslationPair> result = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            translations = realm.where(TranslationPair.class).findAll();
            result = realm.copyFromRealm(translations);
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
        return Observable.just(result);
    }

    public Observable<List<TranslationPair>> getBookmarks() {
        Realm realm = null;
        RealmResults<TranslationPair> translations;
        List<TranslationPair> result = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            translations = realm.where(TranslationPair.class).equalTo("isBookmark", true).findAll();
            result = realm.copyFromRealm(translations);
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
        return Observable.just(result);
    }

    public void saveTranslation(final TranslationPair translation) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.insertOrUpdate(translation);
                }
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    public void clearHistory() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<TranslationPair> historyRows = realm.where(TranslationPair.class).equalTo("isBookmark", false).findAll();
                    historyRows.deleteAllFromRealm();
                }
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    public void clearBookmarks() {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<TranslationPair> historyRows = realm.where(TranslationPair.class).equalTo("isBookmark", true).findAll();
                    for (TranslationPair pair : historyRows) {
                        pair.setBookmark(false);
                    }
                }
            });
        } finally {
            if (realm != null) {
                realm.close();
            }
        }
    }


    public void addBookmark(final TranslationPair translation) {
        Realm realm = null;
        try {
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    TranslationPair savedTranslation = realm.where(TranslationPair.class)
                            .equalTo("originalText", translation.getOriginalText())
                            .equalTo("translatedText", translation.getTranslatedText())
                            .equalTo("lang", translation.getLang())
                            .findFirst();
                    savedTranslation.setBookmark(!translation.isBookmark());
                }
            });
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
    }

    public void initLangs() {
        InputStream is = context.getResources().openRawResource(R.raw.init_langs);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        Realm realm = null;
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            JSONObject json = new JSONObject(writer.toString());
            realm = Realm.getDefaultInstance();
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
//                    realm.insertOrUpdate(translation);
                }
            });
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            if(realm != null) {
                realm.close();
            }
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Observable<List<Language>> getLangs() {
        Realm realm = null;
        RealmResults<Language> languages;
        List<Language> result = new ArrayList<>();
        try {
            realm = Realm.getDefaultInstance();
            languages = realm.where(Language.class).findAll();
            result = realm.copyFromRealm(languages);
        } finally {
            if(realm != null) {
                realm.close();
            }
        }
        return Observable.just(result);
    }
}
