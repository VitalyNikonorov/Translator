package nikonorov.net.translator.screens.historyscreen.presenter;

import java.util.List;

import nikonorov.net.translator.R;
import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.screens.listscreen.model.ListScreenModelImpl;
import nikonorov.net.translator.screens.listscreen.presenter.BaseListScreenPresenter;
import nikonorov.net.translator.screens.listscreen.view.ListScreenView;
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
        super.onStart();
        callHistoryRequest();
    }

    @Override
    public void onListItemIconClick(TranslationPair translation, int position) {
        translation.setHistory(true);
        model.changeBookmarkStatus(translation);
        ListScreenView view = viewRef.get();
        if (view != null) {
            view.changeBookmarkStatus(position);
        }
    }

    private void callHistoryRequest() {
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
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<TranslationPair> translationPairs) {
                        ListScreenView screenView = viewRef.get();
                        if (screenView != null) {
                            if (translationPairs.size() > 0) {
                                screenView.showContent(translationPairs);
                            } else {
                                screenView.showEmptyView(R.string.empty_list_history);
                            }
                        }
                    }
                });
    }

    @Override
    public void onDeleteBtnClick() {
        model.clearHistory().subscribe(new Observer() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Object o) {
                callHistoryRequest();
            }
        });
    }
}
