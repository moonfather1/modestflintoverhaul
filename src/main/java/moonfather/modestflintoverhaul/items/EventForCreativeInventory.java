package moonfather.modestflintoverhaul.items;

import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class EventForCreativeInventory
{
    public static void OnCreativeModeTab(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS))
        {
            event.accept(RegistryManager.ItemGravelUnsearched.get());
        }
    }
}
