package getta.gettaroo.features;

public enum TrustedHosts {

    DISCORD("discord.com"),
    IMGUR("imgur.com"),
    PRNT("prnt.sc");

    private String host;

    private TrustedHosts(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }
}
