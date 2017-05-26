package asgard.prometheus.email.client.urlgenerators.email;

import asgard.prometheus.email.client.dto.UrlResponse;
import asgard.prometheus.email.client.enums.ChannelContextParamName;
import asgard.prometheus.email.client.enums.ChannelId;
import asgard.prometheus.email.client.enums.EmailType;
import asgard.prometheus.email.client.enums.PlacementId;
import asgard.prometheus.email.client.exceptions.InvalidParameterException;
import asgard.prometheus.email.client.utilities.ChannelContextParamUtilities;
import asgard.prometheus.email.client.utilities.ParameterValidatorUtilities;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the EmailUrlGenerator interface which generates recommendation generation URLs to be embedded in
 * emails that points to EDE (Experience Decision Engine) service.
 *
 */
public class EdeEmailUrlGenerator implements EmailUrlGenerator {
    //The protocol (either http or https)
    private String protocol;
    //The host
    private String hostname;
    //The port
    private int port;
    //Email recommendation base URL
    private String emailRecommendationsBaseUrl;

    //Reference to the single recommendation URL generator
    private SingleRecoEmailUrlGenerator singleRecoEmailUrlGenerator;

    //String constants
    private static final String PROTOCOL_HOSTNAME_SEPARATOR = "://";
    private static final String HOSTNAME_PORT_SEPARATOR = ":";
    private static final String URL_PATH_RESOURCE_SEPARATOR = "/";
    private static final String EDE_EMAIL_REC_BASE_PATH_RESOURCE = "v1/ede/email/recommendations";

    //Exception messages
    private static final String ERR_INVALID_CHANNEL_ID = "ChannelId cannot be null";
    private static final String ERR_INVALID_EMAIL_TYPE = "EmailType cannot be null";
    private static final String ERR_INVALID_PLACEMENT_ID = "PlacementId cannot be null";
    private static final String ERR_INVALID_PROTOCOL = "Protocol must be either http or https";
    private static final String ERR_INVALID_HOSTNAME = "Hostname cannot be null or an empty string";
    private static final String ERR_INVALID_PORT = "Port must be in the valid range 0 - 65535";
    private static final String ERR_INVALID_POSITION = "Position cannot be null or negative";
    private static final String ERR_INVALID_PROD_NUMBER_LIST = "Product numbers list cannot be null or empty";
    private static final String ERR_INVALID_STORE_NUMBER_LIST = "Store numbers list cannot be null or empty";
    private static final String ERR_INVALID_CUSTOMER_EMAIL = "Customer email cannot be null or an empty string";

    /**
     * Creates an instance of the EdeEmailUrlGenerator.
     *
     */
    private EdeEmailUrlGenerator() {
        //Private default constructor to ensure no sub-classing or instantiating from outside
    }

    /**
     * Creates an instance of the EdeEmailUrlGenerator.
     *
     * @param protocol  The protocol.
     * @param hostname  The hostname.
     * @param port      The port.
     */
    private EdeEmailUrlGenerator(String protocol, String hostname, int port) {
        this.protocol = protocol.toLowerCase();
        this.hostname = hostname.toLowerCase();
        this.port = port;
    }

    /**
     * Gets an instance of the EdeEmailUrlGenerator class.
     *
     * @param protocol  The protocol to be used (must be either http or https).
     * @param hostname  The hostname of the email recommendation generation service's host. Can also be the IP
     *                  address. (e.g. api-atg.kohls.com)
     * @param port      The port in which the email recommendation generation service is listening on. Pass value
     *                  0 if port need not be specified.
     * @return          The created instance of the EdeEmailUrlGenerator class.
     * @throws InvalidParameterException When one or more passed-in parameters are invalid.
     */
    public static EdeEmailUrlGenerator getInstance(String protocol, String hostname, int port) throws InvalidParameterException {
        //Validate protocol
        if(!ParameterValidatorUtilities.validateProtocolForHttpAndHttps(protocol)) {
            throw new InvalidParameterException(ERR_INVALID_PROTOCOL);
        }

        //Validate hostname
        if(!ParameterValidatorUtilities.validateHostnameForNonNullAndNonEmpty(hostname)) {
            throw new InvalidParameterException(ERR_INVALID_HOSTNAME);
        }

        //Validate port
        if(!ParameterValidatorUtilities.validatePortForValidRange(port)) {
            throw new InvalidParameterException(ERR_INVALID_PORT);
        }

        EdeEmailUrlGenerator emailUrlGenerator = new EdeEmailUrlGenerator(protocol, hostname, port);
        emailUrlGenerator.init();
        return emailUrlGenerator;
    }

