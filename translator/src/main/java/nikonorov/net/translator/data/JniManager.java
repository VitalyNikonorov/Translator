package nikonorov.net.translator.data;

/**
 * Created by Vitaly Nikonorov on 23.04.17.
 * email@nikonorov.net
 */

public class JniManager {
    static {
        System.loadLibrary("data");
    }
    public static native String getAPIKey();
}
