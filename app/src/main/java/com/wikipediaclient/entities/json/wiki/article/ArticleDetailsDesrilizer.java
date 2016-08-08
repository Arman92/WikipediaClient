package com.wikipediaclient.entities.json.wiki.article;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

import retrofit2.Converter;

/**
 * Created by Arman on 2016-08-09.
 */
public class ArticleDetailsDesrilizer  implements JsonDeserializer<ArticleDetails> {
    public static Converter getGsonConverter() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(ArticleDetails.class, new ArticleDetailsDesrilizer())
                .create();
        return  null;
    }

    @Override
    public ArticleDetails deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        Gson gson = new Gson();
        ArticleDetails articleDetails = new ArticleDetails();
        JsonObject jsonObject = json.getAsJsonObject();

        return articleDetails;
    }
}
