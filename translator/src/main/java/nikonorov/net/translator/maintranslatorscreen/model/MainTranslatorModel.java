package nikonorov.net.translator.maintranslatorscreen.model;

import io.reactivex.Observable;
import nikonorov.net.translator.network.model.TranslationResult;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public interface MainTranslatorModel {

    Observable<TranslationResult> translate(String text);
}
