package getta.gettaroo;

import fi.dy.masa.malilib.event.InitializationHandler;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Gettaroo implements ClientModInitializer {

    public static final Logger logger = LogManager.getLogger(Constants.MOD_ID);

    public static ItemRenderer itemRenderer;
    public static ReloadableResourceManager reloadableResourceManager;
    public static boolean shouldUpdate = false;
    public static boolean carpinchosEatsMelon = false;
    public static boolean carpinchosAreInFloor = false;
    public static int eatingTimer = 0;
    public static int floorTimer = 0;
    public static int secondaryEatingTimer = 0;
    public static VertexConsumerProvider vertexConsumerProvider;
    public static MatrixStack matrixStack;

    @Override
    public void onInitializeClient() {
        //Malilib menu
        InitializationHandler.getInstance().registerInitializationHandler(new InitHandler());

        logger.info("onInitializeClient(): Mod initialized");
    }
}
