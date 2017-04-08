package nikonorov.net.translator.data.di;

import android.content.Context;
import android.support.annotation.NonNull;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nikonorov.net.translator.data.Repository;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */
@Module
public class DataBaseModule {

    private Context context;

    public DataBaseModule(@NonNull Context appContext) {
        this.context = appContext;
    }

    @Provides
    @Singleton
    Repository provideRepository() {
        return new Repository(context);
    }
}
