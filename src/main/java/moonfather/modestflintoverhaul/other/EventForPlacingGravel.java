package moonfather.modestflintoverhaul.other;

import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventForPlacingGravel
{
    @SubscribeEvent
    public static void OnEntityPlace(PlayerInteractEvent.RightClickBlock event)
    {
        if (event.getItemStack().is(Blocks.GRAVEL.asItem()))
        {
            BlockPos destination = event.getPos().relative(event.getFace());
            if (event.getEntity().isCrouching())
            {
                event.setUseItem(Event.Result.DENY);
                event.setUseBlock(Event.Result.DENY);
                event.setCancellationResult(InteractionResult.sidedSuccess(event.getWorld().isClientSide()));
                event.setCanceled(true); // without this we get double use() calls
                if (EventForPlacingGravel.CanPlace(event.getWorld(), destination))       // our thing
                {
                    event.getEntity().getLevel().setBlockAndUpdate(destination, RegistryManager.BlockGravelSearched.get().defaultBlockState());
                    if (!event.getWorld().isClientSide() && !event.getPlayer().isCreative())
                    {
                        event.getItemStack().shrink(1);
                    }
                }
            }
            else
            {
                event.setUseItem(Event.Result.DENY);
                BlockState targetState = event.getWorld().getBlockState(event.getPos());
                InteractionResult result = targetState.getBlock().use(targetState, event.getWorld(), event.getPos(), event.getPlayer(), event.getHand(), event.getHitVec());
                event.setUseBlock(Event.Result.DENY);
                event.setCancellationResult(InteractionResult.sidedSuccess(event.getWorld().isClientSide()));
                event.setCanceled(true); // without this we get double use() calls
                if (result.equals(InteractionResult.PASS))     // our thing
                {
                    if (EventForPlacingGravel.CanPlace(event.getWorld(), destination))
                    {
                        event.getPlayer().level.setBlockAndUpdate(destination, RegistryManager.BlockGravelSearched.get().defaultBlockState());
                        if (!event.getWorld().isClientSide() && ! event.getPlayer().isCreative())
                        {
                            event.getItemStack().shrink(1);
                        }
                        event.setCanceled(true);
                    }
                }
            }
        }
    }



    private static boolean CanPlace(Level level, BlockPos pos)
    {
        BlockState state = Blocks.GRAVEL.defaultBlockState();
        return (state.canSurvive(level, pos)) && level.getBlockState(pos).getMaterial().isReplaceable();
    }
}