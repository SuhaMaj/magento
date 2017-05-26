package asgard.prometheus.email.client.dto;

import asgard.prometheus.email.client.enums.UrlType;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Unit tests to verify the functionality of the UrlResponse class
 *
 */
public class UrlResponseTest {
    /**
     * Should set the url attribute properly and when retrieved it should be the same url
     *
     */
    @Test
    public void should_set_and_retrieve_url_properly() {
        String url = "Some URL";
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrl(url);
        assertEquals(url, urlResponse.getUrl());
    }

    /**
     * Should set the urlType attribute properly and when retrieved it should be the same
     * urlType
     *
     */
    @Test
    public void should_set_and_retrieve_urlType_properly() {
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrlType(UrlType.EMAIL_SINGLE_IMG_REQ);
        assertEquals(UrlType.EMAIL_SINGLE_IMG_REQ, urlResponse.getUrlType());
    }

    /**
     * UrlResponse object details should be properly included in the classes toString method
     *
     */
    @Test
    public void should_return_the_UrlResponse_object_details_properly_when_toString_is_called() {
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrl("Some URL");
        urlResponse.setUrlType(UrlType.EMAIL_SINGLE_PROD_REQ);
        String toAssert = "[url=Some URL,urlType=EMAIL_SINGLE_PROD_REQ]";

        assertTrue(urlResponse.toString().contains(toAssert));
    }
}

