package com.example.musicapp.Service;

import retrofit2.Retrofit;

public class APIService {
    private static String base_url = "https://voduyquy.id.vn/Server/";

    public static Dataservice getservice(){
        return APIRetrofotClient.getClient(base_url).create(Dataservice.class);
    }
}
