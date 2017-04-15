package nikonorov.net.translator.screens.listscreen.presenter;

import java.lang.ref.WeakReference;

import nikonorov.net.translator.data.model.TranslationPair;
import nikonorov.net.translator.screens.listscreen.model.ListScreenModel;
import nikonorov.net.translator.screens.listscreen.model.ListScreenModelImpl;
import nikonorov.net.translator.screens.listscreen.view.ListScreenView;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public abstract class BaseListScreenPresenter implements ListScreenPresenter {

    protected WeakReference<ListScreenView> view;
    protected ListScreenModel model;
    protected Subscription subscription = Subscriptions.empty();

    @Override
    public void setView(ListScreenView view) {
        this.view = new WeakReference<>(view);
    }

    public BaseListScreenPresenter() {
        model = new ListScreenModelImpl();
    }

    @Override
    public void onListItemIconClick(TranslationPair translation) {
        model.addBookmark(translation);
    }

    protected void prepareSubscription() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    public void onStop() {
        prepareSubscription();
    }

}
