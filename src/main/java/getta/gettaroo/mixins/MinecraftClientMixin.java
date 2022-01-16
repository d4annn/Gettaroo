package getta.gettaroo.mixins;

import getta.gettaroo.Gettaroo;
import getta.gettaroo.config.Configs;
import getta.gettaroo.config.FeatureToggle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.realms.Request;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.resource.ClientBuiltinResourcePackProvider;
import net.minecraft.client.resource.ResourceReloadLogger;
import net.minecraft.client.util.Window;
import net.minecraft.resource.ReloadableResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Debug(export=true)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin{


    @Shadow @Final private ResourcePackManager resourcePackManager;

    @Shadow @Final private ResourceReloadLogger resourceReloadLogger;

    @Shadow public abstract void setOverlay(@Nullable Overlay overlay);

    @Shadow @Final private ReloadableResourceManager resourceManager;

    @Shadow @Final private static CompletableFuture<Unit> COMPLETED_UNIT_FUTURE;

    @Shadow protected abstract void handleResourceReloadException(Throwable exception);

    @Shadow @Final public WorldRenderer worldRenderer;

    @Shadow public abstract CompletableFuture<Void> reloadResources();

    @Inject(method = "getWindowTitle", at = @At("HEAD"),cancellable = true)
    public void changeWindowTittle(CallbackInfoReturnable<String> cir){
        if(FeatureToggle.WINDOW_NAME.getBooleanValue()) {
            cir.setReturnValue(Configs.Utils.WINDOW_NAME.getStringValue());
        }
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void reloadCarpincho(CallbackInfo ci) {

        if(Gettaroo.shouldUpdate) {
            Gettaroo.shouldUpdate = false;
            List<ResourcePack> list = this.resourcePackManager.createResourcePacks();
            this.resourceManager.reload(Util.getMainWorkerExecutor(), (Executor) this, COMPLETED_UNIT_FUTURE, list);
        }
    }
}



