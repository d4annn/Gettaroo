package getta.gettaroo.mixins;

import getta.gettaroo.config.Configs;
import getta.gettaroo.config.FeatureToggle;
import getta.gettaroo.config.Hotkeys;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {


@Inject(method = "updateWaterSubmersionState", at = @At("HEAD"), cancellable = true)
public void waterTrolling(CallbackInfoReturnable<Boolean> cir){
    if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
        cir.setReturnValue(false);
    }
}

    @Inject(method = "updateSwimming", at = @At("HEAD"),cancellable = true)
    public void waterTrolling1(CallbackInfo ci){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            ci.cancel();
        }
    }

    @Inject(method = "onSwimmingStart", at = @At("HEAD"),cancellable = true)
    public void waterTrolling12(CallbackInfo ci){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()) {
            ci.cancel();
        }
    }

    @Inject(method = "isSwimming", at = @At("HEAD"), cancellable = true)
    public void isSwimming(CallbackInfoReturnable<Boolean> cir){
        if(FeatureToggle.GRAVITY_ON_WATER.getBooleanValue()){
            cir.setReturnValue(false);
        }
    }

    int b = 0;
    boolean a = false;

    @Inject(method = "travel", at = @At("HEAD"), cancellable = true)
    public void travel(Vec3d movementInput, CallbackInfo ci) {

        if (FeatureToggle.STOP_IN_AIR.getBooleanValue() && Hotkeys.STOP_IN_AIR.getKeybind().isKeybindHeld()) {

            if (b > 60) {
                a = false;
                b = 0;
                return;
            }
            b++;
            a = true;
        } else {
            a = false;
        }
        if(a) {
            ci.cancel();
        }
    }

    @Inject(method = "getBlockBreakingSpeed", at = @At("HEAD"), cancellable = true)
    public void getBlockBreakingSpeed(BlockState block, CallbackInfoReturnable<Float> cir){
        MinecraftClient mc = MinecraftClient.getInstance();
        for(String block1 : Configs.Server.INSTA_MINE_BLOCKS.getStrings()){
            if (block.isOf(Registry.BLOCK.get(new Identifier(block1)))) {

                float f = mc.player.inventory.getBlockBreakingSpeed(block);
                if (f > 1.0F) {
                    int i = EnchantmentHelper.getEfficiency(mc.player);
                    ItemStack itemStack = mc.player.inventory.getMainHandStack();
                    if (i > 0 && !itemStack.isEmpty()) {
                        f += ((float)(i * i + 1)) * 70;
                    }
                }

                if (!mc.player.isOnGround()) {
                    f /= 75.0F;
                }
                cir.setReturnValue(f);
            }
        }
    }


}
