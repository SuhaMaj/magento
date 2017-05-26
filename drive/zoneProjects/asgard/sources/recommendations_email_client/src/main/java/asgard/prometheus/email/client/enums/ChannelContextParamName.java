package asgard.prometheus.email.client.enums;

/**
 * Supported channel context parameter names.
 *
 */
public enum ChannelContextParamName {
    /**
     * Kohl's cash amount.
     */
    KOHLS_CASH_AMOUNT("kohlsCashAmount"),

    /**
     * Kohl's cash lower limit.
     */
    KOHLS_CASH_LOWER_LIMIT("kohlsCashLowerLimit"),

    /**
     * Kohl's cash upper limit.
     */
    KOHLS_CASH_UPPER_LIMIT("kohlsCashUpperLimit"),

    /**
     * Customer email address hash.
     */
    CUSTOMER_EMAIL_HASH("customerEmailHash"),

    /**
     * Product numbers.
     */
    PRODUCT_NUMBERS("productNumbers"),

    /**
     * Store numbers.
     */
    STORE_NUMBERS("shipNodes"),

    /**
     * Order number.
     */
    ORDER_NUMBER("orderNumber"),

    /**
     * Atg ID.
     */
    ATG_ID("atgId");

    private final String channelContextParamNameStrValue;

    private ChannelContextParamName(final String channelContextParamNameStrValue) {
        this.channelContextParamNameStrValue = channelContextParamNameStrValue;
    }

    /**
     * Provides the string representation of the enum constants.
     *
     * @return  The string representation of the given enum constant.
     */
    @Override
    public String toString() {
        return channelContextParamNameStrValue;
    }
}

