package nikonorov.net.translator.network.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nikonorov.net.translator.network.YandexTranslatorAPI;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */

@Module
public class NetworkModule {
    private static final String BASE_URL = "https://translate.yandex.net/api/v1.5/tr.json/";

    @Provides
    @Singleton
    YandexTranslatorAPI getTranslatorApi() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        return retrofit.create(YandexTranslatorAPI.class);
    }
}
