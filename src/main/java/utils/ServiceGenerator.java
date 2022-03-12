
package utils;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import java.lang.reflect.Field;
import java.util.concurrent.TimeUnit;

public class ServiceGenerator {
    /**
     * Creates Retrofit Service.
     *
     * @param serviceClass Which service class (api data store) going to be used when creating Retrofit Service.
     * @return Created Retrofit Service.
     */
    public static <S> S generateService(Class<S> serviceClass) {

        String BASE_URL = (String) getFieldValue("BASE_URL", serviceClass);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(chain -> {
                    Request request = chain.request().newBuilder().build();
                    return chain.proceed(request);
                }).build();

        assert BASE_URL != null;
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(serviceClass);
    }
    public static <T> Object getFieldValue(String fieldName, Class<T> tClass) {
        try {
            Field field = tClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(tClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
