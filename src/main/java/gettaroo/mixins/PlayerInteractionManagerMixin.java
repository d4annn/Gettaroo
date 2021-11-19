package gettaroo.mixins;

import gettaroo.config.FeatureToggle;
import gettaroo.utils.InventoryUtils;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class PlayerInteractionManagerMixin{

    private static ItemStack currentItem = null;
/*
    @Inject(method = "interactBlock", at = @At("HEAD"))
    public void getBlock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir){
        if(FeatureToggle.RESTOCK_HAND_SHULKER.getBooleanValue()){
            currentItem = player.inventory.getMainHandStack();
        }
    }

    @Inject(method = "interactBlock", at = @At("RETURN"))
    public void restock(ClientPlayerEntity player, ClientWorld world, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir){
        if(FeatureToggle.RESTOCK_HAND_SHULKER.getBooleanValue()){
            InventoryUtils.restockHand(player, hand, currentItem);
        }
            }

 */
}
