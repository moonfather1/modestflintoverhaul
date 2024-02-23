package moonfather.modestflintoverhaul.other;

import moonfather.modestflintoverhaul.Constants;
import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Mod.EventBusSubscriber
public class EventForRecipes
{
    @SubscribeEvent
    public static void OnServerStarting(ServerStartingEvent event)
    {
        ReplaceGravelInRecipes(event.getServer());
    }

    private static void ReplaceGravelInRecipes(MinecraftServer server)
    {
        ItemStack vanillaGravel = new ItemStack(Blocks.GRAVEL);
        ItemStack ourGravel = new ItemStack(RegistryManager.ItemGravelUnsearched.get());
        List<RecipeHolder<CraftingRecipe>> list = server.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING);
        for (RecipeHolder<CraftingRecipe> recipe : list)
        {
            ListIterator<Ingredient> i = recipe.value().getIngredients().listIterator();
            while (i.hasNext())
            {
                Ingredient ingredient = i.next();
                if (ingredient.test(vanillaGravel) && ! ingredient.test(ourGravel))
                {
                    i.set(Ingredient.of(Constants.Tags.GravelAny));
                }
            }
        }
    }

    //-----------------------------------

    @SubscribeEvent
    public static void OnAddReloadListener(AddReloadListenerEvent event)
    {
        for (PreparableReloadListener listener: event.getListeners())
        {
            if (listener instanceof ReloadListener)
            {
                return;
            }
        }
        event.addListener(lissy);
    }

    private static PreparableReloadListener lissy = new ReloadListener();

    private static class ReloadListener implements PreparableReloadListener
    {
        @Override
        public CompletableFuture<Void> reload(PreparationBarrier p_10638_, ResourceManager p_10639_, ProfilerFiller p_10640_, ProfilerFiller p_10641_, Executor p_10642_, Executor p_10643_)
        {
            if (ServerLifecycleHooks.getCurrentServer() != null) // will be null one during load
            {
                ReplaceGravelInRecipes(ServerLifecycleHooks.getCurrentServer());
            }
            return p_10638_.wait(null);
        }
    }
}