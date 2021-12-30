package getta.gettaroo.mixins;

import getta.gettaroo.config.Configs;
import getta.gettaroo.config.FeatureToggle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.SwimAroundGoal;
import net.minecraft.entity.ai.pathing.SwimNavigation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin{

    @Shadow public abstract boolean isInLava();

    @Inject(method = "isInLava", at = @At("HEAD"), cancellable = true)
   public void isInLava(CallbackInfoReturnable<Boolean> cir){
        if(Configs.Server.LAVA_DOESNT_EXIST.getBooleanValue()) {
            cir.setReturnValue(false);
        }
   }

    @Inject(method = "updateSwimming", at = @At("HEAD"),cancellable = true)
    public void waterTrolling13(CallbackInfo ci){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            ci.cancel();
        }
    }

   @Inject(method = "isSubmergedIn", at = @At("HEAD"),cancellable = true)
   public void waterTrolling1(CallbackInfoReturnable<Boolean> cir){
       if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
           cir.setReturnValue(false);
       }
   }
    @Inject(method = "isSubmergedInWater", at = @At("HEAD"),cancellable = true)
    public void waterTrolling12(CallbackInfoReturnable<Boolean> cir){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }
    @Inject(method = "canBeRiddenInWater", at = @At("HEAD"),cancellable = true)
    public void waterTrolling14(CallbackInfoReturnable<Boolean> cir){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }


   @Inject(method = "isTouchingWater", at = @At("HEAD"), cancellable = true)
   public void isTouchingWater(CallbackInfoReturnable<Boolean> cir){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "updateSubmergedInWaterState", at = @At("HEAD"), cancellable = true)
    public void swim(CallbackInfo ci){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            ci.cancel();
        }
    }

    @Inject(method = "onSwimmingStart", at = @At("HEAD"),cancellable = true)
    public void waterTrolling(CallbackInfo ci){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()){
            ci.cancel();
        }
    }


    @Inject(method = "setSwimming", at = @At("HEAD"),cancellable = true)
    public void waterTrolling121(boolean swimming, CallbackInfo ci){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            ci.cancel();
        }
    }
}
