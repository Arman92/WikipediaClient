
package com.wikipediaclient.entities.json.wiki.images;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class _736 {

    private Integer pageid;
    private Integer ns;
    private String title;
    private List<Image> images = new ArrayList<Image>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The pageid
     */
    public Integer getPageid() {
        return pageid;
    }

    /**
     * 
     * @param pageid
     *     The pageid
     */
    public void setPageid(Integer pageid) {
        this.pageid = pageid;
    }

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
     *     The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     * 
     * @param images
     *     The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
