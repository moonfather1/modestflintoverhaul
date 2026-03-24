package moonfather.modestflintoverhaul.mixin;


import moonfather.modestflintoverhaul.items.ItemsAndBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


//@Mixin(BlockBehaviour.class)
public class BlockMixin
{
	/////////////////////////
	// change of plan - i found a fabric event.  i'll leave this here because NF does not have the event afaik.
	///////////////////////

//
//	/// this handles pick block action  (middle click) for vanilla block. ours is handled in its class.
//
//	@Inject(at = @At("HEAD"), method = "getCloneItemStack(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Z)Lnet/minecraft/world/item/ItemStack;", cancellable = true)
//	private void middleCLickHandling(LevelReader level, BlockPos pos, BlockState state, boolean includeData, CallbackInfoReturnable<ItemStack> info)
//	{
//		if (state.is(Blocks.GRAVEL))
//		{
//			info.setReturnValue(ItemsAndBlocks.ItemGravelUnsearched.getDefaultInstance());
//			info.cancel();
//		}
//	}
}