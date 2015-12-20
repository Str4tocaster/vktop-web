package com.valeriymiller.vktop.api;


import com.valeriymiller.vktop.api.net.NetworkApi;
import com.valeriymiller.vktop.model.User;
import retrofit.Call;

/**
 * Created by valer on 26.10.2015.
 */
public class Api {
    private static final String VK_API_VERSION = "5.37";
    private static final int AUDIO_COUNT = 1000;

    public static User getUserInfo(String token) {
        Call<User> call = NetworkApi.getNetworkApi().getUserInfo(VK_API_VERSION, token);
        User user = null;

        try {
           user = call.execute().body();
        } catch (Exception e) {
            System.out.println("error:" + e);
        }
        return user;
    }
}
