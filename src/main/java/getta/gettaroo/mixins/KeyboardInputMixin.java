package getta.gettaroo.mixins;

import getta.gettaroo.config.Configs;
import getta.gettaroo.config.FeatureToggle;
import getta.gettaroo.utils.DirectionUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.GameOptions;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Environment(EnvType.CLIENT)
@Mixin(KeyboardInput.class)
public class KeyboardInputMixin extends Input{

    @Shadow @Final private GameOptions settings;

    @Redirect(method = "tick", at = @At(
            value = "FIELD",
            target = "net/minecraft/client/input/KeyboardInput.pressingForward:Z",
            opcode = Opcodes.GETFIELD,
            ordinal = 0))
    private boolean onPressingForward(KeyboardInput input) {
        input.pressingForward =(input.pressingForward) || FeatureToggle.AUTO_RUN.getBooleanValue() && Configs.Utils.AUTO_MOVEMENT_DIRECTION.getStringValue().equals(DirectionUtils.FORWARD.getStringValue()) ;
        return input.pressingForward;
    }

    @Redirect(method = "tick", at = @At(
            value = "FIELD",
            target = "net/minecraft/client/input/KeyboardInput.pressingBack:Z",
            opcode = Opcodes.GETFIELD,
            ordinal = 0))
    private boolean onPressingBack(KeyboardInput input) {
        input.pressingBack = (input.pressingBack) || FeatureToggle.AUTO_RUN.getBooleanValue() && Configs.Utils.AUTO_MOVEMENT_DIRECTION.getStringValue().equals(DirectionUtils.BACK.getStringValue());
        return input.pressingBack;
    }

    @Redirect(method = "tick", at = @At(
            value = "FIELD",
            target = "net/minecraft/client/input/KeyboardInput.pressingLeft:Z",
            opcode = Opcodes.GETFIELD,
            ordinal = 0))
    private boolean onPressingLeft(KeyboardInput input) {
        input.pressingLeft = (input.pressingLeft) || FeatureToggle.AUTO_RUN.getBooleanValue() && Configs.Utils.AUTO_MOVEMENT_DIRECTION.getStringValue().equals(DirectionUtils.LEFT.getStringValue());
        return input.pressingLeft;
    }

    @Redirect(method = "tick", at = @At(
            value = "FIELD",
            target = "net/minecraft/client/input/KeyboardInput.pressingRight:Z",
            opcode = Opcodes.GETFIELD,
            ordinal = 0))
    private boolean onPressingRight(KeyboardInput input) {
        input.pressingRight = (input.pressingRight) || FeatureToggle.AUTO_RUN.getBooleanValue() && Configs.Utils.AUTO_MOVEMENT_DIRECTION.getStringValue().equals(DirectionUtils.RIGHT.getStringValue());
        return input.pressingRight;
    }

}
