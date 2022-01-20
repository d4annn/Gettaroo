package getta.gettaroo.features;

import com.google.common.collect.ImmutableList;
import getta.gettaroo.Gettaroo;
import getta.gettaroo.config.FeatureToggle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.realms.Request;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

import java.util.Collections;

@Environment(EnvType.CLIENT)
@SuppressWarnings("unchecked")
public class CarpinchoModel extends AnimalModel<Entity> {
    private final ModelPart body;
    private final ModelPart rightBackLeg;
    private final ModelPart leftBackLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart head;
    private final ModelPart earRight;
    private final ModelPart earLeft;
    private final ModelPart hat;
    private final ModelPart hatBrim;
    private boolean onceEaten = false;
    private boolean rick = false;

    public CarpinchoModel(ModelPart root) {
        this.body = root.getChild("body");
        this.head = root.getChild("head");

        this.earRight = this.head.getChild("earRight");
        this.earLeft = this.head.getChild("earLeft");
        this.hat = this.head.getChild("hat");

        this.hatBrim = this.hat.getChild("hatBrim");

        this.rightBackLeg = root.getChild("rightBackLeg");
        this.leftBackLeg = root.getChild("leftBackLeg");
        this.rightFrontLeg = root.getChild("rightFrontLeg");
        this.leftFrontLeg = root.getChild("leftFrontLeg");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("rightBackLeg", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F), ModelTransform.pivot(-3.5F, 13.3F, 10.0F));
        modelPartData.addChild("leftBackLeg", ModelPartBuilder.create().uv(0, 0).cuboid(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F), ModelTransform.pivot(3.5F, 13.3F, 10.0F));
        modelPartData.addChild("rightFrontLeg", ModelPartBuilder.create().uv(50, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F), ModelTransform.pivot(-4.0F, 13.3F, -8.0F));
        modelPartData.addChild("leftFrontLeg", ModelPartBuilder.create().uv(50, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F), ModelTransform.pivot(4.0F, 13.3F, -8.0F));
        ModelPartData modelPartData1 = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -7.0F, -11.0F, 14.0F, 14.0F, 22.0F), ModelTransform.pivot(0.0F, 11.0F, 0.0F));
        ModelPartData modelPartData2 = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 36).cuboid(-4.0F, -5.5F, -11.0F, 8.0F, 10.0F, 14.0F), ModelTransform.pivot(0.0F, 4.5F, -10.0F));
        modelPartData2.addChild("earRight", ModelPartBuilder.create().uv(0, 16).cuboid(-1.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F), ModelTransform.pivot(-3.5F, -5.0F, 1.5F));
        modelPartData2.addChild("earLeft", ModelPartBuilder.create().uv(0, 16).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, true), ModelTransform.pivot(3.5F, -5.0F, 1.5F));
        ModelPartData modelPartData3 = modelPartData2.addChild("hat", ModelPartBuilder.create().uv(30, 36).cuboid(-3.5F, -4.0F, -1.5F, 7.0F, 4.0F, 7.0F), ModelTransform.pivot(0.0F, -5.1F, -1.5F));
        modelPartData3.addChild("hatBrim", ModelPartBuilder.create().uv(45, 17).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 0.0F, 5.0F), ModelTransform.pivot(0.0F, 0.0F, -3.5F));

        return TexturedModelData.of(modelData, 80, 74);
    }


    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(body, leftBackLeg, leftFrontLeg, rightBackLeg, rightFrontLeg, head);
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.earRight.pitch = -0.39269908169872414f;
        this.earRight.yaw = -0.39269908169872414f;
        this.body.pitch = -0.08726646259971647f;
        this.hat.pitch = -0.3127630032889644f;
        this.earLeft.pitch = -0.39269908169872414f;
        this.earLeft.yaw = 0.39269908169872414f;

        float speed = 1.0f;
        float degree = 1.0f;

        this.head.pitch = headPitch * (MathHelper.PI / 180f);
        this.head.yaw = headYaw * (MathHelper.PI / 180f);
        this.body.yaw = 0;
        this.body.roll = MathHelper.cos(limbAngle * speed * 0.4f) * degree * 0.15f * limbDistance;

        if (entity.isTouchingWater()) {

            this.body.yaw = MathHelper.cos(animationProgress * speed * 0.4f) * degree * 0.05f * 1;
            this.body.roll = 0;
            this.leftBackLeg.pitch = MathHelper.cos(1.0f + animationProgress * speed * 0.4f) * degree * 1.2f * 0.2f + 0.45f;
            this.rightBackLeg.pitch = MathHelper.cos(1.0F + animationProgress * speed * 0.4F) * degree * -1.2F * 0.2F + 0.45F;
            this.rightFrontLeg.pitch = MathHelper.cos(1.0F + animationProgress * speed * 0.4F) * degree * 0.8F * 0.2F + 0.45F;
            this.leftFrontLeg.pitch = MathHelper.cos(1.0F + animationProgress * speed * 0.4F) * degree * -0.8F * 0.2F + 0.45F;
            this.head.pitch += MathHelper.cos(animationProgress * speed * 0.4F) * degree * 0.2F * 0.2F - 0.25F;
        } else {

            if (Gettaroo.carpinchosEatsMelon) {

                this.head.pivotY = 6.0F + getNeckAngle(1) * 9.0F;
                this.head.pitch = getNeckAngle(1);
                try {
                    BakedModel bakedModel = MinecraftClient.getInstance().getItemRenderer().getHeldItemModel(Items.MELON_SLICE.getDefaultStack(), MinecraftClient.getInstance().world, (LivingEntity) null, 69420); //haha funny number
                    Gettaroo.matrixStack.push();
                    Gettaroo.matrixStack.translate(0, 1, -1);
                    MinecraftClient.getInstance().getItemRenderer().renderItem(Items.MELON_SLICE.getDefaultStack(), ModelTransformation.Mode.GROUND, false, Gettaroo.matrixStack, Gettaroo.vertexConsumerProvider, 200, OverlayTexture.DEFAULT_UV, bakedModel);
                    Gettaroo.matrixStack.pop();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
                if (Gettaroo.secondaryEatingTimer == 4 && Gettaroo.eatingTimer == 20) {
                    MinecraftClient.getInstance().getSoundManager().play(new PositionedSoundInstance(new Identifier("gettaroo:kele_sound"), SoundCategory.BLOCKS, 1f, 1, false, 0, SoundInstance.AttenuationType.NONE, 0.0D, 0.0D, 0.0D, true));
                }
                if (Gettaroo.eatingTimer >= 40) {
                    Gettaroo.eatingTimer = 0;
                    if (Gettaroo.secondaryEatingTimer >= 4) {
                        Gettaroo.carpinchosEatsMelon = false;
                        Gettaroo.carpinchosAreInFloor = true;
                        Gettaroo.secondaryEatingTimer = 0;
                        Gettaroo.floorTimer = 0;
                    } else {
                        Gettaroo.secondaryEatingTimer++;
                    }
                }
            } else if (Gettaroo.carpinchosAreInFloor) {

                this.body.pivotY = 17.0F;
                this.body.yaw = 0.0F;
                this.rightBackLeg.pivotY = 21.3F;
                this.rightBackLeg.yaw = -0.3490658503988659F;
                this.rightBackLeg.pitch = 1.5708F;
                this.leftBackLeg.pivotY = 21.3F;
                this.leftBackLeg.yaw = 0.3490658503988659F;
                this.leftBackLeg.pitch = 1.5708F;
                this.rightFrontLeg.pivotY = 22.3F;
                this.rightFrontLeg.yaw = 0.3490658503988659F;
                this.rightFrontLeg.pitch = -1.5708F;
                this.leftFrontLeg.pivotY = 22.3F;
                this.leftFrontLeg.yaw = -0.3490658503988659F;
                this.leftFrontLeg.pitch = -1.5708F;
                this.head.pivotY = 10.5F;

                if (Gettaroo.floorTimer >= 600) {
                    Gettaroo.carpinchosAreInFloor = false;
                    Gettaroo.floorTimer = 0;
                    Gettaroo.eatingTimer = 0;
                    rick = false;
                } else if (Gettaroo.floorTimer == 30 && rick == false && FeatureToggle.LOCK_SLOT_ANTI_CHEAT.getBooleanValue()) {
                    rick = true;
                    MinecraftClient.getInstance().getSoundManager().play(new PositionedSoundInstance(new Identifier("gettaroo:ricktroll"), SoundCategory.BLOCKS, 0.3f, 1, false, 0, SoundInstance.AttenuationType.NONE, 0.0D, 0.0D, 0.0D, true));
                    //Easter egg for the ones that reads the code
                }
            } else {

                this.body.pivotY = 11.0F;
                this.body.yaw = 0.0F;
                this.rightBackLeg.pivotY = 13.3F;
                this.rightBackLeg.yaw = 0.0F;
                this.leftBackLeg.pivotY = 13.3F;
                this.leftBackLeg.yaw = 0.0F;
                this.rightFrontLeg.pivotY = 13.3F;
                this.rightFrontLeg.yaw = 0.0F;
                this.leftFrontLeg.pivotY = 13.3F;
                this.leftFrontLeg.yaw = 0.0F;
                this.head.pivotY = 4.5F;

                this.leftBackLeg.pitch = MathHelper.cos(1.0F + limbAngle * speed * 0.4F) * degree * 0.8F * limbDistance;
                this.rightBackLeg.pitch = MathHelper.cos(1.0F + limbAngle * speed * 0.4F) * degree * -0.8F * limbDistance;
                this.rightFrontLeg.pitch = MathHelper.cos(1.0F + limbAngle * speed * 0.4F) * degree * 0.8F * limbDistance;
                this.leftFrontLeg.pitch = MathHelper.cos(1.0F + limbAngle * speed * 0.4F) * degree * -0.8F * limbDistance;
            }
        }
    }

    int intermediate = 0;

    @Environment(EnvType.CLIENT)
    public float getNeckAngle(float delta) {
        if((intermediate++) + Gettaroo.eatingTimer <= 40) {
            intermediate++;
        } else {
            intermediate = 0;
        }
        if (Gettaroo.eatingTimer + intermediate <= 0) {
            return 0.1F;
        } else if (Gettaroo.eatingTimer + intermediate >= 4 && Gettaroo.eatingTimer + intermediate <= 36) {
            return 0.3F;
        } else {
            return Gettaroo.eatingTimer + intermediate < 4 ? ((float)Gettaroo.eatingTimer + intermediate - delta) / 4F : -((float)(Gettaroo.eatingTimer + intermediate / 2 - 40) - delta) / 4F;
        }
    }
}
