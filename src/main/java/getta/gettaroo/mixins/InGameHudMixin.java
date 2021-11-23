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

    @ModifyVariable(method = "renderScoreboardSidebar", at = @At(value = "STORE", ordinal = 0), ordinal = 5)
    private int modifyX1(int x1) {
        if (FeatureToggle.CUSTOM_SCOREBOARD.getBooleanValue()) {
            return Configs.Utils.SCOREBOARD_X_POSITION.getIntegerValue();
        }
        return x1;
    }

    @ModifyVariable(method = "renderScoreboardSidebar", at = @At(value = "STORE", ordinal = 0), ordinal = 11)
    private int modifyX2(int x2) {
        if(FeatureToggle.CUSTOM_SCOREBOARD.getBooleanValue()) {
            if(Configs.Utils.SCOREBOARD_X_POSITION.getIntegerValue() >= 452){
                return x2 - Configs.Utils.SCOREBOARD_X_POSITION.getIntegerValue();
            }else{
                return x2 + Configs.Utils.SCOREBOARD_X_POSITION.getIntegerValue();
            }
        }
        return x2;
    }

    @ModifyVariable(method = "renderScoreboardSidebar", at = @At(value = "STORE", ordinal = 0), ordinal = 7)
    private int modifyBackgroundColor(int color) {
        if (FeatureToggle.CUSTOM_SCOREBOARD.getBooleanValue()) {
            return ColorUtils.fromStringStatic(Configs.Utils.SCOREBOARD_OBJECTIVE_COLOR.getStringValue()).getColor().getRgb();
        }
        return color;
    }

    @ModifyVariable(method = "renderScoreboardSidebar", at = @At(value = "STORE", ordinal = 0), ordinal = 8)
    private int modifyHeadingBackgroundColor(int color) {
        if (FeatureToggle.CUSTOM_SCOREBOARD.getBooleanValue()) {
            return ColorUtils.fromStringStatic(Configs.Utils.SCOREBOARD_OBJECTIVE_COLOR.getStringValue()).getColor().getRgb();
        }
        return color;
    }



    @ModifyVariable(method = "renderScoreboardSidebar", at = @At(value = "STORE", ordinal = 0), ordinal = 3)
    private int modifyY(int y) {
        if (FeatureToggle.CUSTOM_SCOREBOARD.getBooleanValue()) {
            return Configs.Utils.SCOREBOARD_Y_POSITION.getIntegerValue();
        }
        return y;
    }


    @ModifyArg(method = "renderScoreboardSidebar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I", ordinal = 1), index = 4)
    private int modifyHeadingColor(int color) {
        if(FeatureToggle.CUSTOM_SCOREBOARD.getBooleanValue()) {
            return ColorUtils.fromStringStatic(Configs.Utils.SCOREBOARD_OBJECTIVE_COLOR.getStringValue()).getColor().getRgb();
        }
        return color;
    }

    @ModifyArg(method = "renderScoreboardSidebar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/text/Text;FFI)I", ordinal = 0), index = 4)
    private int modifyTextColor(int color) {
        if(FeatureToggle.CUSTOM_SCOREBOARD.getBooleanValue()) {
            return ColorUtils.fromStringStatic(Configs.Utils.SCOREBOARD_SCORE_COLOR.getStringValue()).getColor().getRgb();
        }
        return color;
    }

    @Redirect(method = "renderScoreboardSidebar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/font/TextRenderer;draw(Lnet/minecraft/client/util/math/MatrixStack;Ljava/lang/String;FFI)I", ordinal = 0))
    private int modifyScoreColor(TextRenderer textRenderer, MatrixStack matrices, String text, float x, float y, int color) {
        if(FeatureToggle.CUSTOM_SCOREBOARD.getBooleanValue()) {
            return ColorUtils.fromStringStatic(Configs.Utils.SCOREBOARD_SCORE_COLOR.getStringValue()).getColor().getRgb();
        }
        return color;
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

