package getta.gettaroo.mixins;

import getta.gettaroo.config.Configs;
import getta.gettaroo.config.FeatureToggle;

import getta.gettaroo.features.HostileEntities;
import getta.gettaroo.features.NeutralEntities;
import getta.gettaroo.utils.ColorUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    private static boolean isHostileRendered = false;
    private static boolean isNoHostileRendered = false;
    private static boolean isItemRendered = false;

    @Inject(method = "render", at = @At("TAIL"))
    private void renderDurationOverlay(MatrixStack matrices, float tickDelta, CallbackInfo ci){

        int x = 923;
        int y = 9;

        if (FeatureToggle.FIRE_BETTER_RENDER.getBooleanValue()) {
            if (client.player.isOnFire()) {

                    client.getTextureManager().bindTexture(new Identifier("gettaroo:textures/fire1.png"));
                    Screen.drawTexture(matrices, x + 8, y + 14, 0, 0, 32, 32, 32, 32);
            }
        }
    }

    @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    public void hideScoreBoard(MatrixStack matrices, ScoreboardObjective objective, CallbackInfo ci){
        if(FeatureToggle.HIDE_SCOREBOARD.getBooleanValue()){
            ci.cancel();
        }
    }
}

