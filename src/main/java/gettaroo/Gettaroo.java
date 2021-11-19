package gettaroo;

import fi.dy.masa.malilib.event.InitializationHandler;
import gettaroo.commands.LockSlotCommand;
import gettaroo.commands.PortalPositionCommand;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Gettaroo implements ClientModInitializer {

    public static Identifier ICON = new Identifier(References.MOD_ID, "assets/gettaroo/img.png");
    public static final Logger logger = LogManager.getLogger(References.MOD_ID);

    public void registerCommmands(){
        logger.info("registerCommand(): Command registration initialized");
        PortalPositionCommand.register(ClientCommandManager.DISPATCHER);
//        LockSlotCommand.register(ClientCommandManager.DISPATCHER);
    }

    @Override
    public void onInitializeClient() {
        //Malilib menu
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());

        //register all commands and keybinds
        registerCommmands();

//        LockSlotCommand.updateList();

        logger.info("onInitializeClient(): Mod initialized");
    }
}
