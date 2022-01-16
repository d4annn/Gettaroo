package getta.gettaroo.mixins;

import getta.gettaroo.Gettaroo;
import getta.gettaroo.config.FeatureToggle;
import getta.gettaroo.features.CarpinchoModel;
import getta.gettaroo.utils.CapybaraUtils;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.HoglinEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.HoglinEntityModel;
import net.minecraft.client.render.entity.model.PigEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(HoglinEntityRenderer.class)
public class HoglinEntityRendererMixin {

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "net/minecraft/client/render/entity/MobEntityRenderer.<init> (Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/EntityModel;F)V"))
    private static void changeModel(Args args, EntityRendererFactory.Context context) {

        if (FeatureToggle.HOGLINS_ARE_FAT_CAPINCHOS.getBooleanValue()) {
            args.set(1, new CarpinchoModel(context.getPart(CapybaraUtils.CARPINCHO_ENTITY)));
        } else {
            args.set(1, new HoglinEntityModel(context.getPart(EntityModelLayers.HOGLIN)));
        }
    }
}
