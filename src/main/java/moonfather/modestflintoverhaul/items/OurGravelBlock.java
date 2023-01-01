package moonfather.modestflintoverhaul.items;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;


public class OurGravelBlock extends GravelBlock
{
    public OurGravelBlock()
    {
        super(BlockBehaviour.Properties.of(Material.SAND, MaterialColor.STONE).strength(0.6F).sound(SoundType.GRAVEL));
    }



    public static Item.Properties GetItemProperties()
    {
        return new Item.Properties();
    }



    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state)
    {
        return Blocks.GRAVEL.asItem().getDefaultInstance();
    }
}
