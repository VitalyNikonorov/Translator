package nikonorov.net.translator.screens.listscreen.model;

import java.util.List;

import javax.inject.Inject;

import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.data.Repository;
import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.mvp.model.MVPModelImpl;
import rx.Observable;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public class ListScreenModelImpl extends MVPModelImpl implements ListScreenModel {

    @Inject
    Repository repository;

    public ListScreenModelImpl() {
        TranslatorApplication.component.inject(this);
    }

    @Override
    public Observable<List<TranslationPair>> getHistory() {
        return repository.getHistory();
    }

    @Override
    public Observable<List<TranslationPair>> getBookmarks() {
        return repository.getBookmarks();
    }

    @Override
    public void changeBookmarkStatus(TranslationPair translation) {
        translation.setBookmark(!translation.isBookmark());
        repository.saveTranslation(translation);
    }

    @Override
    public Observable clearHistory() {
        return repository.clearHistory();
    }

    @Override
    public Observable clearBookmarks() {
        return repository.clearBookmarks();
    }
}
