package nikonorov.net.translator.data;

/**
 * Created by Vitaly Nikonorov on 16.04.17.
 * email@nikonorov.net
 */

public class DBHelper {
    public final static String DB_NAME = "nikonorov.net.translator";
    public final static int DB_VERSION = 1;

    public final static String _ID = "_id";

    public final static String HISTORY_TABLE = "history";
    public final static String ORIGINAL_TEXT = "original_text";
    public final static String TRANSLATED_TEXT = "translated_text";
    public final static String LANGUAGE_DIRECTION = "direction";
    public final static String IS_BOOKMARK = "is_bookmark";
    public final static String IS_HISTORY = "is_history";

    public final static int TRUE = 1;
    public final static int FALSE = 0;

    public static final String DB_CREATE_HISTORY =
            String.format("CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s INTEGER, %s INTEGER, UNIQUE(%s, %s, %s) ON CONFLICT REPLACE);",
                    HISTORY_TABLE,
                    _ID,
                    ORIGINAL_TEXT,
                    TRANSLATED_TEXT,
                    LANGUAGE_DIRECTION,
                    IS_BOOKMARK,
                    IS_HISTORY,
                    ORIGINAL_TEXT,
                    TRANSLATED_TEXT,
                    LANGUAGE_DIRECTION);
}
