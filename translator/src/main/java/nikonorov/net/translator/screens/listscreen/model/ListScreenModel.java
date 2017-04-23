package nikonorov.net.translator.screens.listscreen.model;

import java.util.List;

import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.mvp.model.MVPModel;
import rx.Observable;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public interface ListScreenModel extends MVPModel {
    Observable<List<TranslationPair>> getHistory();
    Observable<List<TranslationPair>> getBookmarks();
    void changeBookmarkStatus(TranslationPair translation);
    Observable clearHistory();
    Observable clearBookmarks();
}
