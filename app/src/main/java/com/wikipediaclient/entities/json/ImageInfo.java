
package com.wikipediaclient.entities.json;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Imageinfo {

    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("descriptionurl")
    @Expose
    private String descriptionurl;
    @SerializedName("descriptionshorturl")
    @Expose
    private String descriptionshorturl;

    /**
     * 
     * @return
     *     The url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 
     * @param url
     *     The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 
     * @return
     *     The descriptionurl
     */
    public String getDescriptionurl() {
        return descriptionurl;
    }

    /**
     * 
     * @param descriptionurl
     *     The descriptionurl
     */
    public void setDescriptionurl(String descriptionurl) {
        this.descriptionurl = descriptionurl;
    }

    /**
     * 
     * @return
     *     The descriptionshorturl
     */
    public String getDescriptionshorturl() {
        return descriptionshorturl;
    }

    /**
     * 
     * @param descriptionshorturl
     *     The descriptionshorturl
     */
    public void setDescriptionshorturl(String descriptionshorturl) {
        this.descriptionshorturl = descriptionshorturl;
    }

}
