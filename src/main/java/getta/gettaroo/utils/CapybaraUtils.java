package getta.gettaroo.utils;

import getta.gettaroo.Constants;
import getta.gettaroo.features.CarpinchoModel;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;

public class CapybaraUtils {

    public static final EntityModelLayer CARPINCHO_ENTITY = new EntityModelLayer(new Identifier(Constants.MOD_ID, "textures/entity/carpincho.png"), "carpincho_model");

    public static void registerModels() {
        EntityModelLayerRegistry.registerModelLayer(CARPINCHO_ENTITY, CarpinchoModel::getTexturedModelData);
    }
}
