package getta.gettaroo.features;

import fi.dy.masa.malilib.config.IConfigBoolean;
import fi.dy.masa.malilib.hotkeys.IKeybind;
import fi.dy.masa.malilib.hotkeys.KeyAction;
import fi.dy.masa.malilib.hotkeys.KeyCallbackToggleBoolean;
import getta.gettaroo.Constants;
import getta.gettaroo.Gettaroo;
import getta.gettaroo.config.FeatureToggle;
import getta.gettaroo.mixins.HoglinEntityRendererInterface;
import getta.gettaroo.mixins.PigEntityRendererInterfaceMixin;
import getta.gettaroo.mixins.PigEntityRendererMixin;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

public class CarpinchoCallbackToggle extends KeyCallbackToggleBoolean {

    public CarpinchoCallbackToggle(IConfigBoolean config) {
        super(config);
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
        super.onKeyAction(action, key);

        Gettaroo.shouldUpdate = true;
        Identifier texture = new Identifier("textures/entity/pig/pig.png");

        if(FeatureToggle.PIGS_ARE_FAT_CARPINCHOS.getBooleanValue() ) {
            texture = new Identifier(Constants.MOD_ID, "textures/entity/carpincho.png");
        }

        PigEntityRendererInterfaceMixin.setTexture(texture);
        texture = new Identifier("textures/entity/hoglin/hoglin.png");

        if(FeatureToggle.HOGLINS_ARE_FAT_CAPINCHOS.getBooleanValue() ) {
            texture = new Identifier(Constants.MOD_ID, "textures/entity/carpincho.png");
        }

        HoglinEntityRendererInterface.setTexture(texture);

        return true;
    }
}
