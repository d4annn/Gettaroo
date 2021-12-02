package getta.gettaroo.mixins;

import getta.gettaroo.config.Cheats;
import getta.gettaroo.config.Configs;
import getta.gettaroo.config.FeatureToggle;
import getta.gettaroo.features.MovementSpeed;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin {

    @Shadow @Final protected MinecraftClient client;


    @Inject(method = "dropSelectedItem", at=@At("HEAD"), cancellable = true)
    public void cancelDrop(boolean dropEntireStack, CallbackInfoReturnable<Boolean> cir){

        for (String item : Configs.Lists.PREVENT_DROPPING_LIST.getStrings()) {

            if (Registry.ITEM.getId(client.player.getInventory().getStack(client.player.getInventory().selectedSlot).getItem()).equals(new Identifier(item))) {

                cir.cancel();
            }
        }
    }

@Shadow private boolean showsDeathScreen;


    @Shadow public Input input;

    @Shadow public abstract boolean isSubmergedInWater();

    @Inject(method = "shouldSlowDown", at = @At("HEAD"), cancellable = true)
    public void shouldSlowDown(CallbackInfoReturnable<Boolean> cir){
        if(FeatureToggle.FAST_SHIFT.getBooleanValue()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }

    @Inject(method = "showsDeathScreen", at = @At("HEAD"),cancellable = true)
    public void showsDeathScreen(CallbackInfoReturnable<Boolean> cir){
        if(FeatureToggle.SHOW_DEATH_SCREEN.getBooleanValue()) {
            cir.setReturnValue(false);
        }
    }
    int i = 0;
    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void spider(CallbackInfo ci){

        if(Cheats.GIGAJUMP.getBooleanValue()) {
            if (client.player.isFallFlying()) {

                Vec3d velocity = client.player.getVelocity();

                client.player.setVelocity(MovementSpeed.checkMovement(client.player).x, MovementSpeed.checkMovement(client.player).y, MovementSpeed.checkMovement(client.player).z );

            }
        }

        if(Cheats.SPIDER.getBooleanValue()) {

            ClientPlayerEntity player = client.player;
            if (!player.horizontalCollision)
                return;

            Vec3d velocity = player.getVelocity();
            if (velocity.y >= 0.2)
                return;

            player.setVelocity(velocity.x, 0.2, velocity.z);
        }

    }

    @Inject(method = "tickMovement", at = @At("HEAD"))
    public void removeFall(CallbackInfo ci){

        if(Cheats.FALL_DAMAGE.getBooleanValue()) {

            ClientPlayerEntity player = client.player;
            if (player.fallDistance <= (player.isFallFlying() ? 1 : 2))
                return;
            if (player.isFallFlying() && player.isSneaking()
                    && !isFallingFastEnoughToCauseDamage(player))
                return;

            player.networkHandler.sendPacket(new PlayerMoveC2SPacket(true));
        }


    }

    private boolean isFallingFastEnoughToCauseDamage(ClientPlayerEntity player)
    {
        return player.getVelocity().y < -0.5;
    }
/*
    @Inject(method = "tick", at = @At("HEAD"))
    public void lockHand(CallbackInfo ci){
        List<LockSlotList.LockSlot> list = LockSlotCommand.readJsonLock().getList();

        ScreenHandler container = client.player.playerScreenHandler;
        for(LockSlotList.LockSlot item : list){

            if(client.player.inventory.getStack(item.getSlot()).getItem() != Registry.ITEM.get(new Identifier(item.getItem()))){

                if(!client.player.inventory.getMainHandStack().equals(Registry.ITEM.get(new Identifier(item.getItem())).getDefaultStack())) {

                    if (client.player.inventory.getSlotWithStack(Registry.ITEM.get(new Identifier(item.getItem())).getDefaultStack()) != -1) {

                        int slotInv = client.player.inventory.getSlotWithStack(Registry.ITEM.get(new Identifier(item.getItem())).getDefaultStack());
                        Slot slot = container.getSlot(slotInv);

                        client.interactionManager.clickSlot(container.syncId, ((SlotMixin) slot).getIndex(), item.getSlot(), SlotActionType.SWAP, client.player);

                    }
                }
            }
        }
    }



 */
}


