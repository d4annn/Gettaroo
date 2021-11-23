package getta.gettaroo.mixins;

import getta.gettaroo.config.Configs;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderPearlItem.class)
public class EnderPearlItemMixin {

//Only for singleplayer/server-side
    @Inject(method = "use", at = @At("TAIL"), cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir){
        if(Configs.Server.ENDERPEARL_NO_COOLDOWN.getBooleanValue()) {
            user.getItemCooldownManager().remove(user.inventory.getMainHandStack().getItem());
        }
    }
}
