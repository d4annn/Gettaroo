package gettaroo.mixins;

import gettaroo.config.Configs;
import gettaroo.config.FeatureToggle;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.item.BlockItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerActionResponseS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {

    @Inject(method = "getClientModName", at = @At("HEAD"), cancellable = true)
    private static void getClient(CallbackInfoReturnable<String> cir){
        if(FeatureToggle.CLIENT_NAME.getBooleanValue()) {
            cir.setReturnValue(Configs.Utils.CLIENT_NAME.getStringValue());
            cir.cancel();
        }
    }
}
