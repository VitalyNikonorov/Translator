package nikonorov.net.translator.screens.listscreen.presenter;

import java.lang.ref.WeakReference;

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

    protected WeakReference<ListScreenView> viewRef;
    protected ListScreenModel model;
    protected Subscription subscription = Subscriptions.empty();

    public void setView(ListScreenView view) {
        this.viewRef = new WeakReference<>(view);
    }

    public BaseListScreenPresenter() {
        model = new ListScreenModelImpl();
    }

    protected void prepareSubscription() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    @Override
    public void onStart() {
        ListScreenView screenView = viewRef.get();
        if (screenView != null) {
            screenView.showPreloader();
        }
    }

    public void onStop() {
        prepareSubscription();
    }

}
