package getta.gettaroo.mixins;

import net.minecraft.client.render.entity.HoglinEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(HoglinEntityRenderer.class)
public interface HoglinEntityRendererInterface {

    @Accessor("TEXTURE")
    public static void setTexture(Identifier identifier) {
    }
}
