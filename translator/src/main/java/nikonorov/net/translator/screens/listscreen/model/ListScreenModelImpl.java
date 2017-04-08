package nikonorov.net.translator.screens.listscreen.model;

import java.util.List;

import javax.inject.Inject;

import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.data.Repository;
import nikonorov.net.translator.data.model.TranslationPair;
import rx.Observable;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public class ListScreenModelImpl implements ListScreenModel {

    @Inject
    Repository repository;

    public ListScreenModelImpl() {
        TranslatorApplication.component.inject(this);
    }

    @Override
    public Observable<List<TranslationPair>> getHistory() {
        return repository.getHistory();
    }
}
