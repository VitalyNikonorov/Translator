package nikonorov.net.translator.data.model;

import io.realm.RealmObject;
import io.realm.annotations.RealmClass;

/**
 * Created by Vitaly Nikonorov on 16.04.17.
 * email@nikonorov.net
 */
@RealmClass
public class Language extends RealmObject {
    private String description;
    private String symbol;

    public Language(String description, String symbol) {
        this.description = description;
        this.symbol = symbol;
    }

    public Language() {
    }

    public String getDescription() {
        return description;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
