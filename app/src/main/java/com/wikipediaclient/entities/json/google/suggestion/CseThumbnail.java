
package com.wikipediaclient.entities.json.google.suggestion;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class CseThumbnail {

    private String width;
    private String height;
    private String src;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The width
     */
    public String getWidth() {
        return width;
    }

    /**
     * 
     * @param width
     *     The width
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * 
     * @return
     *     The height
     */
    public String getHeight() {
        return height;
    }

    /**
     * 
     * @param height
     *     The height
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * 
     * @return
     *     The src
     */
    public String getSrc() {
        return src;
    }

    /**
     * 
     * @param src
     *     The src
     */
    public void setSrc(String src) {
        this.src = src;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
