package gettaroo.commands;

import com.mojang.brigadier.CommandDispatcher;
import gettaroo.Gettaroo;
import gettaroo.config.FeatureToggle;
import net.fabricmc.fabric.api.client.command.v1.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v1.FabricClientCommandSource;
import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.server.command.FillCommand;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;

public class PortalPositionCommand {


    public static BlockPos pos1;
    public static BlockPos pos2;
    public static int current;
    public static boolean activated = false;


    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher){
        dispatcher.register(ClientCommandManager.literal("portal")
                .then(ClientCommandManager.literal("select")
                        .executes(context -> select()))
                .then(ClientCommandManager.literal("add")
                        .executes(context -> add()))
                .then(ClientCommandManager.literal("show")
                        .executes(context -> dos())
                .then(ClientCommandManager.literal("remove")
                        .executes(context -> remove()))));
    }

    public static int dos(){
        pos1 = new BlockPos(50, 50, 50);
        pos2   = new BlockPos(54, 55, 51);
        activated = true;
        return 1;
    }

    public static int select(){
        MinecraftClient mc = MinecraftClient.getInstance();
        HitResult hit = mc.crosshairTarget;

        if(current == 1){
            pos1 = new BlockPos(hit.getPos().x, hit.getPos().y, hit.getPos().z);
            changeCurrent();
            return 1;
        }else if(current == 2){
            pos2 = new BlockPos(hit.getPos().x, hit.getPos().y, hit.getPos().z);
            changeCurrent();
            return 1;
        }
        return 0;
    }

    public static int add(){
        if(FeatureToggle.PORTAL_OUTSIDE_RENDER.getBooleanValue()){
            activated = true;
            return 1;
        }
        return 1;
    }

    public static int remove(){
        if(FeatureToggle.PORTAL_OUTSIDE_RENDER.getBooleanValue()){
            activated = false;
            return 1;
        }
        return 1;
    }

    public static void changeCurrent(){
        if(current == 1){
            current = 2;
        }else if(current == 2){
            current = 1;
        }else{
            current = 1;
        }
    }
}
