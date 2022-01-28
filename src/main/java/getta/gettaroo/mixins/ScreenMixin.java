package getta.gettaroo.mixins;

import getta.gettaroo.features.ImagePreview;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Screen.class)
public class ScreenMixin {

    @Inject(method = "handleTextClick", at = @At("HEAD"), cancellable = true)
    private void checkForImage(Style style, CallbackInfoReturnable<Boolean> cir) {
        if (style == null) {
            return;
        } else {
            ClickEvent clickEvent = style.getClickEvent();

            if (clickEvent != null) {

                if (clickEvent.getValue().contains("http")) {
                    if(ImagePreview.render(clickEvent.getValue())) {
                        System.out.println("asd");
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }


}
