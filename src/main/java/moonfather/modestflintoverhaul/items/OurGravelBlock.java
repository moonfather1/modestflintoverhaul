package moonfather.modestflintoverhaul.items;

import moonfather.modestflintoverhaul.Constants;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;


public class OurGravelBlock extends ColoredFallingBlock
{
    public OurGravelBlock()
    {
        super(Constants.GRAVEL_COLOR, BlockBehaviour.Properties.of().strength(0.6F).sound(SoundType.GRAVEL).mapColor(MapColor.STONE).instrument(NoteBlockInstrument.SNARE));
    }



    @Override
    public ItemStack getCloneItemStack(LevelReader world, BlockPos pos, BlockState state)
    {
        return Blocks.GRAVEL.asItem().getDefaultInstance();
    }
}
