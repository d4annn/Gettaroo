package getta.gettaroo.mixins;

import getta.gettaroo.Constants;
import getta.gettaroo.Gettaroo;
import getta.gettaroo.config.FeatureToggle;
import getta.gettaroo.features.CarpinchoModel;
import getta.gettaroo.utils.CapybaraUtils;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModels;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PigEntityRenderer.class)
public class PigEntityRendererMixin<T extends MobEntity, M extends EntityModel<T>>{

    @ModifyArgs(method = "<init>", at = @At(value = "INVOKE", target = "net/minecraft/client/render/entity/MobEntityRenderer.<init> (Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/EntityModel;F)V"))
    private static void changeModel(Args args, Context context) {

        if(FeatureToggle.PIGS_ARE_FAT_CARPINCHOS.getBooleanValue()) {
            args.set(1, new CarpinchoModel(context.getPart(CapybaraUtils.CARPINCHO_ENTITY)));
        } else {
            args.set(1, new PigEntityModel(context.getPart(EntityModelLayers.PIG)));
        }
   }
}
