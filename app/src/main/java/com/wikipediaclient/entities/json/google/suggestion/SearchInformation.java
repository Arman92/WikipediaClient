
package com.wikipediaclient.entities.json.google.suggestion;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class SearchInformation {

    private Double searchTime;
    private String formattedSearchTime;
    private String totalResults;
    private String formattedTotalResults;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The searchTime
     */
    public Double getSearchTime() {
        return searchTime;
    }

    /**
     * 
     * @param searchTime
     *     The searchTime
     */
    public void setSearchTime(Double searchTime) {
        this.searchTime = searchTime;
    }

    /**
     * 
     * @return
     *     The formattedSearchTime
     */
    public String getFormattedSearchTime() {
        return formattedSearchTime;
    }

    /**
     * 
     * @param formattedSearchTime
     *     The formattedSearchTime
     */
    public void setFormattedSearchTime(String formattedSearchTime) {
        this.formattedSearchTime = formattedSearchTime;
    }

    /**
     * 
     * @return
     *     The totalResults
     */
    public String getTotalResults() {
        return totalResults;
    }

    /**
     * 
     * @param totalResults
     *     The totalResults
     */
    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    /**
     * 
     * @return
     *     The formattedTotalResults
     */
    public String getFormattedTotalResults() {
        return formattedTotalResults;
    }

    /**
     * 
     * @param formattedTotalResults
     *     The formattedTotalResults
     */
    public void setFormattedTotalResults(String formattedTotalResults) {
        this.formattedTotalResults = formattedTotalResults;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
