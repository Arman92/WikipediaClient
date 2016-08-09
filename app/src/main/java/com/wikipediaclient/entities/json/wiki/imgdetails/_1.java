
package com.wikipediaclient.entities.json.wiki.imgdetails;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class _1 {

    @SerializedName("ns")
    @Expose
    private Integer ns;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("missing")
    @Expose
    private String missing;
    @SerializedName("imagerepository")
    @Expose
    private String imagerepository;
    @SerializedName("imageinfo")
    @Expose
    private List<Imageinfo> imageinfo = new ArrayList<Imageinfo>();

    /**
     * 
     * @return
     *     The ns
     */
    public Integer getNs() {
        return ns;
    }

    /**
     * 
     * @param ns
     *     The ns
     */
    public void setNs(Integer ns) {
        this.ns = ns;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The missing
     */
    public String getMissing() {
        return missing;
    }

    /**
     * 
     * @param missing
     *     The missing
     */
    public void setMissing(String missing) {
        this.missing = missing;
    }

    /**
     * 
     * @return
     *     The imagerepository
     */
    public String getImagerepository() {
        return imagerepository;
    }

    /**
     * 
     * @param imagerepository
     *     The imagerepository
     */
    public void setImagerepository(String imagerepository) {
        this.imagerepository = imagerepository;
    }

    /**
     * 
     * @return
     *     The imageinfo
     */
    public List<Imageinfo> getImageinfo() {
        return imageinfo;
    }

    /**
     * 
     * @param imageinfo
     *     The imageinfo
     */
    public void setImageinfo(List<Imageinfo> imageinfo) {
        this.imageinfo = imageinfo;
    }

}
