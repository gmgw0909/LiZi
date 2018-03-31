package com.fancy.learncenter.net;

import android.util.Log;

import com.andview.refreshview.utils.LogUtils;
import com.fancy.learncenter.common.Constant;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hyy on 2017/12/1.
 * describe as
 */

public class IdeaApi {
    private final int DEFAULT_TIMEOUT = 15000;

    IdeaApiService apiService;

    private IdeaApi() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    String text = URLDecoder.decode(message, "utf-8");
                    Log.i("text", "```````" + text);

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    LogUtils.i(message + "是当发生的发生的");

                    Log.i("text", "`````afdasf``");
                }
            }
        });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
//                request.newBuilder()
//                        .addHeader("key","value");
                return chain.proceed(request);
            }
        };

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiService = retrofit.create(IdeaApiService.class);
    }

    //  创建单例
    private static class SingletonHolder {
        private static final IdeaApi INSTANCE = new IdeaApi();
    }

    public static IdeaApiService getApiService() {
        return SingletonHolder.INSTANCE.apiService;
    }
}
