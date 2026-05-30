package moonfather.modestflintoverhaul.changes;

import moonfather.modestflintoverhaul.items.ItemsAndBlocks;
import moonfather.modestflintoverhaul.mixin.ShapedRecipeAccessor;
import moonfather.modestflintoverhaul.mixin.ShapedRecipePatternAccessor;
import moonfather.modestflintoverhaul.mixin.ShapelessRecipeAccessor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;

import java.util.*;

public class RecipeManagerMain
{
    public static void beforeSync(ServerPlayer serverPlayer, boolean joined)
    {
        if (serverPlayer == null || serverPlayer.level() == null || serverPlayer.level().getServer() == null) { return; }
        ItemStack gravelVan = new ItemStack(Items.GRAVEL), gravelOur = new ItemStack(ItemsAndBlocks.ItemGravelUnsearched);
        Collection<RecipeHolder<?>> all = serverPlayer.level().getServer().getRecipeManager().getRecipes();
        for (RecipeHolder<?> recipe : all)
        {
            if (! recipe.value().getType().equals(RecipeType.CRAFTING))
            {
                continue;
            }
            if (recipe.value() instanceof ShapedRecipe shaped)    ///  <<<
            {
                boolean needToAlter = false;
                for (int i = 0; i < shaped.getIngredients().size(); i++)
                {
                    if (shaped.getIngredients().get(i).isPresent() && shaped.getIngredients().get(i).get().test(gravelVan) && ! shaped.getIngredients().get(i).get().test(gravelOur))
                    {
                        needToAlter = true;
                        break;
                    }
                }
                if (needToAlter)
                {
                    List<Optional<Ingredient>> replacement = new ArrayList<>(9);
                    for (int i = 0; i < shaped.getIngredients().size(); i++)
                    {
                        if (shaped.getIngredients().get(i).isEmpty())
                        {
                            replacement.add(Optional.empty());
                        }
                        else if (shaped.getIngredients().get(i).get().test(gravelVan) && ! shaped.getIngredients().get(i).get().test(gravelOur))
                        {
                            Ingredient newIng = makeIngredient(shaped.getIngredients().get(i).get(), gravelOur.getItem());
                            replacement.add(Optional.of(newIng));
                        }
                        else
                        {
                            replacement.add(shaped.getIngredients().get(i));
                        }
                    }
                    ShapedRecipePattern p = ((ShapedRecipeAccessor) shaped).mfo$getPattern();
                    ((ShapedRecipePatternAccessor) (Object) p).mfo$setIngredients(replacement);
                }
            }
            if (recipe.value() instanceof ShapelessRecipe shapeless)      ///  <<<
            {
                List<Ingredient> ingredients = ((ShapelessRecipeAccessor) shapeless).mfo$getIngredients();  // immutable.
                boolean needToAlter = false;
                for (int i = 0; i < ingredients.size(); i++)
                {
                    if (ingredients.get(i).test(gravelVan) && ! ingredients.get(i).test(gravelOur))
                    {
                        needToAlter = true;
                        break;
                    }
                }
                if (needToAlter)
                {
                    List<Ingredient> replacement = new ArrayList<>(9);
                    for (int i = 0; i < ingredients.size(); i++)
                    {
                        if (ingredients.get(i).test(gravelVan) && ! ingredients.get(i).test(gravelOur))
                        {
                            Ingredient newIng = makeIngredient(ingredients.get(i), gravelOur.getItem());
                            replacement.add(newIng);
                        }
                        else
                        {
                            replacement.add(ingredients.get(i));
                        }
                    }
                    ((ShapelessRecipeAccessor) shapeless).mfo$setIngredients(replacement);
                    ((ShapelessRecipeAccessor) shapeless).mfo$setPlacementInfo(null);  // recipe has ingredients. and then in matches check they ignore that and take placementInfo.ingredients
                }
            }
            // i could also do SingleItemRecipe but who cares?
        }
    }

    /////////////////////////////

    public static Ingredient makeIngredient(Ingredient ingredient, Item toAdd)
    {
        int hash;
        if (ingredient.items().count() == 1)
        {
            hash = ingredient.items().findFirst().get().getRegisteredName().hashCode();
        }
        else
        {
            hash = Arrays.hashCode(ingredient.items().toArray()); // probably more expensive than not caching.
        }
        if (map.containsKey(hash))
        {
//            return map.get(hash);
        }
        List<Item> list = new LinkedList<>();
        ingredient.items().forEach(itemHolder -> list.add(itemHolder.value()));
        list.add(toAdd);
        Ingredient result = Ingredient.of(list.stream());
        map.put(hash, result);
        return result;
    }
    private static final Map<Integer, Ingredient> map = new HashMap<>();
}
