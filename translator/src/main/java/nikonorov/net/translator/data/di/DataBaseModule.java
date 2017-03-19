package nikonorov.net.translator.data.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import nikonorov.net.translator.data.DBHelper;

/**
 * Created by Vitaly Nikonorov on 19.03.17.
 * email@nikonorov.net
 */
@Module
public class DataBaseModule {

    @Provides
    @Singleton
    DBHelper getDBHelper() {
        return new DBHelper();
    }
}
