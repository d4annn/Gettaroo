package gettaroo.utils;

import gettaroo.Gettaroo;
import gettaroo.config.FeatureToggle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;


public class InventoryUtils {

    public static boolean isHotbarSlot(int slot){
        if(slot >= 36 && slot <= 44){
            return true;
        }
        return false;
    }
}
