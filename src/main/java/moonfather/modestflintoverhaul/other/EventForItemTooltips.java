package moonfather.modestflintoverhaul.other;

import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class EventForItemTooltips
{
    @SubscribeEvent
    public static void OnItemTooltip(ItemTooltipEvent event)
    {
        if (!event.getItemStack().isEmpty() && event.getItemStack().is(Blocks.GRAVEL.asItem()))
        {
            event.getToolTip().add(wontHaveFlint1);
            event.getToolTip().add(wontHaveFlint2);
        }
        if (!event.getItemStack().isEmpty() && event.getItemStack().is(RegistryManager.ItemGravelUnsearched.get()))
        {
            event.getToolTip().add(mayHaveFlint1);
            event.getToolTip().add(mayHaveFlint2);
        }
    }

    private static final Component mayHaveFlint1 = Component.translatable("item.modestflintoverhaul.gravel_unsearched.tooltip1").withStyle(Style.EMPTY.withColor(0x9e7b4d));
    private static final Component mayHaveFlint2 = Component.translatable("item.modestflintoverhaul.gravel_unsearched.tooltip2").withStyle(Style.EMPTY.withColor(0x9e7b4d));
    private static final Component wontHaveFlint1 = Component.translatable("item.modestflintoverhaul.gravel_searched.tooltip1"). withStyle(Style.EMPTY.withColor(0x9e7b4d));
    private static final Component wontHaveFlint2 = Component.translatable("item.modestflintoverhaul.gravel_searched.tooltip2").withStyle(Style.EMPTY.withColor(0x9e7b4d));
}