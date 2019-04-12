package com.rootsoftit.pensiontracker.data;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    public static final String BASE_URL = "https://pension.hostfxbd.com";
    public static final String API_KEY = "AIzaSyDa6Rsd-NlUfK75N-1zkacvVB8TS3sR3s0";
    private static Api instance = null;
    //    public static final String BASE_URL = "http://127.0.0.1:8001/";
    // Keep your services here, build them in buildRetrofit method later
    private PensionClient pensionClient;

    private Api() {
        buildRetrofit();
    }

    public static Api getInstance() {
        if (instance == null) {
            instance = new Api();
        }

        return instance;
    }

    private void buildRetrofit() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor((new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder requestBuilder = chain.request().newBuilder();
                        requestBuilder.addHeader("Content-Type", "application/json");
                        requestBuilder.addHeader("Accept", "application/json");

                        if (Session.getToken() != null) {
                            requestBuilder.addHeader("Authorization", "Bearer " + Session.getToken());
                        }
                        return chain.proceed(requestBuilder.build());
                    }
                }))
                .addNetworkInterceptor(logging)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        // Build your services once
        this.pensionClient = retrofit.create(PensionClient.class);
    }

    public PensionClient getPensionClient() {
        return this.pensionClient;
    }
}