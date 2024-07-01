package moonfather.modestflintoverhaul;

import net.minecraft.tags.TagKey;
import net.minecraft.util.ColorRGBA;
import net.minecraft.world.item.Item;

public class Constants
{ 
    public static final String MODID = "modestflintoverhaul";
    public static final ColorRGBA GRAVEL_COLOR = new ColorRGBA(-8356741);
    public static class Tags
    {
        public static final TagKey<Item> GravelAny = net.neoforged.neoforge.common.Tags.Items.GRAVELS;
    }
}
