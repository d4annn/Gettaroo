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

            if(FeatureToggle.NEAR_ENTITIES_RENDER.getBooleanValue()){

//                int type = checkEntitiesToRender(client);
//
//                if(type == 0){
//                    client.getTextureManager().bindTexture(new Identifier("getta.gettaroo:textures/zombieFinal.png"));
//                    Screen.drawTexture(matrices, x + 8, y - 50, 0, 0, 64, 64, 64, 64);
//                }else if(type == 1){
//                    client.getTextureManager().bindTexture(new Identifier("getta.gettaroo:textures/cow.png"));
//                    Screen.drawTexture(matrices, x + 8, y - 50, 0, 0, 64, 64, 64, 64);
//                }else if(type == 2){
//                    client.getTextureManager().bindTexture(new Identifier("getta.gettaroo:textures/stickFinal.png"));
//                    Screen.drawTexture(matrices, x + 8, y - 50, 0, 0, 64, 64, 64, 64);
//                }else if(type == 3){
//                    client.getTextureManager().bindTexture(new Identifier("getta.gettaroo:textures/steveFinal.png"));
//                    Screen.drawTexture(matrices, x + 8, y - 50, 0, 0, 64, 64, 64, 64);
//                }
            }
        }
    }

    @Inject(method = "renderScoreboardSidebar", at = @At("HEAD"), cancellable = true)
    public void hideScoreBoard(MatrixStack matrices, ScoreboardObjective objective, CallbackInfo ci){
        if(FeatureToggle.HIDE_SCOREBOARD.getBooleanValue()){
            ci.cancel();
        }
    }

    private static int k = 0;

    private static int checkEntitiesToRender(MinecraftClient client){
        k++;
        if(k == 150) {
            k = 0;
            if (!isHostileRendered && !isNoHostileRendered && !isItemRendered) {
                for (Entity entity : client.player.clientWorld.getEntities()) {

                    for(HostileEntities entities : HostileEntities.values()){
                        if(entity.getName().getString().equalsIgnoreCase(entities.getName())){
                            return 0;
                        }
                    }for(NeutralEntities entities : NeutralEntities.values()){
                        if(entity.getName().getString().equalsIgnoreCase(entities.getName())){
                            return 1;
                        }
                    }if(entity.getType().equals(EntityType.ITEM)){
                        return 2;
                    }else if(entity.getType().equals(EntityType.PLAYER)){
                        return 3;
                    }
                }
            }
        }
        return 5;
    }
}

