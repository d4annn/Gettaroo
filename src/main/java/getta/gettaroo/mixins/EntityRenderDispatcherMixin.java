package getta.gettaroo.mixins;

import getta.gettaroo.Gettaroo;
import getta.gettaroo.config.Configs;
import getta.gettaroo.config.FeatureToggle;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.realms.Request;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;


@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {

    @Shadow @Final private Map<EntityType<?>, EntityRenderer<?>> renderers;

    @Shadow public abstract void reload(ResourceManager manager);

    @Inject(method = "render", at= @At("HEAD"), cancellable = true)
    public void cancelRenderIfSelected(Entity entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci){

        if(FeatureToggle.DISABLE_RENDERING_ENTITIES.getBooleanValue() && Configs.Lists.DISABLED_ENTITIES.getStrings().size() > 0){

            for(String entity1 : Configs.Lists.DISABLED_ENTITIES.getStrings()) {

                if(EntityType.get(new Identifier(entity1).toString()).isPresent()){
                    if(EntityType.get(new Identifier(entity1).toString()).get().equals(entity.getType())){

                        ci.cancel();
                    }
                }
            }
        }
    }
}
