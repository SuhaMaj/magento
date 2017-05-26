package asgard.prometheus.email.client.utilities;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Provides methods that can be used to process REST endpoint query parameters.
 *
 */
public final class UrlParameterUtilities {
    //Logger instance
    private static final Logger LOGGER = LoggerFactory.getLogger(UrlParameterUtilities.class);

    //URL encoding and decoding codec instance
    private static final URLCodec URLCODEC = new URLCodec();

    //String constants
    private static final String QUERY_PARAM_NAME_VALUE_SEPARATOR = "=";
    private static final String QUERY_PARAM_SEPARATOR = "&";

    /**
     * Creates an instance of the UrlParameterUtilities.
     *
     */
    private UrlParameterUtilities() {
        //Private default constructor to prevent instantiation of this utilities class
    }

    /**
     * Generates a URL query parameter string using the provided query parameter names and values.
     *
     * @param queryParamValueMap    The query parameter name and value map.
     * @return                      The query parameter string including the provided parameter names and values
     */
    public static String generateQueryParamStringFromParamValueMap(Map<String, String> queryParamValueMap) {
        String queryParamString = "";

        if(MapUtils.isNotEmpty(queryParamValueMap)) {
            for (Map.Entry<String, String> entry : queryParamValueMap.entrySet())
            {
                //URL encode the query param value
                String encodedQueryParamValue = urlEncode(entry.getValue());

                //If the encoded value is not empty, add that query param to the query string
                if(StringUtils.isNotEmpty(encodedQueryParamValue)) {
                    queryParamString = StringUtils.join(queryParamString,
                            entry.getKey(),
                            QUERY_PARAM_NAME_VALUE_SEPARATOR,
                            encodedQueryParamValue,
                            QUERY_PARAM_SEPARATOR);
                }
            }
        }

        if(StringUtils.isNotEmpty(queryParamString)) {
            //Remove the final query param separator
            return queryParamString.substring(0, queryParamString.length() - 1);
        } else {
            return queryParamString;
        }
    }

    /**
     * Utility method used to encode a given string to an URL friendly string.
     *
     * @param messageToEncode   The string to encode to URL friendly format.
     * @return                  The URL friendly string if encoding succeeded, else null.
     */
    private static String urlEncode(String messageToEncode) {
        String urlEncodedBase64String = null;

        if(StringUtils.isNotEmpty(messageToEncode)) {
            try {
                urlEncodedBase64String = URLCODEC.encode(messageToEncode);
            } catch (EncoderException ex) {
                LOGGER.error("Error in encoding Base64 string to URL healthy Base64 string", ex);
            }
        }

        return urlEncodedBase64String;
    }
}

