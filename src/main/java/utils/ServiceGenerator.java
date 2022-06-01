
package utils;

import okhttp3.Headers;
import okhttp3.Request;
import java.util.Objects;
import retrofit2.Retrofit;
import okhttp3.OkHttpClient;
import java.util.concurrent.TimeUnit;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.wire.WireConverterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.protobuf.ProtoConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class ServiceGenerator {

    Headers headers = new Headers.Builder().build();
    private final Printer log = new Printer(ServiceGenerator.class);

    public ServiceGenerator(Headers headers) {setHeaders(headers);}

    public ServiceGenerator(){}

    /**
     * Creates Retrofit Service.
     *
     * @param serviceClass Which service class (api data store) going to be used when creating Retrofit Service.
     * @return Created Retrofit Service.
     */
    public <S> S generate(Class<S> serviceClass) {

        String BASE_URL = (String) new ObjectUtilities().getFieldValue("BASE_URL", serviceClass);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        HttpLoggingInterceptor headerInterceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(headerInterceptor)
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .addNetworkInterceptor(chain -> {
                    Request request = chain.request().newBuilder().build();
                    request = request.newBuilder()
                            .header("Host", request.url().host())
                            .method(request.method(), request.body())
                            .build();
                    for (String header: headers.names()) {
                        if (!request.headers().names().contains(header)){
                            request = request.newBuilder()
                                    .addHeader(header, Objects.requireNonNull(headers.get(header)))
                                    .build();
                        }
                    }
                    if (request.body() != null) {
                        request = request.newBuilder()
                                .header("Content-Length", String.valueOf(Objects.requireNonNull(request.body()).contentLength()))
                                .header("Content-Type", String.valueOf(Objects.requireNonNull(request.body()).contentType()))
                                .build();
                    }
                    log.new Info("Headers(" + request.headers().size() + "): \n\n" + request.headers());
                    return chain.proceed(request);
                }).build();

        assert BASE_URL != null;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
                .addConverterFactory(WireConverterFactory.create())
                .addConverterFactory(ProtoConverterFactory.create())
                .client(client)
                .build();
        return retrofit.create(serviceClass);
    }

    public void setHeaders(Headers headers){this.headers = headers;}
}