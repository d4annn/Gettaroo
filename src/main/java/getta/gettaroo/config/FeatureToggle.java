package getta.gettaroo.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import fi.dy.masa.malilib.config.ConfigType;
import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.config.IConfigNotifiable;
import fi.dy.masa.malilib.config.IHotkeyTogglable;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBooleanConfigWithMessage;
import fi.dy.masa.malilib.hotkeys.KeybindMulti;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.util.StringUtils;
import getta.gettaroo.Gettaroo;
import getta.gettaroo.features.CarpinchoCallbackToggle;

public enum FeatureToggle implements IHotkeyTogglable, IConfigNotifiable<IConfigBoolean>{

    GRAVITY_ON_LAVA("gravityInLava", false, "", "When you are in lava you have incremented gravity"),
    GRAVITY_ON_WATER("murcianMode", false, "", "When you are in water you have incremented gravity"),
    SHOW_DEATH_SCREEN("dontShowDeathScreen", false, "", "When you die death screen isn't showed"),
    FAST_SHIFT("fastShift", false, "", "When you shift your speed isn't reduced"),
    STOP_IN_AIR("stopInAir", false, "", "Toggle of the hotkey of the key"),
    CLIENT_NAME("changeClientName", false, "", "Changes the name of your client"),
    WINDOW_NAME("changeWindowName", false, "", "Changes the name of your window"),
    HIDE_SCOREBOARD("hideScoreBoard", false, "", "Hides the scoreboard"),
    AUTO_RUN("autoRun", false, "", "When u are walking and you press this key you will auto walk/run"),
    LOCK_SLOT_ANTI_CHEAT("lockSlotCheaty", false, "", "If you use this with an anticheat you will get kick"),
    WEATHER_RENDERER("disableWeatherRender", false, "", "Disables the render of weather"),
    PORTAL_OUTSIDE_RENDER("portalOutsideRender", false, "", "Portal"),
    NEAR_ENTITIES_RENDER("nearEntitiesRender", false, "", "Renders an icon of the nearest entities"),
    DISABLE_RENDERING_ENTITIES("disableRenderingSelectedEntities", false, "", "pito"),
    FIRE_BETTER_RENDER("betterFireRender", false, "", "Disables the fire render and adds a number for the time that you are in fire \n change the postion at utils"),
    DISABLE_DAMAGE_SHAKE("disableDamageShake", false, "", "Disables the shake that you recieve when you take damage"),
    PIGS_ARE_FAT_CARPINCHOS("pigFatCarpinchos", false, "", "Changes pigs texture for a carpincho texture"),
    HOGLINS_ARE_FAT_CAPINCHOS("hoglinsFatCarpinchos", false, "", "Changes hoglins texture for a carpincho texture"),
    DISABLE_ARMORS("disableArmorRendering", false, "", "Disables de rendering of the armors");

    private final String name;
    private final String comment;
    private final String prettyName;
    private final IKeybind keybind;
    private final boolean defaultValueBoolean;
    private final boolean singlePlayer;
    private boolean valueBoolean;
    private IValueChangeCallback<IConfigBoolean> callback;

    private FeatureToggle(String name, boolean defaultValue, String defaultHotkey, String comment)
    {
        this(name, defaultValue, false, defaultHotkey, KeybindSettings.DEFAULT, comment);
    }

    private FeatureToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment)
    {
        this(name, defaultValue, singlePlayer, defaultHotkey, settings, comment, StringUtils.splitCamelCase(name.substring(5)));
    }

    private FeatureToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, String comment, String prettyName)
    {
        this(name, defaultValue, singlePlayer, defaultHotkey, KeybindSettings.DEFAULT, comment, prettyName);
    }

    private FeatureToggle(String name, boolean defaultValue, boolean singlePlayer, String defaultHotkey, KeybindSettings settings, String comment, String prettyName)
    {
        this.name = name;
        this.valueBoolean = defaultValue;
        this.defaultValueBoolean = defaultValue;
        this.singlePlayer = singlePlayer;
        this.comment = comment;
        this.prettyName = prettyName;
        this.keybind = KeybindMulti.fromStorageString(defaultHotkey, settings);
        if(name.equals("fatCarpinchosPigs")) {
            this.keybind.setCallback(new CarpinchoCallbackToggle(this));
        } else {
            this.keybind.setCallback(new KeyCallbackToggleBooleanConfigWithMessage(this));
        }
    }

    @Override
    public ConfigType getType()
    {
        return ConfigType.HOTKEY;
    }

    @Override
    public String getName()
    {
        return this.name;
    }

    @Override
    public String getConfigGuiDisplayName()
    {
        if (this.singlePlayer)
        {
            return GuiBase.TXT_GOLD + this.getName() + GuiBase.TXT_RST;
        }

        return this.getName();
    }

    @Override
    public String getPrettyName()
    {
        return this.prettyName;
    }

    @Override
    public String getStringValue()
    {
        return String.valueOf(this.valueBoolean);
    }

    @Override
    public String getDefaultStringValue()
    {
        return String.valueOf(this.defaultValueBoolean);
    }

    @Override
    public void setValueFromString(String value)
    {
    }

    @Override
    public void onValueChanged()
    {
        if (this.callback != null)
        {
            this.callback.onValueChanged(this);
        }
    }

    @Override
    public void setValueChangeCallback(IValueChangeCallback<IConfigBoolean> callback)
    {
        this.callback = callback;
    }

    @Override
    public String getComment()
    {
        if (this.comment == null)
        {
            return "";
        }

        if (this.singlePlayer)
        {
            return this.comment + "\n" + StringUtils.translate("getta.gettaroo.label.config_comment.single_player_only");
        }
        else
        {
            return this.comment;
        }
    }

    @Override
    public IKeybind getKeybind()
    {
        return this.keybind;
    }

    @Override
    public boolean getBooleanValue()
    {
        return this.valueBoolean;
    }

    @Override
    public boolean getDefaultBooleanValue()
    {
        return this.defaultValueBoolean;
    }

    @Override
    public void setBooleanValue(boolean value)
    {
        boolean oldValue = this.valueBoolean;
        this.valueBoolean = value;

        if (oldValue != this.valueBoolean)
        {
            this.onValueChanged();
        }
    }

    @Override
    public boolean isModified()
    {
        return this.valueBoolean != this.defaultValueBoolean;
    }

    @Override
    public boolean isModified(String newValue)
    {
        return Boolean.parseBoolean(newValue) != this.defaultValueBoolean;
    }

    @Override
    public void resetToDefault()
    {
        this.valueBoolean = this.defaultValueBoolean;
    }

    @Override
    public JsonElement getAsJsonElement()
    {
        return new JsonPrimitive(this.valueBoolean);
    }

    @Override
    public void setValueFromJsonElement(JsonElement element)
    {
        try
        {
            if (element.isJsonPrimitive())
            {
                this.valueBoolean = element.getAsBoolean();
            }
            else
            {
                Gettaroo.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element);
            }
        }
        catch (Exception e)
        {
            Gettaroo.logger.warn("Failed to set config value for '{}' from the JSON element '{}'", this.getName(), element, e);
        }
    }


}
