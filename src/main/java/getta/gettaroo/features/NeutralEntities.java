package getta.gettaroo.features;

public enum NeutralEntities {

    BAT("bat"),
    CHICKEN("chicken"),
    COW("cow"),
    DONKEY("donkey"),
    FOX("fox"),
    HORSE("horse"),
    PIG("pig"),
    RABBIT("rabbit"),
    SALMON("salmon"),
    SHEEP("sheep"),
    SQUID("squid"),
    TROPICAL_FISH("tropical_fish"),
    BEE("bee"),
    DOLPHIN("dolphin"),
    LLAMA("llama"),
    PANDA("panda");

    private String name;

    private NeutralEntities(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
