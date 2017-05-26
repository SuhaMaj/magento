package asgard.prometheus.email.client.urlgenerators.email;

import asgard.prometheus.email.client.dto.UrlResponse;
import asgard.prometheus.email.client.enums.*;
import asgard.prometheus.email.client.exceptions.InvalidParameterException;
import asgard.prometheus.email.client.utilities.ChannelContextParamUtilities;
import asgard.prometheus.email.client.utilities.ParameterValidatorUtilities;
import asgard.prometheus.email.client.utilities.UrlParameterUtilities;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Implementation of the SingleRecoEmailUrlGenerator interface.
 *
 */
class EdeSingleRecoEmailUrlGenerator implements SingleRecoEmailUrlGenerator {
    //Single image recommendation base URL
    private String singleImageRecBaseUrl;
    //Single product recommendation base URL
    private String singleProductRecBaseUrl;

    //Single recommendation base URL resource paths
    private static final String SINGLE_IMAGE_REC_BASE_PATH_RESOURCE = "imgreq/single";
    private static final String SINGLE_PRODUCT_REC_BASE_PATH_RESOURCE = "prdreq/single";

    //Error messages
    private static final String ERR_INVALID_EMAIL_RECO_BASE_URL = "Email recommendation base URL cannot be null or an empty string";

    //Constants
    private static final String RESOURCE_PATH_QUERY_PARAM_SEPARATOR = "?";

    /**
     * Creates an instance of the EdeSingleRecoEmailUrlGenerator.
     */
    private EdeSingleRecoEmailUrlGenerator() {
        //Private default constructor to ensure no sub-classing or instantiating from outside
    }

    /**
     * Creates an instance of the EdeSingleRecoEmailUrlGenerator.
     *
     * @param singleImageRecBaseUrl     The single image request base URL.
     * @param singleProductRecBaseUrl   The single product request base URL.
     */
    private EdeSingleRecoEmailUrlGenerator(String singleImageRecBaseUrl, String singleProductRecBaseUrl) {
        this.singleImageRecBaseUrl = singleImageRecBaseUrl;
        this.singleProductRecBaseUrl = singleProductRecBaseUrl;
    }

    /**
     * Gets an instance of the EdeSingleRecoEmailUrlGenerator class.
     *
     * @param emailRecommendationsBaseUrl   The email recommendations base URL.
     * @return                              The EdeSingleRecoEmailUrlGenerator instance.
     * @throws InvalidParameterException    When one or more passed-in parameters are invalid.
     */
    public static EdeSingleRecoEmailUrlGenerator getInstance(String emailRecommendationsBaseUrl) throws InvalidParameterException {
        //Validate the passed in email recommendation base URL
        if(!ParameterValidatorUtilities.validateEmailRecoBaseUrlForNonNullAndNonEmpty(emailRecommendationsBaseUrl)) {
            throw new InvalidParameterException(ERR_INVALID_EMAIL_RECO_BASE_URL);
        }

        //Prepare the base URLs
        String singleImageRecBaseUrl = StringUtils.join(emailRecommendationsBaseUrl,
                SINGLE_IMAGE_REC_BASE_PATH_RESOURCE);
        String singleProductRecBaseUrl = StringUtils.join(emailRecommendationsBaseUrl,
                SINGLE_PRODUCT_REC_BASE_PATH_RESOURCE);

        return new EdeSingleRecoEmailUrlGenerator(singleImageRecBaseUrl, singleProductRecBaseUrl);
    }

