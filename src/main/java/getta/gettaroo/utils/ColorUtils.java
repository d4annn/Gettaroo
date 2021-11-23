package getta.gettaroo.utils;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;

public enum ColorUtils implements IConfigOptionListEntry {

        LIGHT_PURPLE("lightPurple", TextColor.fromFormatting(Formatting.LIGHT_PURPLE), "getta.gettaroo.label.colors.light_purple"),
        DARK_PURPLE("darkPurple", TextColor.fromFormatting(Formatting.DARK_PURPLE), "getta.gettaroo.label.colors.dark_purple"),
        BLUE("blue", TextColor.fromFormatting(Formatting.BLUE), "getta.gettaroo.label.colors.blue"),
        DARK_BLUE("darkBlue", TextColor.fromFormatting(Formatting.DARK_BLUE), "getta.gettaroo.label.colors.dark_blue"),
        AQUA("aqua", TextColor.fromFormatting(Formatting.AQUA), "getta.gettaroo.label.colors.aqua"),
        DARK_AQUA("darkAqua", TextColor.fromFormatting(Formatting.DARK_AQUA), "getta.gettaroo.label.colors.dark_aqua"),
        RED("red", TextColor.fromFormatting(Formatting.RED), "getta.gettaroo.label.colors.red"),
        DARK_RED("darkRed", TextColor.fromFormatting(Formatting.DARK_RED), "getta.gettaroo.label.colors.dark_red"),
        GREEN("green", TextColor.fromFormatting(Formatting.GREEN), "getta.gettaroo.label.colors.green"),
        DARK_GREEN("darkGreen", TextColor.fromFormatting(Formatting.DARK_GREEN), "getta.gettaroo.label.colors.dark_green"),
        YELLOW("yellow", TextColor.fromFormatting(Formatting.YELLOW), "getta.gettaroo.label.colors.yellow"),
        WHITE("white", TextColor.fromFormatting(Formatting.WHITE), "getta.gettaroo.label.colors.white"),
        BLACK("black", TextColor.fromFormatting(Formatting.BLACK), "getta.gettaroo.label.colors.black"),
        GOLD("gold", TextColor.fromFormatting(Formatting.GOLD), "getta.gettaroo.label.colors.gold"),
        GRAY("gray", TextColor.fromFormatting(Formatting.GRAY), "getta.gettaroo.label.colors.gray"),
        DARK_GRAY("darkGray", TextColor.fromFormatting(Formatting.DARK_GRAY), "getta.gettaroo.label.colors.dark_gray");


        private final String translationKey;
        private final String configString;
        private final TextColor color;

        private ColorUtils(String configString, TextColor color, String translationKey){
            this.translationKey = translationKey;
            this.configString = configString;
            this.color = color;
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
    public ColorUtils fromString(String value) {
        return fromStringStatic(value);
    }

    public static ColorUtils fromStringStatic(String name)
    {
        for (ColorUtils mode : ColorUtils.values())
        {
            if (mode.configString.equalsIgnoreCase(name))
            {
                return mode;
            }
        }

        return ColorUtils.LIGHT_PURPLE;
    }

    public TextColor getColor() {
        return color;
    }
}
