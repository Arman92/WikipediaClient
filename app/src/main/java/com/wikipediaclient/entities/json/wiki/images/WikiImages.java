
package com.wikipediaclient.entities.json.wiki.images;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class WikiImages {

    private Continue _continue;
    private Query query;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The _continue
     */
    public Continue getContinue() {
        return _continue;
    }

    /**
     * 
     * @param _continue
     *     The continue
     */
    public void setContinue(Continue _continue) {
        this._continue = _continue;
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

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
