package moonfather.modestflintoverhaul;

import moonfather.modestflintoverhaul.items.EventForCreativeInventory;
import moonfather.modestflintoverhaul.other.GravelDispenseBehavior;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Constants.MODID)
public class GravelMod
{
    public GravelMod(IEventBus modBus, ModContainer modContainer)
    {
        RegistryManager.Init(modBus);
        modBus.addListener(this::commonSetup);
        modBus.addListener(EventForCreativeInventory::OnCreativeModeTab);
        modContainer.registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ConfigManager.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        GravelDispenseBehavior.Init();
    }
}
