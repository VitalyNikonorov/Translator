package nikonorov.net.translator.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public class ThreadUtils {

    @Nullable private static Handler mainThreadHandler;

    public static void executeOnMain(Runnable task) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            task.run();
        } else {
            addOnMainQueue(task, 0);
        }
    }

    public static void addOnMainQueue(Runnable task, long delayMillis) {
        getMainThreadHandler().postDelayed(task, delayMillis);
    }

    public static Handler getMainThreadHandler(){
        if (mainThreadHandler == null) {
            mainThreadHandler = new Handler(Looper.getMainLooper());
        }
        return mainThreadHandler;
    }
}
