package asgard.prometheus.email.client.urlgenerators.email;

import asgard.prometheus.email.client.dto.UrlResponse;
import asgard.prometheus.email.client.enums.ChannelId;
import asgard.prometheus.email.client.enums.EmailType;
import asgard.prometheus.email.client.enums.PlacementId;

import java.util.Map;

/*
NOTE:
    If this interface is exposed to the outside in future, ensure to throw InvalidParameterException from the methods
    to enforce checked exceptions.
 */
/**
 * A service that generates URLs to be embedded in emails with each generated URL retrieving a single
 * recommended product.
 *
 */
interface SingleRecoEmailUrlGenerator {
    /**
     * Generates a URL which will retrieve a single recommended product's image when invoked.
     *
     * @param channelId                     Channel Id.
     * @param emailType                     Email Type.
     * @param placementId                   Placement Id.
     * @param position                      Position of the image within the placement.
     *                                      This must be based on a 0 indexed list. That is, first product is
     *                                      in 0th position (position=0) and second product is in 1st position
     *                                      (position=1) etc.
     * @param ccp                           Channel context parameter map.
     * @return                              Generated image URL.
     */
    public UrlResponse generateSingleRecoImageUrl(ChannelId channelId,
                                                  EmailType emailType,
                                                  PlacementId placementId,
                                                  Integer position,
                                                  Map<String, String> ccp);

    /**
     * Generates a URL which will re-direct to a single recommended product's PDP page when invoked.
     *
     * @param channelId                     Channel Id.
     * @param emailType                     Email Type.
     * @param placementId                   Placement Id.
     * @param position                      Position of the image within the placement.
     *                                      This must be based on a 0 indexed list. That is, first product is
     *                                      in 0th position (position=0) and second product is in 1st position
     *                                      (position=1) etc.
     * @param ccp                           Channel context parameter map.
     * @return                              Generated product re-direction URL.
     */
    public UrlResponse generateSingleRecoProductUrl(ChannelId channelId,
                                                    EmailType emailType,
                                                    PlacementId placementId,
                                                    Integer position,
                                                    Map<String, String> ccp);
}

