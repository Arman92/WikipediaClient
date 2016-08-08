package com.wikipediaclient.network;

import com.wikipediaclient.entities.json.wiki.article.ArticleDetails;
import com.wikipediaclient.entities.json.wiki.images.WikiImages;
import com.wikipediaclient.entities.json.wiki.imgdetails.WikiImageDetails;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
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
    @GET("?action=query&format=json&prop=imageinfo&iilimit=50&iiprop=url")
    Call<WikiImageDetails> getImageDetails(@Query("titles") String fileName);


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
