package asgard.prometheus.email.client.enums;

/**
 * Supported Placement Ids.
 */
public enum PlacementId {
    /**
     * Horizontal placement.
     */
    HORIZONTAL("Horizontal");

    private final String placementIdStrValue;

    private PlacementId(final String placementIdStrValue) {
        this.placementIdStrValue = placementIdStrValue;
    }

    /**
     * Provides the string representation of the enum constants.
     *
     * @return  The string representation of the given enum constant.
     */
    @Override
    public String toString() {
        return placementIdStrValue;
    }
}