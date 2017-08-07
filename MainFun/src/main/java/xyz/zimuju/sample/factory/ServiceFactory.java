package xyz.zimuju.sample.factory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.zimuju.sample.provider.OkHttpProvider;

public class ServiceFactory {

    private final Gson mGson;
    private OkHttpClient mOkHttpClient;


    private ServiceFactory() {
        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").create();
        mOkHttpClient = OkHttpProvider.getDefaultOkHttpClient();

    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static ServiceFactory getNoCacheInstance() {
        ServiceFactory factory = SingletonHolder.INSTANCE;
        factory.mOkHttpClient = OkHttpProvider.getOkHttpClient();
        return factory;
    }

    public <S> S createService(Class<S> serviceClass) {
        String baseUrl = "";
        try {
            Field field = serviceClass.getField("BASE_URL");
            baseUrl = (String) field.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(mGson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private static class SingletonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }
}
