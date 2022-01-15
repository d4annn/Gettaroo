package getta.gettaroo.features;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.QuadrupedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.PigEntity;
import net.minecraft.util.math.MathHelper;

import java.util.Collections;

@Environment(EnvType.CLIENT)
public class CarpinchoModel extends AnimalModel<Entity> {

    public ModelPart body;
    public ModelPart rightBackLeg;
    public ModelPart leftBackLeg;
    public ModelPart rightFrontLeg;
    public ModelPart leftFrontLeg;
    public ModelPart head;
    public ModelPart chestLeft;
    public ModelPart chestRight;
    public ModelPart earRight;
    public ModelPart earLeft;
    public ModelPart hat;
    public ModelPart hatBrim;

    public CarpinchoModel() {
        this.textureWidth = 80;
        this.textureHeight = 74;
        this.chestRight = new ModelPart(this, 40, 58);
        this.chestRight.setPivot(-7.0F, -3.0F, 4.0F);
        this.chestRight.addCuboid(-2.0F, -4.0F, -4.0F, 2.0F, 6.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.earRight = new ModelPart(this, 0, 16);
        this.earRight.setPivot(-3.5F, -5.0F, 1.5F);
        this.earRight.addCuboid(-1.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightFrontLeg = new ModelPart(this, 50, 0);
        this.rightFrontLeg.setPivot(-4.0F, 13.3F, -8.0F);
        this.rightFrontLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.hatBrim = new ModelPart(this, 45, 17);
        this.hatBrim.setPivot(0.0F, 0.0F, -3.5F);
        this.hatBrim.addCuboid(-4.0F, 0.0F, -2.0F, 8.0F, 0.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body = new ModelPart(this, 0, 0);
        this.body.setPivot(0.0F, 11.0F, 0.0F);
        this.body.addCuboid(-7.0F, -7.0F, -11.0F, 14.0F, 14.0F, 22.0F, 0.0F, 0.0F, 0.0F);
        this.hat = new ModelPart(this, 30, 36);
        this.hat.setPivot(0.0F, -5.1F, -1.5F);
        this.hat.addCuboid(-3.5F, -4.0F, -1.5F, 7.0F, 4.0F, 7.0F, 0.0F, 0.0F, 0.0F);
        this.head = new ModelPart(this, 0, 36);
        this.head.setPivot(0.0F, 4.5F, -10.0F);
        this.head.addCuboid(-4.0F, -5.5F, -11.0F, 8.0F, 10.0F, 14.0F, 0.0F, 0.0F, 0.0F);
        this.chestLeft = new ModelPart(this, 60, 58);
        this.chestLeft.mirror = true;
        this.chestLeft.setPivot(7.0F, -3.0F, 4.0F);
        this.chestLeft.addCuboid(0.0F, -4.0F, -4.0F, 2.0F, 6.0F, 8.0F, 0.0F, 0.0F, 0.0F);
        this.leftBackLeg = new ModelPart(this, 0, 0);
        this.leftBackLeg.setPivot(3.5F, 13.3F, 10.0F);
        this.leftBackLeg.addCuboid(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.leftFrontLeg = new ModelPart(this, 50, 0);
        this.leftFrontLeg.setPivot(4.0F, 13.3F, -8.0F);
        this.leftFrontLeg.addCuboid(-2.0F, 0.0F, -2.0F, 4.0F, 11.0F, 4.0F, 0.0F, 0.0F, 0.0F);
        this.earLeft = new ModelPart(this, 0, 16);
        this.earLeft.mirror = true;
        this.earLeft.setPivot(3.5F, -5.0F, 1.5F);
        this.earLeft.addCuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 3.0F, 0.0F, 0.0F, 0.0F);
        this.rightBackLeg = new ModelPart(this, 0, 0);
        this.rightBackLeg.setPivot(-3.5F, 13.3F, 10.0F);
        this.rightBackLeg.addCuboid(-2.5F, 0.0F, -2.5F, 5.0F, 11.0F, 5.0F, 0.0F, 0.0F, 0.0F);
        this.body.addChild(this.chestRight);
        this.head.addChild(this.earRight);
        this.hat.addChild(this.hatBrim);
        this.head.addChild(this.hat);
        this.body.addChild(this.chestLeft);
        this.head.addChild(this.earLeft);
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

        this.head.pitch =(float)( headPitch * (Math.PI / 180f));
        this.head.yaw = (float)(headYaw * (Math.PI / 180f));
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

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return Collections.emptyList();
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(body, leftBackLeg, leftFrontLeg, rightBackLeg, rightFrontLeg, head);
    }


}
