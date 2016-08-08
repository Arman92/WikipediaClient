package com.wikipediaclient.network;

import com.wikipediaclient.entities.json.WikiImageDetails;

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
            "User-Agent: Wikipedia-Client-App"
    })
    @GET("?action=query&format=json&prop=imageinfo&iilimit=50&iiprop=url")
    Call<WikiImageDetails> getImageDetails(@Query("titles") String fileName);
}
