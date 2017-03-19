package nikonorov.net.translator.network;

import io.reactivex.Observable;
import nikonorov.net.translator.network.model.TranslationResult;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

public interface YandexTranslatorAPI {

    @GET("translate")
    Observable<TranslationResult> translate(
            @Query("key") String key,
            @Query("text") String text,
            @Query("lang") String lang
    );
}
