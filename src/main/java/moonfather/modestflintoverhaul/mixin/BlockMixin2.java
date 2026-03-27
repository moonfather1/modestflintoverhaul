package moonfather.modestflintoverhaul.mixin;

import moonfather.modestflintoverhaul.ModestFlintOverhaul;
import moonfather.modestflintoverhaul.items.ItemsAndBlocks;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin2
{
	///  this changes placing vanilla gravel to place our block.

	@Inject(at = @At("HEAD"), method = "getStateForPlacement", cancellable = true)
	private void blockPlacement(final BlockPlaceContext context, CallbackInfoReturnable<BlockState> info)
	{
		if (((Block) (Object) this).equals(Blocks.GRAVEL))
		{
			info.setReturnValue(ItemsAndBlocks.BlockGravelSearched.get().defaultBlockState());
			info.cancel();
		}
	}
}