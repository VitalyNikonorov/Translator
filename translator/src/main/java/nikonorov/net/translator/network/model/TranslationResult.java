package nikonorov.net.translator.network.model;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public class TranslationResult {

    public final int code;
    public final String lang;
    public final String[] text;

    public TranslationResult(int code, String lang, String[] text) {
        this.code = code;
        this.lang = lang;
        this.text = text;
    }
}
