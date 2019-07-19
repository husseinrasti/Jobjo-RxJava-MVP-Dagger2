package ir.hosseinrasti.app.jobjo.di.module;

import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import ir.hosseinrasti.app.jobjo.data.network.ApiService;
import ir.hosseinrasti.app.jobjo.utils.URLs;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Hossein on 5/25/2018.
 */

@Module
public class ApiModule {

    private int REQUEST_TIMEOUT = 60;

    @Provides
    public OkHttpClient provideClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel( HttpLoggingInterceptor.Level.BODY );

        return new OkHttpClient.Builder().connectTimeout( REQUEST_TIMEOUT, TimeUnit.SECONDS )

                .readTimeout( REQUEST_TIMEOUT, TimeUnit.SECONDS )
                .writeTimeout( REQUEST_TIMEOUT, TimeUnit.SECONDS ).addInterceptor( interceptor ).build();
    }

    @Provides
    public Retrofit provideRetrofit( String baseURL, OkHttpClient client ) {
        return new Retrofit.Builder()
                .baseUrl( baseURL )
                .client( client )
                .addConverterFactory( GsonConverterFactory.create() )
                .addCallAdapterFactory( RxJava2CallAdapterFactory.create() )
                .build();
    }

    @Provides
    public ApiService provideApiService() {
        return provideRetrofit( URLs.SERVER, provideClient() ).create( ApiService.class );
    }

}
