package gettaroo.mixins;

import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.Color4f;
import gettaroo.commands.PortalPositionCommand;
import gettaroo.config.FeatureToggle;
import net.minecraft.block.StructureBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.*;
import net.minecraft.client.render.block.entity.StructureBlockBlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    public void removeWeatherRender(LightmapTextureManager manager, float f, double d, double e, double g, CallbackInfo ci) {
        if (FeatureToggle.WEATHER_RENDERER.getBooleanValue()) {
            ci.cancel();
        }
    }

    @Inject(method = "render", at = @At("RETURN"))
    public void doBox(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f matrix4f, CallbackInfo ci){
        if(FeatureToggle.PORTAL_OUTSIDE_RENDER.getBooleanValue() && PortalPositionCommand.activated) {
            OutlineVertexConsumerProvider vertexConsumers = MinecraftClient.getInstance().getBufferBuilders().getOutlineVertexConsumers();

            BlockPos pos = PortalPositionCommand.pos1;
            BlockPos pos1 = PortalPositionCommand.pos2;
            Vec3d cam = client.cameraEntity.getPos();

            matrices.push();
            matrices.translate(((double) pos.getX() - cam.getX()) * 2 , ((double) pos.getY() - cam.getY())*2 , ((double) pos.getZ() - cam.getZ()) * 2);
            matrices.translate(0, -1.5, 0); // leve superacion de la altura asignada

            WorldRenderer.drawBox(matrices, vertexConsumers.getBuffer(RenderLayer.LINES), pos.getX(), pos.getY() , pos.getZ(), pos1.getX(), pos1.getY(), pos1.getZ(), 255, 0, 0, 1);
        }
    }
}

