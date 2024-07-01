package moonfather.modestflintoverhaul;

import com.mojang.serialization.MapCodec;
import moonfather.modestflintoverhaul.drops.GravelLootModifier;
import moonfather.modestflintoverhaul.falling_onto_trapdoors.ElusiveGravelBlock;
import moonfather.modestflintoverhaul.falling_onto_trapdoors.FleetingGravelBlock;
import moonfather.modestflintoverhaul.items.OurGravelBlock;
import moonfather.modestflintoverhaul.items.OurGravelItem;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class RegistryManager
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MODID);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Constants.MODID);
	private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MODID);

	public static void Init(IEventBus modEventBus)
	{
		RegistryManager.ITEMS.register(modEventBus);
		RegistryManager.BLOCKS.register(modEventBus);
		RegistryManager.LOOT_MODIFIERS.register(modEventBus);
	}

	public static final DeferredHolder<Item, Item> ItemGravelUnsearched = ITEMS.register("gravel_unsearched", OurGravelItem::new);

	public static final DeferredHolder<Block, Block> BlockGravelSearched = BLOCKS.register("gravel_searched", OurGravelBlock::new);
	//public static final RegistryObject<Item> ItemBlockGravelSearched = ITEMS.register("gravel_searched", () -> BlockItemEx.Create(BlockGravelSearched.get(), GravelBlock.GetItemProperties()).AppendTooltipLine(GravelBlock.TooltipLine1));

	public static final DeferredHolder<Block, Block> BlockGravelFleeting = BLOCKS.register("gravel_temporary1", FleetingGravelBlock::new);
	public static final DeferredHolder<Block, Block> BlockGravelElusive = BLOCKS.register("gravel_temporary2", ElusiveGravelBlock::new);

	public static final Supplier<MapCodec<? extends IGlobalLootModifier>> StupidGLMSerializer1 = LOOT_MODIFIERS.register("loot_modifier_for_gravel", GravelLootModifier.CODEC);
}
