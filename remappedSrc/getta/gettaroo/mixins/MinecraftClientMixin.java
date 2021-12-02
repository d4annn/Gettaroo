package getta.gettaroo.mixins;

import getta.gettaroo.config.Configs;
import getta.gettaroo.config.FeatureToggle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ClientBuiltinResourcePackProvider;
import net.minecraft.client.util.Window;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export=true)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

    @Shadow @Final private Window window;

    @Shadow public abstract ClientBuiltinResourcePackProvider getResourcePackDownloader();

    @Shadow @Final private static Logger LOGGER;

    @Inject(method = "getWindowTitle", at = @At("HEAD"),cancellable = true)
    public void changeWindowTittle(CallbackInfoReturnable<String> cir){
        if(FeatureToggle.WINDOW_NAME.getBooleanValue()) {
            cir.setReturnValue(Configs.Utils.WINDOW_NAME.getStringValue());
            cir.cancel();
        }
    }
}


