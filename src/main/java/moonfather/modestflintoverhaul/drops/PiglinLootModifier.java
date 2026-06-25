package moonfather.modestflintoverhaul.drops;

import com.google.common.base.Suppliers;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.ListIterator;
import java.util.function.Supplier;

public class PiglinLootModifier extends LootModifier
{
    protected PiglinLootModifier(LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        if (context.getQueriedLootTableId().equals(BuiltInLootTables.PIGLIN_BARTERING.location()))
        {
            int count = 0;
            ListIterator<ItemStack> i = generatedLoot.listIterator();
            while (i.hasNext())
            {
                ItemStack stack = i.next();
                if (stack.is(Items.GRAVEL))
                {
                    count += stack.getCount();;
                    i.remove();
                }
            }
            if (count > 0)
            {
                generatedLoot.add(new ItemStack(RegistryManager.ItemGravelUnsearched.get(), count));
            }
        }
        return generatedLoot;
    }

    /// ///////////////////////////////////////////////

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

    public static final Supplier<MapCodec<PiglinLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
                    .apply(inst, PiglinLootModifier::new)));
}
