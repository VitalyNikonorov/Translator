package nikonorov.net.translator.network.model;

import java.util.Map;

/**
 * Created by Vitaly Nikonorov on 15.04.17.
 * email@nikonorov.net
 */

public class GetLangsResult {

    public final String[] dirs;
    public final Map<String, String> langs;

    public GetLangsResult(String[] dirs, Map<String, String> langs) {
        this.dirs = dirs;
        this.langs = langs;
    }
}
