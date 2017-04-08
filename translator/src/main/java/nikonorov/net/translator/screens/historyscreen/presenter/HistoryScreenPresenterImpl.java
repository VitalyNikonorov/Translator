package nikonorov.net.translator.screens.historyscreen.presenter;

import java.util.List;

import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.screens.listscreen.model.ListScreenModelImpl;
import nikonorov.net.translator.screens.listscreen.presenter.BaseListScreenPresenter;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public class HistoryScreenPresenterImpl extends BaseListScreenPresenter {

    public HistoryScreenPresenterImpl() {
        model = new ListScreenModelImpl();
    }

    @Override
    public void onStart() {
        model.getHistory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TranslationPair>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TranslationPair> translationPairs) {
                        view.get().showContent(translationPairs);
                    }
                });
    }
}
