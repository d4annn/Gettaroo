package getta.gettaroo.config;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.config.options.ConfigHotkey;
import fi.dy.masa.malilib.config.options.ConfigOptionList;
import fi.dy.masa.malilib.hotkeys.KeybindSettings;
import getta.gettaroo.utils.DirectionUtils;
import getta.gettaroo.utils.RowUtils;
import net.minecraft.client.input.Input;

import java.util.List;

public class Hotkeys {

    public static final ConfigHotkey OPEN_CONFIG_GUI = new ConfigHotkey("openConfigGui","G,C", "The key open the in-game config GUI");
    public static final ConfigHotkey STOP_IN_AIR = new ConfigHotkey("stopInAir", "", KeybindSettings.PRESS_ALLOWEXTRA, "Allows you to stay in air");
    public static final ConfigHotkey FAST_DISCONNECT = new ConfigHotkey("fastDisconnect", "", KeybindSettings.PRESS_ALLOWEXTRA, "Instadisconnects you when pressed, BE CAREFUL !");
    public static final ConfigHotkey SWAP_INVENTORY_ROW = new ConfigHotkey("swapInventoryRow", "", KeybindSettings.PRESS_ALLOWEXTRA, "This thing changes your hotbar rows");
    public static final ConfigHotkey SWAP_INVENTORY_ROW_REVERSE = new ConfigHotkey("swapInventoryRowReverse", "", KeybindSettings.PRESS_ALLOWEXTRA, "Does the same than the swapInventoryRow but into the other direction");
    public static final ConfigHotkey SWAP_INVENTORY_CUSTOM_ROW = new ConfigHotkey("swapInventoryCustomRow", "", KeybindSettings.PRESS_ALLOWEXTRA, "Swaps your current row to the selected row at utils");

    public static final List<ConfigHotkey> HOTKEY_LIST = ImmutableList.of(
            OPEN_CONFIG_GUI,
            STOP_IN_AIR,
            FAST_DISCONNECT,
            SWAP_INVENTORY_ROW,
            SWAP_INVENTORY_ROW_REVERSE,
            SWAP_INVENTORY_CUSTOM_ROW
    );

}
