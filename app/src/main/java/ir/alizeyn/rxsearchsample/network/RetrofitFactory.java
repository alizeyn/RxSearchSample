package ir.alizeyn.rxsearchsample.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import ir.alizeyn.rxsearchsample.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author alizeyn
 * Created at 8/13/19
 */
public class RetrofitFactory {

    private static HttpLoggingInterceptor loggingInterceptor;

    static {
        loggingInterceptor = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
    }

    public static final String BASE_URL = "https://moviesapi.ir/";

    private static Retrofit retrofit;
    final private static OkHttpClient client = new OkHttpClient().newBuilder()
            .readTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build();

    public static ApiServices getApiServices() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create(
                                    new GsonBuilder()
                                            .setFieldNamingPolicy(FieldNamingPolicy
                                                    .LOWER_CASE_WITH_UNDERSCORES)
                                            .create()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit.create(ApiServices.class);
    }
}
