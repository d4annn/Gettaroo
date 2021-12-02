package getta.gettaroo.mixins;

import getta.gettaroo.config.Configs;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = PickaxeItem.class, priority = 1100)
public class PickaxeItemMixin extends MiningToolItemMixin{


    @Override
    public void miningSpeedHandler(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
      Material material = state.getMaterial();

      for(String block : Configs.Server.INSTA_MINE_BLOCKS.getStrings()){
          if(state.isOf(Registry.BLOCK.get(new Identifier(block)))){
              material = Material.STONE;
              cir.setReturnValue(38.28572F);
          }
      }
    }


}
