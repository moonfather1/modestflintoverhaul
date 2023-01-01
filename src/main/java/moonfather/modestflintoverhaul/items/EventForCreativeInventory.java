package moonfather.modestflintoverhaul.items;

import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.event.CreativeModeTabEvent;

public class EventForCreativeInventory
{
    public static void OnCreativeModeTab(CreativeModeTabEvent.BuildContents event)
    {
        if (event.getTab() == CreativeModeTabs.NATURAL_BLOCKS)
        {
            event.accept(RegistryManager.ItemGravelUnsearched.get());
        }
    }
}
