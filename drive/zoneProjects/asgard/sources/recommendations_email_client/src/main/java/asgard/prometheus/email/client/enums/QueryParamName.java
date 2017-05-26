package asgard.prometheus.email.client.enums;

/**
 * Supported query parameter names.
 *
 */
public enum QueryParamName {
    /**
     * Channel Id.
     */
    CHANNEL_ID("cid"),
    /**
     * Email type.
     */
    EMAIL_TYPE("etype"),
    /**
     * Placement Id.
     */
    PLACEMENT_ID("plid"),
    /**
     * Position.
     */
    POSITION("pos"),
    /**
     * Channel context parameters.
     */
    CCP("ccp");

    private final String queryParamNameStrValue;

    private QueryParamName(final String queryParamNameStrValue) {
        this.queryParamNameStrValue = queryParamNameStrValue;
    }

    /**
     * Provides the string representation of the enum constants.
     *
     * @return  The string representation of the given enum constant.
     */
    @Override
    public String toString() {
        return queryParamNameStrValue;
    }
}