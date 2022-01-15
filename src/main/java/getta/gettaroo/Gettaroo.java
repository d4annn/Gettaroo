package getta.gettaroo;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.resource.ReloadableResourceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gettaroo implements ClientModInitializer {

    public static final Logger logger = LogManager.getLogger(Constants.MOD_ID);
    public static ItemRenderer itemRenderer;
    public static ReloadableResourceManager reloadableResourceManager;
    public static boolean shouldUpdate = false;

    @Override
    public void onInitializeClient() {
        //Malilib menu
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());

        logger.info("onInitializeClient(): Mod initialized");
    }
}
