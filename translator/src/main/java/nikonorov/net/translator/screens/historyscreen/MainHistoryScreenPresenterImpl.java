package nikonorov.net.translator.screens.historyscreen;

import java.lang.ref.WeakReference;

import nikonorov.net.translator.mvp.presenter.MVPPresenterImpl;
import nikonorov.net.translator.screens.historyscreen.view.MainHistoryView;

/**
 * Created by Vitaly Nikonorov on 23.04.17.
 * email@nikonorov.net
 */

public class MainHistoryScreenPresenterImpl extends MVPPresenterImpl<MainHistoryView>
        implements MainHistoryScreenPresenter {

    public MainHistoryScreenPresenterImpl(MainHistoryView view) {
        viewReference = new WeakReference<>(view);
    }
}
