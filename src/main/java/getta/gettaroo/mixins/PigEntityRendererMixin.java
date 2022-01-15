package getta.gettaroo.mixins;

import getta.gettaroo.Constants;
import getta.gettaroo.config.FeatureToggle;
import getta.gettaroo.features.CarpinchoModel;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PigEntityModel;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(PigEntityRenderer.class)
public class PigEntityRendererMixin{

   @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/entity/MobEntityRenderer;<init>(Lnet/minecraft/client/render/entity/EntityRenderDispatcher;Lnet/minecraft/client/render/entity/model/EntityModel;F)V"), index = 1)
    private static EntityModel<?> changeModel(EntityModel<?> entityModel) {

       if(FeatureToggle.PIGS_ARE_FAT_CARPINCHOS.getBooleanValue()) {
           return new CarpinchoModel();
       }
       return new PigEntityModel();
   }
}
