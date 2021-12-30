package getta.gettaroo.mixins;

import getta.gettaroo.config.FeatureToggle;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwimNavigation.class)
public class SwimNavigationMixin {

    @Inject(method = "isAtValidPosition", at = @At("HEAD"),cancellable = true)
    public void doSomething(CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(false);
    }

    @Inject(method = "tick", at = @At("HEAD"),cancellable = true)
    public void waterTrolling1(CallbackInfo ci){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            ci.cancel();
        }
    }
}
