package moonfather.modestflintoverhaul.other;

import moonfather.modestflintoverhaul.OptionsHolder;
import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class GravelDispenseBehavior extends OptionalDispenseItemBehavior
{
    public static void Init()
    {
        DispenserBlock.registerBehavior(Items.GRAVEL, new GravelDispenseBehavior(RegistryManager.BlockGravelSearched.get()));
        DispenserBlock.registerBehavior(RegistryManager.ItemGravelUnsearched.get(), new GravelDispenseBehavior(Blocks.GRAVEL));
    }

    private final Block blockToPlace;
    public GravelDispenseBehavior(Block blockToPlace)
    {
        this.blockToPlace = blockToPlace;
    }

    @Override
    protected @NotNull ItemStack execute(BlockSource source, ItemStack stack)
    {
        if (! OptionsHolder.ShouldDispenseBlocks())
        {
            return super.execute(source, stack);
        }
        Level level = source.getLevel();
        BlockPos pos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
        BlockState state = level.getBlockState(pos);
        if (state.isAir() || state.getMaterial().isReplaceable())
        {
            this.setSuccess(true);
            level.setBlockAndUpdate(pos, this.blockToPlace.defaultBlockState());
            stack.shrink(1);
            return stack;
        }
        else
        {
            this.setSuccess(false);
            return stack;
        }
    }
}
