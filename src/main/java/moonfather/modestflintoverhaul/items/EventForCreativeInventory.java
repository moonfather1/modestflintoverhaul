package moonfather.modestflintoverhaul.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

public class EventForCreativeInventory
{
    public static void onCreativeModeTab(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS))
        {
            event.insertAfter(Items.GRAVEL.getDefaultInstance(), ItemsAndBlocks.ItemGravelUnsearched.get().getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}
