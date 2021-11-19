package gettaroo;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.gui.GuiBase;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import gettaroo.config.Callbacks;
import gettaroo.config.Configs;
import gettaroo.event.InputHandler;
import gettaroo.gui.GuiConfig;
import net.minecraft.client.MinecraftClient;

public class InitHandler implements IInitializationHandler {

    @Override
    public void registerModHandlers() {

        ConfigManager.getInstance().registerConfigHandler(References.MOD_ID, new Configs());

        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerMouseInputHandler(InputHandler.getInstance());


        Callbacks.init(MinecraftClient.getInstance());

    }
}
