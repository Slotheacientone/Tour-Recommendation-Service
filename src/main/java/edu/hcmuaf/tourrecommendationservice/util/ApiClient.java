package edu.hcmuaf.tourrecommendationservice.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ApiClient {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final ExecutorService executor = Executors.newCachedThreadPool();
    private static final ApiClient mInstance = new ApiClient();

    private static OkHttpClient client;

    private ApiClient() {
        client = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpClient getClient(){
        return client;
    }

    public static CompletableFuture<Response> sendAsync(Request request) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                System.out.println("Send request: " + request);
                return client.newCall(request).execute();
            } catch (IOException exception) {
                System.out.println(exception.getMessage());
            }
            return null;
        }, executor);
    }

}
