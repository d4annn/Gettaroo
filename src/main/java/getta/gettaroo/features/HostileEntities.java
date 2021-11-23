package getta.gettaroo.features;

public enum HostileEntities {

    ZOMBIE("zombie"),
    SPIDER("spider"),
    WITCH("witch"),
    BLAZE("blaze"),
    CREEPER("creeper"),
    DROWNED("drowned"),
    GHAST("ghast"),
    GUARDIAN("guardian"),
    HOGLIN("hoglin"),
    HUSK("husk"),
    MAGMA_CUBE("magma_cube"),
    SHULKER("shulker"),
    SKELETON("skeleton"),
    SLIME("slime"),
    WITHER_SKELETON("wither_skeleton"),
    ENDERMAN("enderman"),
    ZOGLIN("zoglin");

    private String name;

    private HostileEntities(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
