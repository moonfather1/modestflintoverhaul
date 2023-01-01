package moonfather.modestflintoverhaul.middleclick;

import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class EventForMouse
{
    @SubscribeEvent
    public static void OnClickInput(InputEvent.InteractionKeyMappingTriggered event)
    {
        if (event.isPickBlock())
        {
            HitResult target = Minecraft.getInstance().hitResult;
            if (target != null && target.getType() == HitResult.Type.BLOCK)
            {
                BlockPos pos = ((BlockHitResult)target).getBlockPos();
                BlockState state = Minecraft.getInstance().level.getBlockState(pos);
                if (state.is(Blocks.GRAVEL))
                {
                    event.setCanceled(true);
                    /////////
                    LocalPlayer player = Minecraft.getInstance().player;
                    ItemStack result = new ItemStack(RegistryManager.ItemGravelUnsearched.get());
                    if (player.getAbilities().instabuild)
                    {
                        player.getInventory().setPickedItem(result);
                        Minecraft.getInstance().gameMode.handleCreativeModeItemAdd(player.getItemInHand(InteractionHand.MAIN_HAND), 36 + player.getInventory().selected);
                        //return true;
                    }
                    int slot = player.getInventory().findSlotMatchingItem(result);
                    if (slot != -1)
                    {
                        if (Inventory.isHotbarSlot(slot))
                            player.getInventory().selected = slot;
                        else
                            Minecraft.getInstance().gameMode.handlePickItem(slot);
                        //return true;
                    }
                    //return false;
                    /////////
                }
            }
        }
    }
}
