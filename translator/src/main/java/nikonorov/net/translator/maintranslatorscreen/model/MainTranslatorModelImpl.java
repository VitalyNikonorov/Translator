package nikonorov.net.translator.maintranslatorscreen.model;

import javax.inject.Inject;

import nikonorov.net.translator.TranslatorApplication;
import nikonorov.net.translator.network.YandexTranslatorAPI;

/**
 * Created by Vitaly Nikonorov on 18.03.17.
 * email@nikonorov.net
 */

public class MainTranslatorModelImpl implements MainTranslatorModel {

    @Inject
    YandexTranslatorAPI translatorAPI;

    public MainTranslatorModelImpl() {
        TranslatorApplication.component.inject(this);
    }
}
