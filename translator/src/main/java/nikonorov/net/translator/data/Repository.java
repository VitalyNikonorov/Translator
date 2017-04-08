package nikonorov.net.translator.data;

import android.content.Context;

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
    private final Realm realm;

    @Inject
    public Repository(Context context) {
        TranslatorApplication.component.inject(this);
        Realm.init(context);
        realm = Realm.getDefaultInstance();
    }

    public Observable getHistory() {
        RealmResults<TranslationPair> translations = realm.where(TranslationPair.class).findAll();
        return translations.asObservable();
    }

    public void saveTranslation(TranslationPair translation) {
        realm.copyToRealmOrUpdate(translation);
    }
}
