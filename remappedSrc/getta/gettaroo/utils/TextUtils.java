package getta.gettaroo.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;

public class TextUtils {

    public static final Text HAND_IS_EMPTY = Text.of("§4Your hand is empty");
    public static final Text FILE_NOT_FOUND = Text.of("§4A problem ocurred while creating the file");

    public static void sendMessage(String message){
        MinecraftClient mc = MinecraftClient.getInstance();
        mc.player.sendChatMessage(message);
    }
}
