package getta.gettaroo.mixins;

import getta.gettaroo.config.Configs;
import getta.gettaroo.config.FeatureToggle;
import net.minecraft.client.ClientBrandRetriever;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientBrandRetriever.class)
public class ClientBrandRetrieverMixin {

//    @Redirect(method = "getClientModName", at = @At(target = "Lnet/minecraft/client/ClientBrandRetriever;getClientModName()Ljava/lang/String;", value = "FIELD"))
//    private static String getClient(CallbackInfoReturnable<String> cir){
//        return Configs.Utils.CLIENT_NAME.getStringValue();
//    }
}
