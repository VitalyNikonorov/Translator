package nikonorov.net.translator.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.data.model.TranslationPair;
import rx.Observable;

/**
 * Created by Vitaly Nikonorov on 08.04.17.
 * email@nikonorov.net
 */

public class Repository {
    @Inject
    public Repository(Context context) {
        TranslatorApplication.component.inject(this);
        Realm.init(context);
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
}
