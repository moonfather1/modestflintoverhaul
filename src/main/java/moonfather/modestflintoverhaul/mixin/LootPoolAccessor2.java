package moonfather.modestflintoverhaul.mixin;

import com.google.common.collect.ImmutableList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ImmutableList.Builder.class)
public interface LootPoolAccessor2
{
    @Accessor("contents")
    Object[]  mfo$getContents();
}