    /*
    --------------------------------------------------------------------------------------------------------------------
        SONAR Notes:
        - Sonar analysis of the method signature of this method has been disabled since the Sonar analysis reports
          the number of parameters in this method is more than the allowed maximum of 7 according to Sonar. However,
          this method signature cannot be changed as the requirement for the method signature is specified by the
          client.
    --------------------------------------------------------------------------------------------------------------------
     */
    /**
     * Generates a list of Kohl's cash based recommended product's image & product request URLs
     * to retrieve one product per URL.
     *
     * @param channelId             Channel Id.
     * @param emailType             Email Type.
     * @param placementId           Placement Id.
     * @param position              Position of the image within the placement.
     *                              This must be based on a 0 indexed list. That is, first product is
     *                              in 0th position (position=0) and second product is in 1st position
     *                              (position=1) etc.
     * @param kohlsCashAmount       Kohl's cash amount.
     * @param kohlsCashLowerLimit   Specifies the lower limit (Inclusive) from Kohl's cash amount for products to
     *                              be recommended. Set to null if no lower limit needs to be applied, i.e. Kohl's cash
     *                              amount itself will be the lower limit.
     *                              E.g. If Kohl's cash amount is 30 and product's having a price >= 20 needs
     *                              to be recommended, kohlsCashLowerLimit = 10.
     * @param kohlsCashUpperLimit   Specifies the upper limit (Inclusive) from Kohl's cash amount for products to
     *                              be recommended. Set to null if no upper limit needs to be applied, i.e. Kohl's cash
     *                              amount itself will be the upper limit.
     *                              E.g. If Kohl's cash amount is 30 and product's having a price <= 75 needs
     *                              to be recommended, kohlsCashUpperLimit = 45.
     * @param customerEmail         Customer's email address.
     * @return                      A list of generated image & product request URLs.
     * @throws InvalidParameterException When one or more passed-in parameters are invalid.
     */
    @Override
    public List<UrlResponse> generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId channelId, //NOSONAR
                                                                                 EmailType emailType,
                                                                                 PlacementId placementId,
                                                                                 Integer position,
                                                                                 Double kohlsCashAmount,
                                                                                 Double kohlsCashLowerLimit,
                                                                                 Double kohlsCashUpperLimit,
                                                                                 String customerEmail) throws InvalidParameterException {
        //Validate the mandatory input parameters
        validateMandatoryInputParamsForEmailUrlGeneration(channelId, emailType, placementId, position);

        //Hash the customer email address
        String hashedCustomerEmailAddress = ChannelContextParamUtilities.hashCustomerEmailAddress(customerEmail);

        //Generate ccp map
        Map<String, String> ccpMap = generateCcpMapForKohlsCashEmailRecos(kohlsCashAmount,
                kohlsCashLowerLimit,
                kohlsCashUpperLimit,
                hashedCustomerEmailAddress);

        //Generate the single recommendation URLs
        List<UrlResponse> urlResponseList = new LinkedList<>();
        urlResponseList.add(this.singleRecoEmailUrlGenerator.generateSingleRecoImageUrl(channelId, emailType, placementId, position, ccpMap));
        urlResponseList.add(this.singleRecoEmailUrlGenerator.generateSingleRecoProductUrl(channelId, emailType, placementId, position, ccpMap));

        return urlResponseList;
    }


    /**
     * Generates a list of shipment based recommended product's image & product request URLs
     * to retrieve one product per URL. This method can be used for Partial Shipment and
     * Complete Shipment emails.
     *
     * @param channelId             Channel Id.
     * @param emailType             Email Type.
     * @param placementId           Placement Id.
     * @param position              Position of the image within the placement.
     *                              This must be based on a 0 indexed list. That is, first product is
     *                              in 0th position (position=0) and second product is in 1st position
     *                              (position=1) etc.
     * @param productNumbers        List of product numbers.
     * @param customerEmail         Customer's email address.
     * @return                      A list of generated image & product request URLs.
     * @throws InvalidParameterException When one or more passed-in parameters are invalid.
     */
    @Override
    public List<UrlResponse> generateShipmentSingleRecoImageAndProductUrls(ChannelId channelId,
                                                                           EmailType emailType,
                                                                           PlacementId placementId,
                                                                           Integer position,
                                                                           List<String> productNumbers,
                                                                           String customerEmail) throws InvalidParameterException {
        //Validate the mandatory input parameters
        validateMandatoryInputParamsForEmailUrlGeneration(channelId, emailType, placementId, position);

        //Hash the customer email address
        String hashedCustomerEmailAddress = ChannelContextParamUtilities.hashCustomerEmailAddress(customerEmail);

        //Generate ccp map
        Map<String, String> ccpMap = generateCcpMapForPartialShipmentEmailRecos(productNumbers, hashedCustomerEmailAddress);

        //Generate the single recommendation URLs
        List<UrlResponse> urlResponseList = new LinkedList<>();
        urlResponseList.add(this.singleRecoEmailUrlGenerator.generateSingleRecoImageUrl(channelId, emailType, placementId, position, ccpMap));
        urlResponseList.add(this.singleRecoEmailUrlGenerator.generateSingleRecoProductUrl(channelId, emailType, placementId, position, ccpMap));

        return urlResponseList;
    }

    /**
     * Generates a list of BOPUS prior to pick based recommended product's image & product request URLs
     * to retrieve one product per URL.
     *
     * @param channelId         Channel Id.
     * @param emailType         Email Type.
     * @param placementId       Placement Id.
     * @param position          Position of the image within the placement.
     *                          This must be based on a 0 indexed list. That is, first product is
     *                          in 0th position (position=0) and second product is in 1st position
     *                          (position=1) etc.
     * @param productNumbers    List of product numbers.
     * @param storeNumbers      List of store numbers.
     * @param customerEmail     Customer's email address.
     * @param orderNumber       Order number.
     * @param atgId             Atg Id.
     * @return                  A list of generated image & product request URLs.
     * @throws InvalidParameterException When one or more passed-in parameters are invalid.
     */
    @Override
    public List<UrlResponse> generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId channelId,
                                                                                     EmailType emailType,
                                                                                     PlacementId placementId,
                                                                                     Integer position,
                                                                                     List<String> productNumbers,
                                                                                     List<String> storeNumbers,
                                                                                     String customerEmail,
                                                                                     String orderNumber,
                                                                                     String atgId) throws InvalidParameterException {
        //Validate the general mandatory input parameters
        validateMandatoryInputParamsForEmailUrlGeneration(channelId, emailType, placementId, position);

        //Validate the specific mandatory input parameters for prior to pickup Bopus email URL generation
        validateSpecificMandatoryParamsForPriorToPickupBopusEmailUrlGeneration(productNumbers, storeNumbers, customerEmail);

        //Hash the customer email address
        String hashedCustomerEmailAddress = ChannelContextParamUtilities.hashCustomerEmailAddress(customerEmail);

        //Generate ccp map
        Map<String, String> ccpMap = generateCcpMapForPriorToPickupBopusEmailRecos(productNumbers,
                storeNumbers,
                hashedCustomerEmailAddress,
                orderNumber,
                atgId);

        //Generate the single recommendation URLs
        List<UrlResponse> urlResponseList = new LinkedList<>();
        urlResponseList.add(this.singleRecoEmailUrlGenerator.generateSingleRecoImageUrl(channelId, emailType, placementId, position, ccpMap));
        urlResponseList.add(this.singleRecoEmailUrlGenerator.generateSingleRecoProductUrl(channelId, emailType, placementId, position, ccpMap));

        return urlResponseList;
    }

    /**
     * Generates a list of BOPUS after pick based recommended product's image & product request URLs
     * to retrieve one product per URL.
     *
     * @param channelId         Channel Id.
     * @param emailType         Email Type.
     * @param placementId       Placement Id.
     * @param position          Position of the image within the placement.
     *                          This must be based on a 0 indexed list. That is, first product is
     *                          in 0th position (position=0) and second product is in 1st position
     *                          (position=1) etc.
     * @param customerEmail     Customer's email address.
     * @param atgId             Atg Id.
     * @return                  A list of generated image & product request URLs.
     * @throws InvalidParameterException When one or more passed-in parameters are invalid.
     */
    @Override
    public List<UrlResponse> generateAfterPickupBopusSingleRecoImageAndProductUrls(ChannelId channelId,
                                                                                   EmailType emailType,
                                                                                   PlacementId placementId,
                                                                                   Integer position,
                                                                                   String customerEmail,
                                                                                   String atgId) throws InvalidParameterException {
        //Validate the general mandatory input parameters
        validateMandatoryInputParamsForEmailUrlGeneration(channelId, emailType, placementId, position);

        //Validate the specific mandatory input parameters for after pickup Bopus email URL generation
        validateSpecificMandatoryParamsAfterPickupBopusEmailUrlGeneration(customerEmail);

        //Hash the customer email address
        String hashedCustomerEmailAddress = ChannelContextParamUtilities.hashCustomerEmailAddress(customerEmail);

        //Generate ccp map
        Map<String, String> ccpMap = generateCcpMapForAfterPickupBopusEmailRecos(hashedCustomerEmailAddress, atgId);

        //Generate the single recommendation URLs
        List<UrlResponse> urlResponseList = new LinkedList<>();
        urlResponseList.add(this.singleRecoEmailUrlGenerator.generateSingleRecoImageUrl(channelId, emailType, placementId, position, ccpMap));
        urlResponseList.add(this.singleRecoEmailUrlGenerator.generateSingleRecoProductUrl(channelId, emailType, placementId, position, ccpMap));

        return urlResponseList;
    }

    /**
     * Generates the channel context parameter map for Kohl's cash email recommendation URLs
     *
     * @param kohlsCashAmount       The Kohl's cash amount.
     * @param kohlsCashLowerLimit   The Kohl's cash lower limit.
     * @param kohlsCashUpperLimit   The Kohl's cash upper limit.
     * @param hashedCustomerEmail   The hashed customer email address.
     * @return                      The channel context parameter map for Kohl's cash email recommendation URLs
     */
    private static Map<String, String> generateCcpMapForKohlsCashEmailRecos(Double kohlsCashAmount,
                                                                            Double kohlsCashLowerLimit,
                                                                            Double kohlsCashUpperLimit,
                                                                            String hashedCustomerEmail) {
        Map<String, String> ccpMap = new HashMap<>();

        //Only add kohl's cash amount if not null
        if(kohlsCashAmount != null) {
            ccpMap.put(ChannelContextParamName.KOHLS_CASH_AMOUNT.toString(), kohlsCashAmount.toString());
        }

        //Only add kohl's cash lower limit if not null
        if(kohlsCashLowerLimit != null) {
            ccpMap.put(ChannelContextParamName.KOHLS_CASH_LOWER_LIMIT.toString(), kohlsCashLowerLimit.toString());
        }

        //Only add kohl's cash upper limit if not null
        if(kohlsCashUpperLimit != null) {
            ccpMap.put(ChannelContextParamName.KOHLS_CASH_UPPER_LIMIT.toString(), kohlsCashUpperLimit.toString());
        }

        //Only add email hash if not null and not empty
        if(StringUtils.isNotEmpty(hashedCustomerEmail)) {
            ccpMap.put(ChannelContextParamName.CUSTOMER_EMAIL_HASH.toString(), hashedCustomerEmail);
        }

        return ccpMap;
    }

    /**
     * Generates the channel context parameter map for Partial shipment email recommendation URLs.
     *
     * @param productNumbers        List of Product numbers.
     * @param hashedCustomerEmail   The hashed customer email address.
     * @return                      The channel context parameter map for Partial shipment email recommendation URLs.
     */
    private static Map<String, String> generateCcpMapForPartialShipmentEmailRecos(List<String> productNumbers, String hashedCustomerEmail) {
        Map<String, String> ccpMap = new HashMap<>();

        //Only add product number if not null and not empty
        if (CollectionUtils.isNotEmpty(productNumbers)) {
            ccpMap.put(ChannelContextParamName.PRODUCT_NUMBERS.toString(), StringUtils.join(productNumbers, ","));
        }

        //Only add email hash if not null and not empty
        if(StringUtils.isNotEmpty(hashedCustomerEmail)) {
            ccpMap.put(ChannelContextParamName.CUSTOMER_EMAIL_HASH.toString(), hashedCustomerEmail);
        }

        return ccpMap;
    }

    /**
     * Generates the channel context parameter map for BOPUS prior to pickup email recommendation URLs.
     *
     * @param productNumbers        List of product numbers.
     * @param storeNumbers          List of store numbers.
     * @param hashedCustomerEmail   The hashed customer email address.
     * @param orderNumber           Order number.
     * @param atgId                 Atg Id.
     * @return                      The channel context parameter map for BOPUS prior to pickup email recommendation URLs.
     */
    private static Map<String, String> generateCcpMapForPriorToPickupBopusEmailRecos(List<String> productNumbers,
                                                                                     List<String> storeNumbers,
                                                                                     String hashedCustomerEmail,
                                                                                     String orderNumber,
                                                                                     String atgId) {
        Map<String, String> ccpMap = new HashMap<>();

        //Product numbers cannot be empty, so directly add without any checking
        ccpMap.put(ChannelContextParamName.PRODUCT_NUMBERS.toString(), StringUtils.join(productNumbers, ","));

        //Store numbers cannot be empty, so directly add without any checking
        ccpMap.put(ChannelContextParamName.STORE_NUMBERS.toString(), StringUtils.join(storeNumbers, ","));

        //Hashed customer email cannot be null or empty, so directly add without any checking
        ccpMap.put(ChannelContextParamName.CUSTOMER_EMAIL_HASH.toString(), hashedCustomerEmail);

        //Only add order number if not null or empty
        if(StringUtils.isNotEmpty(orderNumber)) {
            ccpMap.put(ChannelContextParamName.ORDER_NUMBER.toString(), orderNumber);
        }

        //Only add Atg ID if not null or empty
        if(StringUtils.isNotEmpty(atgId)) {
            ccpMap.put(ChannelContextParamName.ATG_ID.toString(), atgId);
        }

        return ccpMap;
    }

    /**
     * Generates the channel context parameter map for BOPUS after pickup email recommendation URLs.
     *
     * @param hashedCustomerEmail   The hashed customer email address.
     * @param atgId                 Atg Id.
     * @return                      The channel context parameter map for BOPUS after pickup email recommendation URLs.
     */
    private static Map<String, String> generateCcpMapForAfterPickupBopusEmailRecos(String hashedCustomerEmail,
                                                                                   String atgId) {
        Map<String, String> ccpMap = new HashMap<>();

        //Hashed customer email cannot be null or empty, so directly add without any checking
        ccpMap.put(ChannelContextParamName.CUSTOMER_EMAIL_HASH.toString(), hashedCustomerEmail);

        //Only add Atg ID if not null or empty
        if(StringUtils.isNotEmpty(atgId)) {
            ccpMap.put(ChannelContextParamName.ATG_ID.toString(), atgId);
        }

        return ccpMap;
    }

    /**
     * Validates the mandatory input params required in email recommendation URL generation
     *
     * @param channelId     The channel id.
     * @param emailType     The email type.
     * @param placementId   The placement id.
     * @param position      The position.
     * @throws InvalidParameterException If anyone of the mandatory input parameters are invalid.
     */
    private static void validateMandatoryInputParamsForEmailUrlGeneration(ChannelId channelId,
                                                                          EmailType emailType,
                                                                          PlacementId placementId,
                                                                          Integer position) throws InvalidParameterException {
        //Validate channel id
        if(!ParameterValidatorUtilities.validateChannelIdForNonNull(channelId)) {
            throw new InvalidParameterException(ERR_INVALID_CHANNEL_ID);
        }

        //Validate email type
        if(!ParameterValidatorUtilities.validateEmailTypeForNonNull(emailType)) {
            throw new InvalidParameterException(ERR_INVALID_EMAIL_TYPE);
        }

        //Validate placement id
        if(!ParameterValidatorUtilities.validatePlacementIdForNonNull(placementId)) {
            throw new InvalidParameterException(ERR_INVALID_PLACEMENT_ID);
        }

        //Validate position
        if(!ParameterValidatorUtilities.validateRecoPositionForPositivity(position)) {
            throw new InvalidParameterException(ERR_INVALID_POSITION);
        }
    }

    /**
     * Validates BOPUS prior to pickup based email URL generation specific input parameters.
     *
     * @param productNumbers    List of product numbers.
     * @param storeNumbers      List of store numbers.
     * @param customerEmail     Customer's email address.
     * @throws InvalidParameterException If anyone of the mandatory input parameters are invalid.
     */
    private static void validateSpecificMandatoryParamsForPriorToPickupBopusEmailUrlGeneration(List<String> productNumbers,
                                                                                               List<String> storeNumbers,
                                                                                               String customerEmail) throws InvalidParameterException {
        //Validate product number list
        if(!ParameterValidatorUtilities.validateProductNumberListForNonNullAndNonEmpty(productNumbers)) {
            throw new InvalidParameterException(ERR_INVALID_PROD_NUMBER_LIST);
        }

        //Validate store number list
        if(!ParameterValidatorUtilities.validateStoreNumberListForNonNullAndNonEmpty(storeNumbers)) {
            throw new InvalidParameterException(ERR_INVALID_STORE_NUMBER_LIST);
        }

        //Validate customer email address
        if(!ParameterValidatorUtilities.validateCustomerEmailAddressForNonNullAndNonEmpty(customerEmail)) {
            throw new InvalidParameterException(ERR_INVALID_CUSTOMER_EMAIL);
        }
    }

    /**
     * Validates the BOPUS after pickup based email URL generation specific input parameters.
     *
     * @param customerEmail     Customer's email address.
     * @throws InvalidParameterException If anyone of the mandatory input parameters are invalid.
     */
    private static void validateSpecificMandatoryParamsAfterPickupBopusEmailUrlGeneration(String customerEmail) throws InvalidParameterException {
        //Validate customer email address
        if(!ParameterValidatorUtilities.validateCustomerEmailAddressForNonNullAndNonEmpty(customerEmail)) {
            throw new InvalidParameterException(ERR_INVALID_CUSTOMER_EMAIL);
        }
    }

    /**
     * Initializes the EdeEmailUrlGenerator.
     *
     */
    private void init() throws InvalidParameterException {
        generateEmailRecommendationsBaseUrl();
        this.singleRecoEmailUrlGenerator = EdeSingleRecoEmailUrlGenerator.getInstance(this.emailRecommendationsBaseUrl);
    }

    /**
     * Generates the email recommendations base URL.
     *
     */
    private void generateEmailRecommendationsBaseUrl() {
        if(this.port == 0) {
            //If port is 0 then do not add the port number after the hostname
            this.emailRecommendationsBaseUrl = StringUtils.join(this.protocol,
                    PROTOCOL_HOSTNAME_SEPARATOR,
                    this.hostname,
                    URL_PATH_RESOURCE_SEPARATOR,
                    EDE_EMAIL_REC_BASE_PATH_RESOURCE,
                    URL_PATH_RESOURCE_SEPARATOR);
        } else {
            //Here the port number is non 0, thus, add the port number after the hostname
            this.emailRecommendationsBaseUrl = StringUtils.join(this.protocol,
                    PROTOCOL_HOSTNAME_SEPARATOR,
                    this.hostname,
                    HOSTNAME_PORT_SEPARATOR,
                    this.port,
                    URL_PATH_RESOURCE_SEPARATOR,
                    EDE_EMAIL_REC_BASE_PATH_RESOURCE,
                    URL_PATH_RESOURCE_SEPARATOR);
        }
    }
}

