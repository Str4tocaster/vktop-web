package com.valeriymiller.vktop.api.net;

import com.valeriymiller.vktop.model.User;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by valer on 26.10.2015.
 */
public interface VKApiInterface {

    @GET("/method/users.get?")
    Call<User> getUserInfo(
            @Query("v") String version,
            @Query("access_token") String token
    );

}
