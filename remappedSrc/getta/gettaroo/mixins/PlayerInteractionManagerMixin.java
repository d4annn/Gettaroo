package getta.gettaroo.mixins;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

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
