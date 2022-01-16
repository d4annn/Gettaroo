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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.ResourceReloadLogger;
import net.minecraft.util.Identifier;

public class CarpinchoCallbackToggle extends KeyCallbackToggleBoolean {

    public CarpinchoCallbackToggle(IConfigBoolean config) {
        super(config);
    }

    @Override
    public boolean onKeyAction(KeyAction action, IKeybind key) {
        super.onKeyAction(action, key);

        Identifier texture = new Identifier("textures/entity/pig/pig.png");

        if(FeatureToggle.PIGS_ARE_FAT_CARPINCHOS.getBooleanValue() ) {
            texture = new Identifier(Constants.MOD_ID, "textures/entity/carpincho.png");
        }

        PigEntityRendererInterfaceMixin.setTexture(texture);
      //  MinecraftClient.getInstance().worldRenderer.reload();
        MinecraftClient.getInstance().getEntityModelLoader().reload(MinecraftClient.getInstance().getResourceManager());
        texture = new Identifier("textures/entity/hoglin/hoglin.png");

        if(FeatureToggle.HOGLINS_ARE_FAT_CAPINCHOS.getBooleanValue() ) {
            texture = new Identifier(Constants.MOD_ID, "textures/entity/carpincho.png");
        }

        HoglinEntityRendererInterface.setTexture(texture);


        return true;
    }
}
