package getta.gettaroo.mixins;

import getta.gettaroo.config.FeatureToggle;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {


    @Redirect(method = "renderOverlays", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;isOnFire()Z"
    ))
    private static boolean disableFireRender(ClientPlayerEntity clientPlayerEntity){
        if(FeatureToggle.FIRE_BETTER_RENDER.getBooleanValue()) {
            return false;
        }
        return clientPlayerEntity.isOnFire();
    }
}
