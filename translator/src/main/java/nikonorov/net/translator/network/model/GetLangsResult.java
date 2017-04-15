package nikonorov.net.translator.network.model;

/**
 * Created by Vitaly Nikonorov on 15.04.17.
 * email@nikonorov.net
 */

public class GetLangsResult {

    public final String[] dirs;
    public final String[] langs;

    public GetLangsResult(String[] dirs, String[] langs) {
        this.dirs = dirs;
        this.langs = langs;
    }
}
