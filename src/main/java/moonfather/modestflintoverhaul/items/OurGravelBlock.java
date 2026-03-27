package moonfather.modestflintoverhaul.items;

import moonfather.modestflintoverhaul.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;


public class OurGravelBlock extends ColoredFallingBlock
{
    public OurGravelBlock(ResourceKey<Block> id)
    {
        BlockBehaviour.Properties p = BlockBehaviour.Properties.ofFullCopy(Blocks.GRAVEL);
        p.setId(id);
        super(Constants.GRAVEL_COLOR, p);
    }


    @Override
    protected ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData)
    {
        return Items.GRAVEL.getDefaultInstance();
    }

    @Override
    public ItemStack getCloneItemStack(LevelReader level, BlockPos pos, BlockState state, boolean includeData, Player player)
    {
        return Items.GRAVEL.getDefaultInstance();
    }
}
