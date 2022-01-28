package getta.gettaroo.mixins;

import getta.gettaroo.Gettaroo;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnimalEntity.class)
public class AnimalEntityMixin {

    @Inject(method = "interactMob", at = @At("HEAD"))
    private void carpinchoBreed(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if((Object)this instanceof PigEntity && !Gettaroo.carpinchosAreInFloor) {
            ItemStack itemStack = player.getStackInHand(hand);
            if(itemStack.getItem().equals(Items.MELON_SLICE) && itemStack.getCount() >= 1) {
                Gettaroo.carpinchosEatsMelon = true;
            }
        }
    }
}
