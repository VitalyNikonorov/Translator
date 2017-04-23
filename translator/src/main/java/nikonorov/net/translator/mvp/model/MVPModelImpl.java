package nikonorov.net.translator.mvp.model;

/**
 * Created by Vitaly Nikonorov on 23.04.17.
 * email@nikonorov.net
 */

public abstract class MVPModelImpl implements MVPModel {

    @Override
    public void handleError(Throwable t) {
        //TODO handle and send crash report
        t.printStackTrace();
    }
}
