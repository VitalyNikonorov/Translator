package nikonorov.net.translator.screens.maintranslatorscreen.model;

import nikonorov.net.translator.network.model.TranslationResult;
import rx.Observable;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public interface MainTranslatorModel {

    Observable<TranslationResult> translate(String text);
}
