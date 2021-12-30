package getta.gettaroo;

import fi.dy.masa.malilib.event.InitializationHandler;
import getta.gettaroo.commands.PortalPositionCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Gettaroo implements ClientModInitializer {

    public static final Logger logger = LogManager.getLogger(References.MOD_ID);

    public void registerCommmands(){
        logger.info("registerCommand(): Command registration initialized");
        PortalPositionCommand.register(ClientCommandManager.DISPATCHER);

    }

    @Override
    public void onInitializeClient() {
        //Malilib menu
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());

        //register all commands and keybinds
        registerCommmands();

        logger.info("onInitializeClient(): Mod initialized");
    }
}
