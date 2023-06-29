package moonfather.modestflintoverhaul;

import com.mojang.serialization.Codec;
import moonfather.modestflintoverhaul.drops.GravelLootModifier;
import moonfather.modestflintoverhaul.falling_onto_trapdoors.ElusiveGravelBlock;
import moonfather.modestflintoverhaul.falling_onto_trapdoors.FleetingGravelBlock;
import moonfather.modestflintoverhaul.items.OurGravelBlock;
import moonfather.modestflintoverhaul.items.OurGravelItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class RegistryManager
{
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Constants.MODID);
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Constants.MODID);
	private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Constants.MODID);

	public static void Init()
	{
		RegistryManager.ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
		RegistryManager.BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
		RegistryManager.LOOT_MODIFIERS.register(FMLJavaModLoadingContext.get().getModEventBus());
	}

	public static final RegistryObject<Item> ItemGravelUnsearched = ITEMS.register("gravel_unsearched", OurGravelItem::new);

	public static final RegistryObject<Block> BlockGravelSearched = BLOCKS.register("gravel_searched", OurGravelBlock::new);
	//public static final RegistryObject<Item> ItemBlockGravelSearched = ITEMS.register("gravel_searched", () -> BlockItemEx.Create(BlockGravelSearched.get(), GravelBlock.GetItemProperties()).AppendTooltipLine(GravelBlock.TooltipLine1));

	public static final RegistryObject<Block> BlockGravelFleeting = BLOCKS.register("gravel_temporary1", FleetingGravelBlock::new);
	public static final RegistryObject<Block> BlockGravelElusive = BLOCKS.register("gravel_temporary2", ElusiveGravelBlock::new);

	public static final RegistryObject<Codec<? extends IGlobalLootModifier>> StupidGLMSerializer1 = LOOT_MODIFIERS.register("loot_modifier_for_gravel", GravelLootModifier.CODEC);
}
