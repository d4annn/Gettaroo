package gettaroo.mixins;

import gettaroo.config.Configs;
import gettaroo.config.FeatureToggle;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.Tag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin{

    @Shadow public abstract boolean isInLava();

    @Inject(method = "isInLava", at = @At("HEAD"), cancellable = true)
   public void isInLava(CallbackInfoReturnable<Boolean> cir){
        if(Configs.Server.LAVA_DOESNT_EXIST.getBooleanValue()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
   }

   @Inject(method = "isTouchingWater", at = @At("HEAD"), cancellable = true)
   public void isTouchingWater(CallbackInfoReturnable<Boolean> cir){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "updateSubmergedInWaterState", at = @At("HEAD"), cancellable = true)
    public void swim(CallbackInfo ci){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            ci.cancel();
        }
    }

}
