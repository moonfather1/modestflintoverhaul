package moonfather.modestflintoverhaul.other;

import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
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
                event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
                event.setCanceled(true); // without this we get double use() calls
                if (EventForPlacingGravel.CanPlace(event.getLevel(), destination))       // our thing
                {
                    event.getEntity().level().setBlockAndUpdate(destination, RegistryManager.BlockGravelSearched.get().defaultBlockState());
                    if (!event.getLevel().isClientSide() && !event.getEntity().isCreative())
                    {
                        event.getItemStack().shrink(1);
                    }
                    PlayPlaceSound(event.getEntity().level(), destination, event.getEntity());
                }
            }
            else
            {
                event.setUseItem(Event.Result.DENY);
                BlockState targetState = event.getLevel().getBlockState(event.getPos());
                InteractionResult result = targetState.getBlock().use(targetState, event.getLevel(), event.getPos(), event.getEntity(), event.getHand(), event.getHitVec());
                event.setUseBlock(Event.Result.DENY);
                event.setCancellationResult(InteractionResult.sidedSuccess(event.getLevel().isClientSide()));
                event.setCanceled(true); // without this we get double use() calls
                if (result.equals(InteractionResult.PASS))     // our thing
                {
                    if (EventForPlacingGravel.CanPlace(event.getLevel(), destination))
                    {
                        event.getEntity().level().setBlockAndUpdate(destination, RegistryManager.BlockGravelSearched.get().defaultBlockState());
                        if (!event.getLevel().isClientSide() && !event.getEntity().isCreative())
                        {
                            event.getItemStack().shrink(1);
                        }
                        PlayPlaceSound(event.getEntity().level(), destination, event.getEntity());
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



    private static void PlayPlaceSound(Level level, BlockPos location, Entity entity)
    {
        SoundType soundType = Blocks.GRAVEL.getSoundType(Blocks.GRAVEL.defaultBlockState(), level, location, null);
        level.playSound(entity, location, soundType.getPlaceSound(), SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
    }
}