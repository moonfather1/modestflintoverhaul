package moonfather.modestflintoverhaul.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LootPool.Builder.class)
public interface LootPoolAccessor1
{
    @Accessor("entries")
    ImmutableList.Builder<LootPoolEntryContainer> mfo$getEntries();
}
