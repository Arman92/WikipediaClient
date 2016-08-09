
package com.wikipediaclient.entities.json.google.suggestion;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Hcard {

    private String fn;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The fn
     */
    public String getFn() {
        return fn;
    }

    /**
     * 
     * @param fn
     *     The fn
     */
    public void setFn(String fn) {
        this.fn = fn;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
