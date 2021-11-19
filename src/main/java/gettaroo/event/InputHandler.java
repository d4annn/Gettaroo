package gettaroo.event;

import com.google.common.collect.ImmutableList;
import fi.dy.masa.malilib.hotkeys.IHotkey;
import fi.dy.masa.malilib.hotkeys.IKeybindManager;
import fi.dy.masa.malilib.hotkeys.IKeybindProvider;
import fi.dy.masa.malilib.hotkeys.IMouseInputHandler;
import fi.dy.masa.malilib.util.GuiUtils;
import gettaroo.References;
import gettaroo.config.Cheats;
import gettaroo.config.Configs;
import gettaroo.config.FeatureToggle;
import gettaroo.config.Hotkeys;
import net.minecraft.client.MinecraftClient;

public class InputHandler implements IKeybindProvider, IMouseInputHandler {

    private static final InputHandler INSTANCE = new InputHandler();

    private InputHandler() {
        super();
    }

    public static InputHandler getInstance() {
        return INSTANCE;
    }

    @Override
    public void addKeysToMap(IKeybindManager manager) {
        for (FeatureToggle toggle : FeatureToggle.values()) {
            manager.addKeybindToMap(toggle.getKeybind());
        }

        for (IHotkey hotkey : Hotkeys.HOTKEY_LIST) {
            manager.addKeybindToMap(hotkey.getKeybind());
        }

        for( Cheats cheat : Cheats.values()){
            manager.addKeybindToMap(cheat.getKeybind());
        }

    }

    @Override
    public void addHotkeys(IKeybindManager manager) {

        manager.addHotkeysForCategory(References.MOD_ID, "gettaroo.hotkeys.category.hotkeys", Hotkeys.HOTKEY_LIST);
        manager.addHotkeysForCategory(References.MOD_ID, "gettaroo.hotkeys.category.getta_toggles_hotkey", ImmutableList.copyOf(FeatureToggle.values()));
        manager.addHotkeysForCategory(References.MOD_ID, "gettaroo.hotkeys.category.cheat_toggles_hotkey", ImmutableList.copyOf(Cheats.values()));
    }
}

