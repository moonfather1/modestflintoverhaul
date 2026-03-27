package moonfather.modestflintoverhaul.mixin;

import net.minecraft.world.item.crafting.NormalCraftingRecipe;
import net.minecraft.world.item.crafting.PlacementInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;


@Mixin(NormalCraftingRecipe.class)
public interface NormalCraftingRecipeAccessor
{
    @Accessor("placementInfo")
    PlacementInfo mfo$getPlacementInfo();

    @Accessor("placementInfo")
    @Mutable
    void mfo$setPlacementInfo(PlacementInfo newValue);
}


