
package com.wikipediaclient.entities.json.wiki.imgdetails;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class WikiImageDetails {

    @SerializedName("batchcomplete")
    @Expose
    private String batchcomplete;
    @SerializedName("query")
    @Expose
    private Query query;

    /**
     * 
     * @return
     *     The batchcomplete
     */
    public String getBatchcomplete() {
        return batchcomplete;
    }

    /**
     * 
     * @param batchcomplete
     *     The batchcomplete
     */
    public void setBatchcomplete(String batchcomplete) {
        this.batchcomplete = batchcomplete;
    }

    /**
     * 
     * @return
     *     The query
     */
    public Query getQuery() {
        return query;
    }

    /**
     * 
     * @param query
     *     The query
     */
    public void setQuery(Query query) {
        this.query = query;
    }

}
