package moonfather.modestflintoverhaul.falling_onto_trapdoors;

import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;

@Mod.EventBusSubscriber
public class EventForFixingFallingBlocks {
    @SubscribeEvent
    public static void OnEntityJoinLevel(EntityJoinLevelEvent event)
    {
        if (event.loadedFromDisk())
        {
            return; // this speeds up checks. could have done without the check.
        }
        if (event.getEntity() instanceof FallingBlockEntity fbe)
        {
            if (fbe.getBlockState().is(Blocks.GRAVEL))
            {
                fbe.blockState = RegistryManager.BlockGravelFleeting.get().defaultBlockState();
            }
            else if (fbe.getBlockState().is(RegistryManager.BlockGravelSearched.get()))
            {
                fbe.blockState = RegistryManager.BlockGravelElusive.get().defaultBlockState();
            }
        }
    }
}