    /**
     * Generates a URL which will retrieve a single recommended product's image when invoked.
     *
     * @param channelId                   Channel Id.
     * @param emailType                   Email Type.
     * @param placementId                 Placement Id.
     * @param position                    Position of the image within the placement.
     *                                    This must be based on a 0 indexed list. That is, first product is
     *                                    in 0th position (position=0) and second product is in 1st position
     *                                    (position=1) etc.
     * @param ccp                         Channel context parameter map.
     * @return                            Generated image URL.
     */
    @Override
    public UrlResponse generateSingleRecoImageUrl(ChannelId channelId,
                                                  EmailType emailType,
                                                  PlacementId placementId,
                                                  Integer position,
                                                  Map<String, String> ccp) {
        //Prepare the query parameter string
        String queryParamString = generateQueryParamString(channelId, emailType, placementId, position, ccp);

        //Prepare the final URL
        String singleRecoImageUrl = this.singleImageRecBaseUrl;
        if(StringUtils.isNotEmpty(queryParamString)) {
            singleRecoImageUrl = StringUtils.join(singleRecoImageUrl,
                    RESOURCE_PATH_QUERY_PARAM_SEPARATOR,
                    queryParamString);
        }

        //Prepare the URL response object and return
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrl(singleRecoImageUrl);
        urlResponse.setUrlType(UrlType.EMAIL_SINGLE_IMG_REQ);
        return urlResponse;
    }

    /**
     * Generates a URL which will re-direct to a single recommended product's PDP page when invoked.
     *
     * @param channelId                   Channel Id.
     * @param emailType                   Email Type.
     * @param placementId                 Placement Id.
     * @param position                    Position of the image within the placement.
     *                                    This must be based on a 0 indexed list. That is, first product is
     *                                    in 0th position (position=0) and second product is in 1st position
     *                                    (position=1) etc.
     * @param ccp                         Channel context parameter map.
     * @return                            Generated product re-direction URL.
     */
    @Override
    public UrlResponse generateSingleRecoProductUrl(ChannelId channelId,
                                                    EmailType emailType,
                                                    PlacementId placementId,
                                                    Integer position,
                                                    Map<String, String> ccp) {
        //Prepare the query parameter string
        String queryParamString = generateQueryParamString(channelId, emailType, placementId, position, ccp);

        //Prepare the final URL
        String singleRecoProductUrl = this.singleProductRecBaseUrl;
        if(StringUtils.isNotEmpty(queryParamString)) {
            singleRecoProductUrl = StringUtils.join(singleRecoProductUrl,
                    RESOURCE_PATH_QUERY_PARAM_SEPARATOR,
                    queryParamString);
        }

        //Prepare the URL response object and return
        UrlResponse urlResponse = new UrlResponse();
        urlResponse.setUrl(singleRecoProductUrl);
        urlResponse.setUrlType(UrlType.EMAIL_SINGLE_PROD_REQ);
        return urlResponse;
    }

    /**
     * Generates the query parameter string
     *
     * @param channelId     The channel id.
     * @param emailType     The email type.
     * @param placementId   The placement id.
     * @param position      The position.
     * @param ccp           The channel context parameters.
     * @return              The query parameter string.
     */
    private String generateQueryParamString(ChannelId channelId,
                                            EmailType emailType,
                                            PlacementId placementId,
                                            Integer position,
                                            Map<String, String> ccp) {
        //Encode channel context params
        String encodedCcp = ChannelContextParamUtilities.encodeCcpMapToUrlFormat(ccp);

        //Prepare the query parameter string
        Map<String, String> queryParamNameValueMap = new LinkedHashMap<>();
        queryParamNameValueMap.put(QueryParamName.CHANNEL_ID.toString(), channelId.toString());
        queryParamNameValueMap.put(QueryParamName.EMAIL_TYPE.toString(), emailType.toString());
        queryParamNameValueMap.put(QueryParamName.PLACEMENT_ID.toString(), placementId.toString());
        queryParamNameValueMap.put(QueryParamName.POSITION.toString(), position.toString());
        if(StringUtils.isNotEmpty(encodedCcp)) {
            queryParamNameValueMap.put(QueryParamName.CCP.toString(), encodedCcp);
        }

        return UrlParameterUtilities.generateQueryParamStringFromParamValueMap(queryParamNameValueMap);
    }
}

