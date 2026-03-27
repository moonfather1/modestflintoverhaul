package moonfather.modestflintoverhaul;

import moonfather.modestflintoverhaul.items.EventForCreativeInventory;
import moonfather.modestflintoverhaul.items.ItemsAndBlocks;
import moonfather.modestflintoverhaul.other.GravelDispenseBehavior;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Constants.MODID)
public class ModestFlintOverhaul
{
    public ModestFlintOverhaul(IEventBus modBus, ModContainer modContainer)
    {
        // client: toolt, middle, creative

        ItemsAndBlocks.init(modBus);
        modBus.addListener(this::commonSetup);
        modBus.addListener(EventForCreativeInventory::onCreativeModeTab);
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ConfigManager.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        GravelDispenseBehavior.init();
    }
}
