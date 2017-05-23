package tm.fantom.newsviewer.deps;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import tm.fantom.newsviewer.BuildConfig;

/**
 * Created by fantom on 22-May-17.
 */

@Module
public class NetModule {
    private Context context;
    private static final int CONNECT_TIMEOUT = 45;
    private static final int WRITE_TIMEOUT = 45;
    private static final int READ_TIMEOUT = 45;
    private static final long CACHE_SIZE = 16 * 1024 * 1024; // 16 MB

    public NetModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    GsonConverterFactory provideGsonConverterFactory() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(
            GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .baseUrl(BuildConfig.END_POINT)
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(new OkHttpClient.Builder()
                        .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                        .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                        .addInterceptor(provideOfflineCacheInterceptor())
                        .addNetworkInterceptor(provideCacheInterceptor())
                        .cache(new Cache(context.getCacheDir(), CACHE_SIZE))
                        .build())
                .build();
    }

    @Provides
    @Singleton
    RestInterface providesRestService(Retrofit retrofit) {
        return retrofit.create(RestInterface.class);
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private Interceptor provideOfflineCacheInterceptor () {
        return chain -> {
            Request originalRequest = chain.request();
            String cacheHeaderValue = isNetworkConnected()
                    ? "public, max-age=2419200"
                    : "public, only-if-cached, max-stale=2419200" ;
            Request request = originalRequest.newBuilder().build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build();
        };
    }

    private Interceptor provideCacheInterceptor () {
        return chain -> {
            Request originalRequest = chain.request();
            String cacheHeaderValue = isNetworkConnected()
                    ? "public, max-age=2419200"
                    : "public, only-if-cached, max-stale=2419200" ;
            Request request = originalRequest.newBuilder().build();
            Response response = chain.proceed(request);
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", cacheHeaderValue)
                    .build();
        };
    }
}