package moonfather.modestflintoverhaul.changes;

import com.google.common.collect.ImmutableList;
import moonfather.modestflintoverhaul.Constants;
import moonfather.modestflintoverhaul.items.ItemsAndBlocks;
import moonfather.modestflintoverhaul.mixin.LootPoolAccessor1;
import moonfather.modestflintoverhaul.mixin.LootPoolAccessor2;
import moonfather.modestflintoverhaul.mixin.LootPoolAccessor3;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;

public class PiglinBarterTableModifier
{
    public static void init()
    {
        LootTableEvents.MODIFY.register(PiglinBarterTableModifier::modifyTable);
    }

    private static void modifyTable(ResourceKey<LootTable> lootTableResourceKey, LootTable.Builder builder, LootTableSource lootTableSource, HolderLookup.Provider provider)
    {
        if (lootTableResourceKey.equals(BuiltInLootTables.PIGLIN_BARTERING))
        {
            builder.modifyPools((b) -> PiglinBarterTableModifier.modifyPool(b, provider));
        }
    }

    private static void modifyPool(LootPool.Builder builder, HolderLookup.Provider provider)
    {
        ImmutableList.Builder<LootPoolEntryContainer> entries = ((LootPoolAccessor1) builder).mfo$getEntries();
        Object[] array = ((LootPoolAccessor2) (Object) entries).mfo$getContents();
        for (Object entry : array)
        {
            if (entry instanceof LootItem lootItem)
            {
                if (((LootPoolAccessor3) lootItem).mfo$getItem().is(vanillaGravel))
                {
                    ((LootPoolAccessor3) lootItem).mfo$setItem(provider.getOrThrow(ourKey)  );
                }
            }
        }
    }
    private static final Identifier vanillaGravel = Identifier.withDefaultNamespace("gravel");
    private static final Identifier ourId = Identifier.fromNamespaceAndPath(Constants.MODID, "gravel_unsearched");
    private static final ResourceKey<Item> ourKey = ResourceKey.create(Registries.ITEM, ourId);
}
