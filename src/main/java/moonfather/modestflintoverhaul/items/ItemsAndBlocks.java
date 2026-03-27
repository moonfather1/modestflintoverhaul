package moonfather.modestflintoverhaul.items;

import com.mojang.serialization.MapCodec;
import moonfather.modestflintoverhaul.Constants;
import moonfather.modestflintoverhaul.drops.GravelLootModifier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ItemsAndBlocks
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BuiltInRegistries.ITEM, Constants.MODID);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, Constants.MODID);
	private static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MODID);

	public static void init(IEventBus modEventBus)
	{
		ItemsAndBlocks.ITEMS.register(modEventBus);
		ItemsAndBlocks.BLOCKS.register(modEventBus);
		ItemsAndBlocks.LOOT_MODIFIERS.register(modEventBus);
	}

	private static final ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Constants.MODID, "gravel_unsearched"));
	public static final DeferredHolder<Item, Item> ItemGravelUnsearched = ITEMS.register("gravel_unsearched", () -> new OurGravelItem(itemKey));

	private static final ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(Constants.MODID, "gravel_searched"));
	public static final DeferredHolder<Block, Block> BlockGravelSearched = BLOCKS.register("gravel_searched", () -> new OurGravelBlock(blockKey));
	//public static final RegistryObject<Item> ItemBlockGravelSearched = ITEMS.register("gravel_searched", () -> BlockItemEx.Create(BlockGravelSearched.get(), GravelBlock.GetItemProperties()).AppendTooltipLine(GravelBlock.TooltipLine1));

	public static final Supplier<MapCodec<? extends IGlobalLootModifier>> StupidGLMSerializer1 = LOOT_MODIFIERS.register("loot_modifier_for_gravel", GravelLootModifier.CODEC);
}
