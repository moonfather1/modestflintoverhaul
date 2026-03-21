package moonfather.modestflintoverhaul.other;

import moonfather.modestflintoverhaul.Constants;
import moonfather.modestflintoverhaul.RegistryManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.List;
import java.util.ListIterator;

@EventBusSubscriber
public class EventForRecipes
{
    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event)
    {
        replaceGravelInRecipes(event.getServer());
    }

    private static void replaceGravelInRecipes(MinecraftServer server)
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
                    i.set(Ingredient.of(Constants.Tags.GravelAnyForCrafting));
                }
            }
        }
    }

    //-----------------------------------

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event)
    {
        // i checked, there won't be an existing on in collection.
        event.addListener(lissy);
    }

    private static final PreparableReloadListener lissy = new ReloadListener();

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
                replaceGravelInRecipes(ServerLifecycleHooks.getCurrentServer());
            }
        }
    }
}