package gettaroo.utils;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;

public enum DirectionUtils implements IConfigOptionListEntry {

    FORWARD("gettaroo.label.direction.forward", "forward"),
    BACK("gettaroo.label.direction.back", "back"),
    LEFT("gettaroo.label.direction.left", "left"),
    RIGHT("gettaroo.label.direction.right", "right");

    private final String translationKey;
    private final String configString;

    private DirectionUtils(String translationKey, String configString){
        this.translationKey = translationKey;
        this.configString = configString;
    }

    @Override
    public String getStringValue()
    {
        return this.configString;
    }

    @Override
    public String getDisplayName()
    {
        return StringUtils.translate(this.translationKey);
    }

    @Override
    public IConfigOptionListEntry cycle(boolean forward)
    {
        int id = this.ordinal();

        if (forward)
        {
            if (++id >= values().length)
            {
                id = 0;
            }
        }
        else
        {
            if (--id < 0)
            {
                id = values().length - 1;
            }
        }

        return values()[id % values().length];
    }

    @Override
    public DirectionUtils fromString(String value) {
        return fromStringStatic(value);
    }

    public static DirectionUtils fromStringStatic(String name)
    {
        for (DirectionUtils mode : DirectionUtils.values())
        {
            if (mode.configString.equalsIgnoreCase(name))
            {
                return mode;
            }
        }

        return DirectionUtils.FORWARD;
    }
}
