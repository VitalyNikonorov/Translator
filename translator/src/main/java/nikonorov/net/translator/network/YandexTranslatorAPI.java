package nikonorov.net.translator.network;

import nikonorov.net.translator.network.model.GetLangsResult;
import nikonorov.net.translator.network.model.TranslationResult;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

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

    @GET("requestLanguages")
    Observable<GetLangsResult> getLangs(
            @Query("key") String key,
            @Query("ui") String ui
    );
}
