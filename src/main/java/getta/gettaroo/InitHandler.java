package getta.gettaroo;

import fi.dy.masa.malilib.config.ConfigManager;
import fi.dy.masa.malilib.event.InputEventHandler;
import fi.dy.masa.malilib.interfaces.IInitializationHandler;
import getta.gettaroo.config.Callbacks;
import getta.gettaroo.config.Configs;
import getta.gettaroo.event.InputHandler;
import net.minecraft.client.MinecraftClient;

public class InitHandler implements IInitializationHandler {

    @Override
    public void registerModHandlers() {

        ConfigManager.getInstance().registerConfigHandler(Constants.MOD_ID, new Configs());

        InputEventHandler.getKeybindManager().registerKeybindProvider(InputHandler.getInstance());
        InputEventHandler.getInputManager().registerMouseInputHandler(InputHandler.getInstance());


        Callbacks.init(MinecraftClient.getInstance());

    }
}
