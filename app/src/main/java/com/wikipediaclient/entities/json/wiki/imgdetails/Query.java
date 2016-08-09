
package com.wikipediaclient.entities.json.wiki.imgdetails;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Query {

    @SerializedName("pages")
    @Expose
    private Pages pages;

    /**
     * 
     * @return
     *     The pages
     */
    public Pages getPages() {
        return pages;
    }

    /**
     * 
     * @param pages
     *     The pages
     */
    public void setPages(Pages pages) {
        this.pages = pages;
    }

}
