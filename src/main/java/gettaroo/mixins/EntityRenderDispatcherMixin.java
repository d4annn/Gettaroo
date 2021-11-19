package gettaroo.mixins;

import gettaroo.config.Configs;
import gettaroo.config.FeatureToggle;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "render", at= @At("HEAD"), cancellable = true)
    public void cancelRenderIfSelected(Entity entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci){

        if(FeatureToggle.DISABLE_RENDERING_ENTITIES.getBooleanValue() && Configs.Utils.DISABLED_ENTITIES.getStrings().size() > 0){

            for(String entity1 : Configs.Utils.DISABLED_ENTITIES.getStrings()) {

                if(EntityType.get(new Identifier(entity1).toString()).isPresent()){
                    if(EntityType.get(new Identifier(entity1).toString()).get().equals(entity.getType())){

                        ci.cancel();
                    }
                }
            }
        }
    }
}
