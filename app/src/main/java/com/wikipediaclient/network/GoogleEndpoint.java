package com.wikipediaclient.network;


import com.wikipediaclient.entities.json.google.suggestion.GoogleSuggestion;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by Arman on 2016-08-08.
 */
public interface GoogleEndpoint {
    @Headers({
            "Accept: */*",
            "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36\n"
    })
    @GET("v1?key=AIzaSyBYzr0bMmuq78cH7o0-tQiq_OTGXEYTrNE&cx=007693810685342571715:qnxhnw0h4g4")
    Call<GoogleSuggestion> getSuggestions(@Query("q") String searchCriteria);
}
