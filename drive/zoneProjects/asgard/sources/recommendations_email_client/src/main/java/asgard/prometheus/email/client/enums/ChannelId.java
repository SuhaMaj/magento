package asgard.prometheus.email.client.enums;


/**
 * Supported Channel Ids.
 */
public enum ChannelId {
    /**
     * Marketing email channel.
     */
    MKT_EMAIL("MktEmail"),
    TRANS_EMAIL("TransEmail"),
    DEMO_EMAIL("DemoEmail");

    private final String channelIdStrValue;

    private ChannelId(final String channelIdStrValue) {
        this.channelIdStrValue = channelIdStrValue;
    }

    /**
     * Provides the string representation of the enum constants.
     *
     * @return  The string representation of the given enum constant.
     */
    @Override
    public String toString() {
        return channelIdStrValue;
    }
}

