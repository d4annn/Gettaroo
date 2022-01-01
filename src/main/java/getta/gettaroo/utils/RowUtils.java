package getta.gettaroo.utils;

import fi.dy.masa.malilib.config.IConfigOptionListEntry;
import fi.dy.masa.malilib.util.StringUtils;

public enum RowUtils implements IConfigOptionListEntry {

    ROW_2("getta.gettaroo.row.2", "2"),
    ROW_3("getta.gettaroo.row.3", "3"),
    ROW_4("getta.gettaroo.row.4", "4");

    private String translationKey;
    private String row;

    private RowUtils(String translationKey, String row) {
        this.translationKey = translationKey;
        this.row = row;
    }

    @Override
    public String getStringValue() {
        return this.row;
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
    public IConfigOptionListEntry fromString(String value) {
        return fromStringStatic(value);
    }

    public static RowUtils fromStringStatic(String name)
    {
        for (RowUtils mode : RowUtils.values())
        {
            if (mode.row.equalsIgnoreCase(name))
            {
                return mode;
            }
        }

        return RowUtils.ROW_2;
    }
}
