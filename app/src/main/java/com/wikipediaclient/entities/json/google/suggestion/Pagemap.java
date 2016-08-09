
package com.wikipediaclient.entities.json.google.suggestion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Pagemap {

    private List<Hcard> hcard = new ArrayList<Hcard>();
    private List<CseThumbnail> cseThumbnail = new ArrayList<CseThumbnail>();
    private List<Metatag> metatags = new ArrayList<Metatag>();
    private List<CseImage> cseImage = new ArrayList<CseImage>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The hcard
     */
    public List<Hcard> getHcard() {
        return hcard;
    }

    /**
     * 
     * @param hcard
     *     The hcard
     */
    public void setHcard(List<Hcard> hcard) {
        this.hcard = hcard;
    }

    /**
     * 
     * @return
     *     The cseThumbnail
     */
    public List<CseThumbnail> getCseThumbnail() {
        return cseThumbnail;
    }

    /**
     * 
     * @param cseThumbnail
     *     The cse_thumbnail
     */
    public void setCseThumbnail(List<CseThumbnail> cseThumbnail) {
        this.cseThumbnail = cseThumbnail;
    }

    /**
     * 
     * @return
     *     The metatags
     */
    public List<Metatag> getMetatags() {
        return metatags;
    }

    /**
     * 
     * @param metatags
     *     The metatags
     */
    public void setMetatags(List<Metatag> metatags) {
        this.metatags = metatags;
    }

    /**
     * 
     * @return
     *     The cseImage
     */
    public List<CseImage> getCseImage() {
        return cseImage;
    }

    /**
     * 
     * @param cseImage
     *     The cse_image
     */
    public void setCseImage(List<CseImage> cseImage) {
        this.cseImage = cseImage;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
