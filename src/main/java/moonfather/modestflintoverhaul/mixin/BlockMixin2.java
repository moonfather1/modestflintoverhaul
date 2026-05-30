package moonfather.modestflintoverhaul.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import moonfather.modestflintoverhaul.ConfigManager;
import moonfather.modestflintoverhaul.items.ItemsAndBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.ListIterator;

@Mixin(Block.class)
public class BlockMixin2
{

	/// next one takes care of block drops when broken normally (what was GLM on forge).

	@ModifyReturnValue(
			method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;)Ljava/util/List;",
			at = @At("RETURN")
	)
	private static List<ItemStack> checkDrops1(List<ItemStack> original, final BlockState state, final ServerLevel level, final BlockPos pos, @Nullable final BlockEntity blockEntity)
	{
		if (state.is(Blocks.GRAVEL))
		{
			return reviewListVanilla(original, false, 0, level.getRandom());
		}
		if (state.is(ItemsAndBlocks.BlockGravelSearched))
		{
			return reviewListOurs(original);
		}
		return original;
	}

	@ModifyReturnValue(
			method = "getDrops(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/entity/BlockEntity;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
			at = @At("RETURN")
	)
	private static List<ItemStack> checkDrops2(List<ItemStack> original, final BlockState state, final ServerLevel level, final BlockPos pos, @Nullable final BlockEntity blockEntity, @Nullable final Entity breaker, final ItemStack tool)
	{
		boolean silkTouch = false;   int fortune = 0;
        ItemEnchantments en = tool.get(DataComponents.ENCHANTMENTS);
		if (en != null)
		{
			if (silkTouchHolder == null)
			{
				silkTouchHolder = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.SILK_TOUCH);
				fortHolder = level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).getOrThrow(Enchantments.FORTUNE);
			}
			if (en.getLevel(silkTouchHolder) > 0)
			{
				silkTouch = true;
			}
			else
			{
				fortune = en.getLevel(fortHolder);
			}
		}
		if (state.is(Blocks.GRAVEL))
		{
			return reviewListVanilla(original, silkTouch, fortune, level.getRandom());
		}
		if (state.is(ItemsAndBlocks.BlockGravelSearched))
		{
			return reviewListOurs(original);
		}
		return original;
	}
	@Unique
	private static Holder<Enchantment> silkTouchHolder = null;
	@Unique
	private static Holder<Enchantment> fortHolder = null;

	///  this changes placing vanilla gravel to place our block.

	@Inject(at = @At("HEAD"), method = "getStateForPlacement", cancellable = true)
	private void blockPlacement(final BlockPlaceContext context, CallbackInfoReturnable<BlockState> info)
	{
		if (((Block) (Object) this).equals(Blocks.GRAVEL))
		{
			info.setReturnValue(ItemsAndBlocks.BlockGravelSearched.defaultBlockState());
			info.cancel();
		}
	}

	/////////////////////////////////////////////////////////////////

	private static List<ItemStack> reviewListVanilla(List<ItemStack> generatedLoot, boolean isSilkTouch, int fortune, RandomSource random)
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
		if (isSilkTouch)
		{
			generatedLoot.add(ItemsAndBlocks.ItemGravelUnsearched.getDefaultInstance());
		}
		else
		{
			generatedLoot.add(new ItemStack(Blocks.GRAVEL));
			int howManyWeExpectPer10Gravel = (ConfigManager.getBaseDropChance() + ConfigManager.getFortuneBonus(fortune)) / 10;
			int count = getCountToDrop(random, howManyWeExpectPer10Gravel);
			////System.out.println("~~~supposed to drop " + howManyWeExpectPer10Gravel + " flint per 10 gravel, dropping " + count + ".");
			if (count > 0)
			{
				generatedLoot.add(new ItemStack(Items.FLINT, count));
			}
		}
		return generatedLoot;
	}
	private static List<ItemStack> reviewListOurs(List<ItemStack> generatedLoot)
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
		return generatedLoot;
	}

	///////////////////////////////////////////////////////////

	private static int getCountToDrop(RandomSource random, int howManyWeExpectPer10Gravel)
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
}