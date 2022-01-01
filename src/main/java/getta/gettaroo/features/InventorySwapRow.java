package getta.gettaroo.features;

import getta.gettaroo.Constants;
import getta.gettaroo.config.Configs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.screen.slot.SlotActionType;

public class InventorySwapRow {

    public static void swapRouletteCurrent(MinecraftClient client, boolean reverse) {

        for(int i = 0; i < Constants.INVENTORY_ROW_SLOTS; i++) {

            if(!reverse) {

                client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, 36 + i, 27 + i, SlotActionType.SWAP, client.player);
                client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, 27 + i, 18 + i, SlotActionType.SWAP, client.player);
                client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, 18 + i, 9 + i, SlotActionType.SWAP, client.player);
            } else {

                client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, 36 + i, 9 + i, SlotActionType.SWAP, client.player);
                client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, 9 + i, 18 + i, SlotActionType.SWAP, client.player);
                client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, 18 + i, 27 + i, SlotActionType.SWAP, client.player);
            }
        }
    }

    public static void swapCustomRow(MinecraftClient client) {

        for(int i = 0; i < Constants.INVENTORY_ROW_SLOTS; i++) {

            String rowToChange = Configs.Utils.CUSTOM_SWAP_ROW.getStringValue();

            if(rowToChange.equals("2")) {

                client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, 36 + i, 27 + i, SlotActionType.SWAP, client.player);
            } else if(rowToChange.equals("3")) {

                client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, 36 + i, 18 + i, SlotActionType.SWAP, client.player);
            }else if(rowToChange.equals("4")) {

                client.interactionManager.clickSlot(client.player.playerScreenHandler.syncId, 36 + i, 9 + i, SlotActionType.SWAP, client.player);
            }
        }
    }
}
