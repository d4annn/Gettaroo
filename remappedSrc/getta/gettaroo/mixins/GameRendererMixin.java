package getta.gettaroo.mixins;

import getta.gettaroo.config.FeatureToggle;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "bobViewWhenHurt", at = @At("HEAD"), cancellable = true)
    public void removeShake(MatrixStack matrixStack, float f, CallbackInfo ci) {
        if (FeatureToggle.DISABLE_DAMAGE_SHAKE.getBooleanValue()) {
            ci.cancel();
        }
    }
}
