package nikonorov.net.translator.screens.historyscreen.presenter;

import java.util.List;

import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.screens.listscreen.model.ListScreenModelImpl;
import nikonorov.net.translator.screens.listscreen.presenter.BaseListScreenPresenter;
import nikonorov.net.translator.screens.listscreen.view.ListScreenView;
import rx.Observer;
import rx.Subscription;
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
        ListScreenView screenView = view.get();
        if (screenView != null) {
            screenView.showPreloader();
        }
        prepareSubscription();
        subscription = model.getHistory()
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
                        ListScreenView screenView = view.get();
                        if (screenView != null) {
                            if (translationPairs.size() > 0) {
                                screenView.showContent(translationPairs);
                            } else {
                                screenView.showPreloader();
                            }
                        }
                    }
                });
    }

    @Override
    public void onDeleteBtnClick() {
        model.clearHistory();
    }
}
