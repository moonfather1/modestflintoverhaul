package moonfather.modestflintoverhaul.mixin;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.ShapelessRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ShapelessRecipe.class)
public interface ShapelessRecipeAccessor
{
    @Accessor("ingredients")
    List<Ingredient> mfo$getIngredients();

    @Accessor("ingredients")
    @Mutable
    void mfo$setIngredients(List<Ingredient> newList);

    @Accessor("placementInfo")
    PlacementInfo mfo$getPlacementInfo();

    @Accessor("placementInfo")
    @Mutable
    void mfo$setPlacementInfo(PlacementInfo newValue);

}


