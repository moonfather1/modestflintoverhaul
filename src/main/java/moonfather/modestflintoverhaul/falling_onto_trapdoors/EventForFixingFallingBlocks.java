package moonfather.modestflintoverhaul.falling_onto_trapdoors;

import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventForFixingFallingBlocks {
    @SubscribeEvent
    public static void OnEntityJoinLevel(EntityJoinWorldEvent event)
    {
        if (event.loadedFromDisk())
        {
            return; // this speeds up checks. could have done without the check.
        }
        if (event.getEntity() instanceof FallingBlockEntity fbe)
        {
            if (fbe.blockState.is(Blocks.GRAVEL))
            {
                fbe.blockState = RegistryManager.BlockGravelFleeting.get().defaultBlockState();
            }
            else if (fbe.blockState.is(RegistryManager.BlockGravelSearched.get()))
            {
                fbe.blockState = RegistryManager.BlockGravelElusive.get().defaultBlockState();
            }
        }
    }
}
