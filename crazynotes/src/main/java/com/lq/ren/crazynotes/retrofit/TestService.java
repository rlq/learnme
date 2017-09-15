package com.lq.ren.crazynotes.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Author lqren on 17/9/4.
 */

public class TestService {

    public static final String APP_URL = "https://requestb.in";//s0afe4s0

    public static class RequestData {

        public final String status_code;
        public final String content;

        public RequestData(String status, String content) {
            this.status_code = status;
            this.content = content;
        }

    }

    public interface RequestClient {

        @GET("/{version}")
        Call<RequestData> getData(@Path("version") String version);

        @GET("/{version}")
        Call<String> getDatas(@Path("version") String version);
    }

    public static void main(String... args) throws IOException {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APP_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RequestClient client = retrofit.create(RequestClient.class);

        Call<String> call = client.getDatas("s0afe4s0");
        System.out.print(call.execute().body());

    }

}
