package nikonorov.net.translator.screens.infoscreen.presenter;

import java.lang.ref.WeakReference;

import nikonorov.net.translator.mvp.presenter.MVPPresenterImpl;
import nikonorov.net.translator.screens.infoscreen.view.InfoView;

/**
 * Created by Vitaly Nikonorov on 22.04.17.
 * email@nikonorov.net
 */

public class InfoPresenterImpl extends MVPPresenterImpl<InfoView> implements InfoPresenter {
    public InfoPresenterImpl(InfoView view) {
        viewReference = new WeakReference<>(view);
    }
}
