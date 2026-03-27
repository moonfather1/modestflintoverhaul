package moonfather.modestflintoverhaul.other;

import moonfather.modestflintoverhaul.Constants;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

@EventBusSubscriber
public class EventForRecipes
{
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event)
    {
        RecipeManagerMain.beforeSync(event.getServer());
    }

    //-----------------------------------

    @SubscribeEvent
    public static void onAddReloadListener(AddServerReloadListenersEvent event)
    {
        // i checked, there won't be an existing on in collection.
        event.addListener(id, lissy);
    }

    private static final PreparableReloadListener lissy = new ReloadListener();
    private static final Identifier id = Identifier.fromNamespaceAndPath(Constants.MODID, "recipe_listener");

    private static class ReloadListener implements ResourceManagerReloadListener
    {
        private ReloadListener()
        {
        }

        @Override
        public void onResourceManagerReload(ResourceManager resourceManager)
        {
            if (ServerLifecycleHooks.getCurrentServer() != null)
            {
                RecipeManagerMain.beforeSync(ServerLifecycleHooks.getCurrentServer());
            }
        }
    }
}