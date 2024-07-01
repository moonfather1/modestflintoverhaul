package moonfather.modestflintoverhaul.other;

import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
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
                event.setUseItem(TriState.FALSE);
                event.setUseBlock(TriState.FALSE);
                event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
                event.setCanceled(true); // without this we get double use() calls
                if (EventForPlacingGravel.CanPlace(event.getLevel(), destination))       // our thing
                {
                    event.getEntity().level().setBlockAndUpdate(destination, RegistryManager.BlockGravelSearched.get().defaultBlockState());
                    if (!event.getEntity().isCreative())
                    {
                        event.getItemStack().shrink(1);
                    }
                }
            }
            else
            {
                event.setUseItem(TriState.FALSE);
                BlockState targetState = event.getLevel().getBlockState(event.getPos());
                InteractionResult result = targetState.getBlock().defaultBlockState().useWithoutItem(event.getLevel(), event.getEntity(), event.getHitVec());
                event.setUseBlock(TriState.FALSE);
                event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
                event.setCanceled(true); // without this we get double use() calls
                if (result.equals(InteractionResult.PASS))     // our thing
                {
                    if (EventForPlacingGravel.CanPlace(event.getLevel(), destination))
                    {
                        event.getEntity().level().setBlockAndUpdate(destination, RegistryManager.BlockGravelSearched.get().defaultBlockState());
                        if (!event.getEntity().isCreative())
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
        return (state.canSurvive(level, pos)) && level.getBlockState(pos).canBeReplaced();
    }
}