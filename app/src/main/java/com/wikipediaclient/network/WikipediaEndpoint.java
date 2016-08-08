package com.wikipediaclient.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Arman on 2016-08-08.
 */
public interface WikipediaEndpoint {

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "User-Agent: Wikipedia-Client-App",
    })
    @GET("?action=query&format=json&prop=pageimages&piprop=original")
    Call<ResponseBody> getImages(@Query("titles") String articleName);


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json",
            "User-Agent: Wikipedia-Client-App",
    })
    @GET("?action=query&prop=extracts&exintro&format=json")
    Call<ResponseBody> getArticleDetails(@Query("titles") String articleName);
}
