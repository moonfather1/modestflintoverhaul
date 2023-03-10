package moonfather.modestflintoverhaul.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BlockItemEx extends BlockItem
{
    public static BlockItemEx Create(Block block, Item.Properties properties)
    {
        return new BlockItemEx(block, properties);
    }

    private BlockItemEx(Block block, Item.Properties properties)
    {
        super(block, properties);
    }

    private Component line1 = null, line2 = null;
    public BlockItemEx AppendTooltipLine(Component line)
    {
        if (this.line1 == null)
        {
            this.line1 = line;
        }
        else if (this.line2 == null)
        {
            this.line2 = line;
        }
        return this;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> lines, TooltipFlag flags)
    {
        super.appendHoverText(stack, level, lines, flags);
        if (line1 != null)
        {
            lines.add(line1);
            if (line2 != null)
            {
                lines.add(line2);
            }
        }
    }
}
