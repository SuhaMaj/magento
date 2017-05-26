package asgard.prometheus.email.client.urlgenerators.email;

import asgard.prometheus.email.client.dto.UrlResponse;
import asgard.prometheus.email.client.enums.ChannelId;
import asgard.prometheus.email.client.enums.EmailType;
import asgard.prometheus.email.client.enums.PlacementId;
import asgard.prometheus.email.client.exceptions.InvalidParameterException;

import java.util.List;

/**
 * A service that generates recommendation generation URLs to be embedded in emails.
 *
 */
public interface EmailUrlGenerator {
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
    public List<UrlResponse> generateKohlsCashBasedSingleRecoImageAndProductUrls(ChannelId channelId, //NOSONAR
                                                                                 EmailType emailType,
                                                                                 PlacementId placementId,
                                                                                 Integer position,
                                                                                 Double kohlsCashAmount,
                                                                                 Double kohlsCashLowerLimit,
                                                                                 Double kohlsCashUpperLimit,
                                                                                 String customerEmail) throws InvalidParameterException;

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
    public List<UrlResponse> generateShipmentSingleRecoImageAndProductUrls(ChannelId channelId,
                                                                           EmailType emailType,
                                                                           PlacementId placementId,
                                                                           Integer position,
                                                                           List<String> productNumbers,
                                                                           String customerEmail) throws InvalidParameterException;

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
    public List<UrlResponse> generatePriorToPickupBopusSingleRecoImageAndProductUrls(ChannelId channelId,
                                                                                     EmailType emailType,
                                                                                     PlacementId placementId,
                                                                                     Integer position,
                                                                                     List<String> productNumbers,
                                                                                     List<String> storeNumbers,
                                                                                     String customerEmail,
                                                                                     String orderNumber,
                                                                                     String atgId) throws InvalidParameterException;

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
    public List<UrlResponse> generateAfterPickupBopusSingleRecoImageAndProductUrls(ChannelId channelId,
                                                                                   EmailType emailType,
                                                                                   PlacementId placementId,
                                                                                   Integer position,
                                                                                   String customerEmail,
                                                                                   String atgId) throws InvalidParameterException;

}

