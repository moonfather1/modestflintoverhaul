package moonfather.modestflintoverhaul.items;

import moonfather.modestflintoverhaul.Constants;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ItemsAndBlocks
{

    public static final Item ItemGravelUnsearched = makeGravelItem();
    public static final Block BlockGravelSearched = makeGravelBlock();

    ////////////////////////////////////////

    public static void init()
    {
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.NATURAL_BLOCKS).register((itemGroup) -> itemGroup. accept(ItemGravelUnsearched));
    }

    private static Item makeGravelItem()
    {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MODID, "gravel_unsearched"));
        Item result = new OurGravelItem(itemKey);
        Registry.register(BuiltInRegistries.ITEM, itemKey, result);
        return result;
    }

    private static Block makeGravelBlock()
    {
        ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MODID, "gravel_searched"));
        Block result = new OurGravelBlock(blockKey);
        Registry.register(BuiltInRegistries.BLOCK, blockKey, result);
        return result;
    }
}
