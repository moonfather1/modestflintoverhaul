package moonfather.modestflintoverhaul.falling_onto_trapdoors;

import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GravelBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class ElusiveGravelBlock extends GravelBlock
{
    public ElusiveGravelBlock()
    {
        super(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL));
    }
    // ignore the name.
    // the thing is, we had two gravel blocks - original and ours. but when original fell onto a torch or a trapdoor, it spawned its item
    //   which wasn't good and i couldn't change it. but when our block fell onto a trapdoor, it spawned nothing because it had no item.
    //   the later i could change using overrides below, but since i need swicharoo for former, i though i add two temporary blocks for clarity.
    // this block falls instead of VANILLA gravel when it starts falling. it should never appear in world as a block or as an item - only as within FallingBlockEntity.



    @Override
    public void onBrokenAfterFall(Level level, BlockPos blockPos, FallingBlockEntity fallingBlockEntity)
    {
        fallingBlockEntity.spawnAtLocation(new ItemStack(Items.GRAVEL));
    }

    @Override
    public void onLand(Level level, BlockPos blockPos, BlockState blockState1, BlockState blockState2, FallingBlockEntity fallingBlockEntity)
    {
        level.setBlockAndUpdate(blockPos, RegistryManager.BlockGravelSearched.get().defaultBlockState());
    }
}
