package moonfather.modestflintoverhaul.drops;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import moonfather.modestflintoverhaul.OptionsHolder;
import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.ListIterator;
import java.util.Random;

public class GravelLootModifier extends LootModifier
{
    public GravelLootModifier(LootItemCondition[] conditionsIn)
    {
        super(conditionsIn);
    }


    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context)
    {
        if (context.getQueriedLootTableId().equals(Blocks.GRAVEL.getLootTable()))
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
            boolean silkTouch = ctxTool != null && EnchantmentHelper.getItemEnchantmentLevel(Enchantments.SILK_TOUCH, ctxTool) > 0;
            if (silkTouch)
            {
                generatedLoot.add(RegistryManager.ItemGravelUnsearched.get().getDefaultInstance());
            }
            else
            {
                generatedLoot.add(new ItemStack(Blocks.GRAVEL));
                int fortune = ctxTool != null ? EnchantmentHelper.getItemEnchantmentLevel(Enchantments.BLOCK_FORTUNE, ctxTool) : 0;
                int howManyWeExpectPer10Gravel = (OptionsHolder.COMMON.BaseDropChance.get() + OptionsHolder.GetFortuneBonus(fortune)) / 10;
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

    private int GetCountToDrop(Random random, int howManyWeExpectPer10Gravel)
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

    public static class Serializer extends GlobalLootModifierSerializer<GravelLootModifier>
    {
        @Override
        public GravelLootModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn)
        {
            return new GravelLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(GravelLootModifier dropsModifier)
        {
            JsonObject result = new JsonObject();
            result.add("conditions", new JsonArray());
            return result;
        }
    }
}
