
package com.wikipediaclient.entities.json.wiki.images;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Continue {

    private String imcontinue;
    private String _continue;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The imcontinue
     */
    public String getImcontinue() {
        return imcontinue;
    }

    /**
     * 
     * @param imcontinue
     *     The imcontinue
     */
    public void setImcontinue(String imcontinue) {
        this.imcontinue = imcontinue;
    }

    /**
     * 
     * @return
     *     The _continue
     */
    public String getContinue() {
        return _continue;
    }

    /**
     * 
     * @param _continue
     *     The continue
     */
    public void setContinue(String _continue) {
        this._continue = _continue;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
