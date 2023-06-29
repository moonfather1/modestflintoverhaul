package moonfather.modestflintoverhaul.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;


public class OurGravelBlock extends GravelBlock
{
    public OurGravelBlock()
    {
        super(BlockBehaviour.Properties.of().strength(0.6F).sound(SoundType.GRAVEL).mapColor(MapColor.STONE).instrument(NoteBlockInstrument.SNARE));
    }



    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state)
    {
        return Blocks.GRAVEL.asItem().getDefaultInstance();
    }
}
