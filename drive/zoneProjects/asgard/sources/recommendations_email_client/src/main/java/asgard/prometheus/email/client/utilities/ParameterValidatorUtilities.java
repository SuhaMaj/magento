package asgard.prometheus.email.client.utilities;

import asgard.prometheus.email.client.enums.ChannelId;
import asgard.prometheus.email.client.enums.EmailType;
import asgard.prometheus.email.client.enums.PlacementId;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Provides methods that can be used to validate parameters passed into various methods.
 *
 */
public final class ParameterValidatorUtilities {
    //Constants defining the TCP port range min and max
    private static final int LOWEST_TCP_PORT = 0;
    private static final int HIGHEST_TCP_PORT = 65535;

    /**
     * Creates an instance of the ParameterValidatorUtilities.
     *
     */
    private ParameterValidatorUtilities() {
        //Private default constructor to prevent instantiation of this utilities class
    }

    /**
     * Validates the protocol to be either http or https.
     *
     * @param protocol  The protocol to be validated.
     * @return          True if protocol is either http or https, false otherwise.
     */
    public static boolean validateProtocolForHttpAndHttps(String protocol) {
        return "http".equalsIgnoreCase(protocol) || "https".equalsIgnoreCase(protocol);
    }

    /**
     * Validates the hostname to be not null and not an empty string.
     *
     * @param hostname  The hostname to ve validated.
     * @return          True if hostname is not null and not an empty string, false otherwise.
     */
    public static boolean validateHostnameForNonNullAndNonEmpty(String hostname) {
        return StringUtils.isNotEmpty(hostname);
    }

    /**
     * Validates the port to be in the valid TCP port range.
     *
     * @param port  The port to be validated.
     * @return      True if the port is in the valid TCP port range, false otherwise.
     */
    public static boolean validatePortForValidRange(int port) {
        return port >= LOWEST_TCP_PORT && port <= HIGHEST_TCP_PORT;
    }

    /**
     * Validates the recommendation position to be a valid non-negative position.
     *
     * @param position  The position to be validated.
     * @return          True if the position is a valid non-negative position, false otherwise.
     */
    public static boolean validateRecoPositionForPositivity(Integer position) {
        return position != null && position >= 0;
    }

    /**
     * Validates the channel id to be a non null value.
     *
     * @param channelId The channel id.
     * @return          True if channel id not null, false otherwise.
     */
    public static boolean validateChannelIdForNonNull(ChannelId channelId) {
        return channelId != null;
    }

    /**
     * Validates the email type to be a non null value.
     *
     * @param emailType The email type.
     * @return          True if the email type is not null, false otherwise.
     */
    public static boolean validateEmailTypeForNonNull(EmailType emailType) {
        return emailType != null;
    }

    /**
     * Validates the placement id to be a non null value.
     *
     * @param placementId   The placement id.
     * @return              True, if the placement id is not null, false otherwise.
     */
    public static boolean validatePlacementIdForNonNull(PlacementId placementId) {
        return placementId != null;
    }

    /**
     * Validates the email recommendations base URL to be a non-null and non empty string.
     *
     * @param emailRecommendationsBaseUrl   The email recommendation base URL to be validated.
     * @return                              True, if email recommendation base URL is non-null and non empty, false
     *                                      otherwise.
     */
    public static boolean validateEmailRecoBaseUrlForNonNullAndNonEmpty(String emailRecommendationsBaseUrl) {
        return StringUtils.isNotEmpty(emailRecommendationsBaseUrl);
    }

    /**
     * Validates the list of product numbers to be a non null and non empty list
     *
     * @param productNumbers    The list of product numbers.
     * @return                  True, if list of product numbers is non null and non empty, false otherwise.
     */
    public static boolean validateProductNumberListForNonNullAndNonEmpty(List<String> productNumbers) {
        return CollectionUtils.isNotEmpty(productNumbers);
    }

    /**
     * Validates the list of store numbers to be a non null and non empty list
     *
     * @param storeNumbers  The list of store numbers.
     * @return              True, if the list of store numbers in non null and non empty, false otherwise.
     */
    public static boolean validateStoreNumberListForNonNullAndNonEmpty(List<String> storeNumbers) {
        return CollectionUtils.isNotEmpty(storeNumbers);
    }

    /**
     * Validates the customer email address to be a non null and not an empty string
     *
     * @param customerEmail The customer email address.
     * @return              True, if the customer email address is non null and non empty, false otherwise.
     */
    public static boolean validateCustomerEmailAddressForNonNullAndNonEmpty(String customerEmail) {
        return StringUtils.isNotEmpty(customerEmail);
    }
}

