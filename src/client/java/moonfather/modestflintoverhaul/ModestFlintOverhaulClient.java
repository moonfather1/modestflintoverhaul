package moonfather.modestflintoverhaul;

import moonfather.modestflintoverhaul.items.ItemsAndBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.event.player.PlayerPickItemEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class ModestFlintOverhaulClient implements ClientModInitializer
{
	@Override
	public void onInitializeClient()
	{
		ItemTooltipCallback.EVENT.register(ModestFlintOverhaulClient::tooltipHandler);
		PlayerPickItemEvents.BLOCK.register(ModestFlintOverhaulClient::middleClickHandler);
	}



	private static ItemStack middleClickHandler(ServerPlayer player, BlockPos pos, BlockState state, boolean requestIncludeData)
	{
		if (state.is(Blocks.GRAVEL))
		{
			return ItemsAndBlocks.ItemGravelUnsearched.getDefaultInstance();
		}
		return null;
	}



	private static void tooltipHandler(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipFlag tooltipFlag, List<Component> components)
	{
		if (itemStack.is(ItemsAndBlocks.ItemGravelUnsearched))
		{
			components.add(our1);
			components.add(our2);
		}
		if (itemStack.is(Items.GRAVEL))
		{
			components.add(van1);
			components.add(van2);
		}
	}
	private static final Component our1 = Component.translatable("item.modestflintoverhaul.gravel_unsearched.tooltip1").withColor(Constants.GRAVEL_COLOR.rgba());
	private static final Component our2 = Component.translatable("item.modestflintoverhaul.gravel_unsearched.tooltip2").withColor(Constants.GRAVEL_COLOR.rgba());
	private static final Component van1 = Component.translatable("item.modestflintoverhaul.gravel_searched.tooltip1").withColor(Constants.GRAVEL_COLOR.rgba());
	private static final Component van2 = Component.translatable("item.modestflintoverhaul.gravel_searched.tooltip2").withColor(Constants.GRAVEL_COLOR.rgba());
}