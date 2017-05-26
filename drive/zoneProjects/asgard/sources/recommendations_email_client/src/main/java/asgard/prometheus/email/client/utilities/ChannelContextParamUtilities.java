package asgard.prometheus.email.client.utilities;

import com.google.gson.Gson;
import asgard.prometheus.recommendationshbasekeymaker.RecommendationKey;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Provides methods that can be used to process channel context parameters.
 *
 */
public final class ChannelContextParamUtilities {
    //Logger instance
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelContextParamUtilities.class);

    //Character encoding used for the encoding
    private static final String UTF8 = "UTF-8";

    //Algorithm id key
    private static final String ALGORITHM_ID_KEY = "algorithmId";

    //Customer recommendations algorithm id
    private static final String CUST_REC_ALGO_ID = "135";

    //CCP key for customer email
    private static final String CUST_EMAIL_KEY = "customerEmail";

    /**
     * Creates an instance of the ChannelContextParamUtilities.
     *
     */
    private ChannelContextParamUtilities() {
        //Private default constructor to prevent instantiation of this utilities class
    }

    /**
     * Encodes the given channel context parameter map to a URL friendly format.
     *
     * @param ccp   Channel context parameter map to be encoded.
     * @return      The encoded channel context parameter map in URL friendly format.
     */
    public static String encodeCcpMapToUrlFormat(Map<String, String> ccp) {
        return serializeMapToBase64String(ccp);
    }

    /**
     * Utility method that can be used to convert a given Map to a Base64 encoded string.
     *
     * @param map   Map to be serialized to Base64 string.
     * @return      Base64 string representation of the map if serialization succeeded, else null.
     */
    private static String serializeMapToBase64String(Map<String, String> map) {
        String base64EncodedMap = null;

        if(MapUtils.isNotEmpty(map)) {
            try {
                //First convert the map to a JSON string
                String mapAsJsonString = new Gson().toJson(map);

                //Get the byte representation of the JSON string
                byte[] jsonInBytes = mapAsJsonString.getBytes(UTF8);

                //Convert to Base64
                base64EncodedMap = Base64.encodeBase64URLSafeString(jsonInBytes);
            } catch (UnsupportedEncodingException ex) {
                LOGGER.error("Error in serializing Map to Base64", ex);
            }
        }

        return base64EncodedMap;
    }

    /**
     * Hashes the given customer email address.
     *
     * @param customerEmail The clear text customer email address.
     * @return              The hashed customer email address.
     */
    public static String hashCustomerEmailAddress(String customerEmail) {
        String customerEmailHash = null;

        if(StringUtils.isNotEmpty(customerEmail)) {
            RecommendationKey recommendationKey = new RecommendationKey();

            //Set the algorithm id
            recommendationKey.setParameter(ALGORITHM_ID_KEY, CUST_REC_ALGO_ID);

            //Set customer id
            recommendationKey.setParameter(CUST_EMAIL_KEY, customerEmail);

            customerEmailHash = recommendationKey.hash();
        }

        return customerEmailHash;
    }
}

