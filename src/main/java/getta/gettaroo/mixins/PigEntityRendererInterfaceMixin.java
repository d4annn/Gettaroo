package getta.gettaroo.mixins;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.PigEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PigEntityRenderer.class)
public interface PigEntityRendererInterfaceMixin {

    @Accessor("TEXTURE")
    public static void setTexture(Identifier identifier) {
    }
}
