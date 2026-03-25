package moonfather.modestflintoverhaul.mixin;

import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShapedRecipePattern;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Optional;

@Mixin(ShapedRecipePattern.class)
public interface ShapedRecipePatternAccessor
{
    @Accessor("ingredients")
    List<Optional<Ingredient>> mfo$getIngredients();

    @Accessor("ingredients")
    @Mutable
    void mfo$setIngredients(List<Optional<Ingredient>> newList);
}


