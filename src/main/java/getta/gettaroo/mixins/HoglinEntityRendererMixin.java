package getta.gettaroo.mixins;

import getta.gettaroo.config.FeatureToggle;
import getta.gettaroo.features.CarpinchoModel;
import net.minecraft.client.render.entity.HoglinEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.HoglinEntityModel;
import net.minecraft.client.render.entity.model.PigEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HoglinEntityRenderer.class)
public class HoglinEntityRendererMixin {

    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobEntityRenderer;<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Lnet/minecraft/client/render/entity/model/EntityModel;F)V"), index = 1)
    private static EntityModel<?> changeModel(EntityModel<?> entityModel) {

        if(FeatureToggle.HOGLINS_ARE_FAT_CAPINCHOS.getBooleanValue()) {
            return new CarpinchoModel();
        }
        return new HoglinEntityModel();
    }
}
