package moonfather.modestflintoverhaul.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import moonfather.modestflintoverhaul.ModestFlintOverhaul;
import moonfather.modestflintoverhaul.items.ItemsAndBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FallingBlockEntity.class)
public class FallingBlockEntityMixin
{

    /// this takes care of blocks falling onto trapdoors, etc.

    @WrapOperation(
            method = "tick",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/item/FallingBlockEntity;spawnAtLocation(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;")
    )
    private ItemEntity changeDrops(FallingBlockEntity instance, final ServerLevel level, final ItemLike resource, Operation<ItemEntity> original)
    {
        if (resource.asItem().equals(Items.GRAVEL))
        {
            return original.call(instance, level, ItemsAndBlocks.ItemGravelUnsearched);
        }
        else if (resource.equals(ItemsAndBlocks.BlockGravelSearched))
        {
            return original.call(instance, level, Items.GRAVEL);
        }
        else
        {
            return original.call(instance, level, resource);  // should not happen
        }
    }
}
