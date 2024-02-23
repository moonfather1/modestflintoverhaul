package moonfather.modestflintoverhaul;

import moonfather.modestflintoverhaul.items.EventForCreativeInventory;
import moonfather.modestflintoverhaul.other.GravelDispenseBehavior;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(Constants.MODID)
public class GravelMod
{
    public GravelMod(IEventBus modEventBus)
    {
        RegistryManager.Init(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(EventForCreativeInventory::OnCreativeModeTab);
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        ModLoadingContext.get().registerConfig(net.neoforged.fml.config.ModConfig.Type.COMMON, ConfigManager.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        GravelDispenseBehavior.Init();
    }
}
