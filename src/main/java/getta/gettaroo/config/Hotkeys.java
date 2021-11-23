package getta.gettaroo.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;

import java.util.List;

public class Hotkeys {

    public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGui","G,C", "The key open the in-game config GUI");
    public static final ConfigHotkey STOP_IN_AIR = new ConfigHotkey("stopInAir", "", KeybindSettings.PRESS_ALLOWEXTRA, "Allows you to stay in air");
    public static final ConfigHotkey FAST_DISCONNECT = new ConfigHotkey("fastDisconnect", "", KeybindSettings.PRESS_ALLOWEXTRA, "Instadisconnects you when pressed, BE CAREFUL !");

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            OPEN_CONFIG_GUI,
            STOP_IN_AIR,
            FAST_DISCONNECT
    );

}
