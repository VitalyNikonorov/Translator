package nikonorov.net.translator.network.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nikonorov.net.translator.network.YandexTranslatorAPI;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

@Module
public class NetworkModule {
    private static final String BASE_URL = "https://translate.yandex.net/";

    @Provides
    @Singleton
    YandexTranslatorAPI getTranslatorApi() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(
                                GsonConverterFactory.create()
                        );

        Retrofit retrofit = builder
                        .client(
                                httpClient.build()
                        )
                        .build();

        return retrofit.create(YandexTranslatorAPI.class);
    }
}
