package moonfather.modestflintoverhaul.drops;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import moonfather.modestflintoverhaul.ConfigManager;
import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.ListIterator;
import java.util.function.Supplier;

public class GravelLootModifier extends LootModifier
{
    public GravelLootModifier(LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
    }


    @NotNull
    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        if (context.getQueriedLootTableId().equals(Blocks.GRAVEL.getLootTable().location()))
        {
            ListIterator<ItemStack> i = generatedLoot.listIterator();
            while (i.hasNext())
            {
                ItemStack stack = i.next();
                if (stack.is(Items.FLINT))
                {
                    i.remove();
                }
                if (stack.is(Blocks.GRAVEL.asItem()))
                {
                    i.remove();
                }
            }
            ItemStack ctxTool = context.getParamOrNull(LootContextParams.TOOL);
            boolean silkTouch = false;
            if (ctxTool != null)
            {
                Holder<Enchantment> enchantment = context.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
                silkTouch = EnchantmentHelper.getItemEnchantmentLevel(enchantment, ctxTool) > 0;
            }
            if (silkTouch)
            {
                generatedLoot.add(RegistryManager.ItemGravelUnsearched.get().getDefaultInstance());
            }
            else
            {
                generatedLoot.add(new ItemStack(Blocks.GRAVEL));
                Holder<Enchantment> enchantment = context.getLevel().registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
                int fortune = ctxTool != null ? EnchantmentHelper.getItemEnchantmentLevel(enchantment, ctxTool) : 0;
                int howManyWeExpectPer10Gravel = (ConfigManager.BaseDropChance + ConfigManager.GetFortuneBonus(fortune)) / 10;
                int count = GetCountToDrop(context.getRandom(), howManyWeExpectPer10Gravel);
                ////System.out.println("~~~supposed to drop " + howManyWeExpectPer10Gravel + " flint per 10 gravel, dropping " + count + ".");
                if (count > 0)
                {
                    generatedLoot.add(new ItemStack(Items.FLINT, count));
                }
            }
            //System.out.println("~~~~gravel, vanilla");
            return generatedLoot;
        }
        if (context.getQueriedLootTableId().equals(RegistryManager.BlockGravelSearched.get().getLootTable()))
        {
            ListIterator<ItemStack> i = generatedLoot.listIterator();
            while (i.hasNext())
            {
                ItemStack stack = i.next();
                if (stack.is(Items.FLINT))
                {
                    i.remove();
                }
                if (stack.is(Blocks.GRAVEL.asItem()))
                {
                    i.remove();
                }
            }
            generatedLoot.add(new ItemStack(Blocks.GRAVEL));
            //System.out.println("~~~~gravel, ours");
            return generatedLoot;
        }
        return generatedLoot;
    }

    ///////////////////////////////////////////////////////////

    private int GetCountToDrop(RandomSource random, int howManyWeExpectPer10Gravel)
    {
        if (howManyWeExpectPer10Gravel < 10)
        {
            // below 100%, just roll 10-sided for a single piece
            if (random.nextInt(10) < howManyWeExpectPer10Gravel)
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
        else if (howManyWeExpectPer10Gravel < 16)
        {
            // at least 10/10 but less than 16/10
            // we will have 2/10 fixed zero-drops, rest divided between 1 flint and 2 flint.
            int r = random.nextInt(10);
            if (r < 2)
            {
                return 0;
            }
            // 16-total out of 10 should drop 1, total-8 out of 10 should drop 2
            if (r < 2 + (16-howManyWeExpectPer10Gravel))
            {
                return 1;
            }
            else
            {
                return 2;
            }
        }
        else
        {
            // more than 16/10
            // we will have 2/10 fixed zero-drops, for other 8, 2 definite pieces plus roll 10-sided for third.
            // limited to 3 flint.
            float rf = random.nextFloat();
            if (rf < 0.2f)
            {
                return 0;
            }
            howManyWeExpectPer10Gravel = howManyWeExpectPer10Gravel - 16;
            if (rf < 0.2f + (howManyWeExpectPer10Gravel - 16) / 8f)
            {
                return 3;
            }
            else
            {
                return 2;
            }
        }
    }

    ///////////////////////////////////////////////////////////

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }

    public static final Supplier<MapCodec<GravelLootModifier>> CODEC = Suppliers.memoize(() ->
            RecordCodecBuilder.mapCodec(inst -> codecStart(inst)
                    .apply(inst, GravelLootModifier::new)));

}
