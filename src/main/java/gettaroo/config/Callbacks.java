package gettaroo.config;

import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.hotkeys.IHotkeyCallback;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.interfaces.IClientTickHandler;
import fi.dy.masa.malilib.interfaces.IValueChangeCallback;
import fi.dy.masa.malilib.util.GuiUtils;
import gettaroo.gui.GuiConfig;
import gettaroo.utils.TextUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.player.PlayerEntity;

public class Callbacks implements IClientTickHandler {

    public static boolean shouldAutoRun = false;

    public static void init(MinecraftClient mc){

        IHotkeyCallback callbackGeneric = new KeyCallbackHotkeys(mc);

        Hotkeys.OPEN_CONFIG_GUI.getKeybind().setCallback(callbackGeneric);
        Hotkeys.FAST_DISCONNECT.getKeybind().setCallback(callbackGeneric);


    }

    @Override
    public void onClientTick(MinecraftClient mc) {
    }

    public static class FeatureCallbackHold implements IValueChangeCallback<IConfigBoolean>
    {
        private final KeyBinding keyBind;

        public FeatureCallbackHold(KeyBinding keyBind)
        {
            this.keyBind = keyBind;
        }

        @Override
        public void onValueChanged(IConfigBoolean config)
        {
            if (config.getBooleanValue())
            {
                KeyBinding.setKeyPressed(InputUtil.fromTranslationKey(this.keyBind.getBoundKeyTranslationKey()), true);
                KeyBinding.onKeyPressed(InputUtil.fromTranslationKey(this.keyBind.getBoundKeyTranslationKey()));
            }
            else
            {
                KeyBinding.setKeyPressed(InputUtil.fromTranslationKey(this.keyBind.getBoundKeyTranslationKey()), false);
            }
        }


    }
    private static class KeyCallbackHotkeys implements IHotkeyCallback{

        private final MinecraftClient mc;

            public KeyCallbackHotkeys(MinecraftClient mc){
                this.mc = mc;
            }

        @Override
        public boolean onKeyAction(KeyAction action, IKeybind key) {

            if (key == Hotkeys.OPEN_CONFIG_GUI.getKeybind()) {
                GuiBase.openGui(new GuiConfig());
                return true;
            }else if(key == Hotkeys.FAST_DISCONNECT.getKeybind()){
                MinecraftClient mc = MinecraftClient.getInstance();
                mc.player.world.disconnect();
            }

            return false;
        }
    }
}

