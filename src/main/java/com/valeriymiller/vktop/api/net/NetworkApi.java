package com.valeriymiller.vktop.api.net;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.valeriymiller.vktop.model.User;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by valer on 26.10.2015.
 */
public class NetworkApi {
    private static final String URL = "https://api.vk.com";
    private static VKApiInterface service;

    public static VKApiInterface getNetworkApi() {
        if (service == null) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(User.class, new UserDeserializer())
                    .create();


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            service = retrofit.create(VKApiInterface.class);
        }
        return service;
    }
}
