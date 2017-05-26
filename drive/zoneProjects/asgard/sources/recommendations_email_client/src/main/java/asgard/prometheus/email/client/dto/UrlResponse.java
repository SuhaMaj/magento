package asgard.prometheus.email.client.dto;

import asgard.prometheus.email.client.enums.UrlType;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Response to a URL generation API call.
 *
 */
public class UrlResponse {
    private String url;
    private UrlType urlType;

    /**
     * Get the URL
     *
     * @return The URL
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL
     *
     * @param url The URL
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Gets the URL type
     *
     * @return The URL type
     */
    public UrlType getUrlType() {
        return urlType;
    }

    /**
     * Sets the URL type
     *
     * @param urlType The URL type
     */
    public void setUrlType(UrlType urlType) {
        this.urlType = urlType;
    }

    /**
     * Returns a string representation of the object
     *
     * @return A string representation of the object
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
