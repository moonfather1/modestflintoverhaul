package moonfather.modestflintoverhaul.changes;

import moonfather.modestflintoverhaul.ModestFlintOverhaul;
import moonfather.modestflintoverhaul.items.ItemsAndBlocks;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

public class PiglinBarterDropModifier
{
    public static void init()
    {
        LootTableEvents.MODIFY_DROPS.register(PiglinBarterDropModifier::modifyDrops);
    }

    private static void modifyDrops(Holder<LootTable> lootTableHolder, LootContext lootContext, List<ItemStack> list)
    {
        if (lootTableHolder.is(BuiltInLootTables.PIGLIN_BARTERING))
        {
            for (int i = 0; i < list.size(); i++)
            {
                if (list.get(i).is(Items.GRAVEL))
                {
                    try
                    {
                        list.set(i, ItemsAndBlocks.ItemGravelUnsearched.getDefaultInstance().copyWithCount(list.get(i).getCount()));
                        // was ok in testing
                    }
                    catch (Exception ignored)
                    {
                        ModestFlintOverhaul.LOGGER.error("Error in MFO mod: immutable list detected where not expected. Please inform author.");
                    }
                }
            }
        }
    }
}

