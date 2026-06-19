package moonfather.modestflintoverhaul.items;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jspecify.annotations.Nullable;

public class OurGravelItem extends Item
{
    public OurGravelItem(ResourceKey<Item> id)
    {
        super((new Item.Properties()).setId(id));
    }

    /////////////////////////////////////


    @Override
    public InteractionResult useOn(UseOnContext context)
    {
        BlockPlaceContext blockplacecontext = new BlockPlaceContext(context);
        if (! blockplacecontext.canPlace())
        {
            return InteractionResult.FAIL;
        }
        else
        {
            BlockState blockstate = this.getPlacementState(blockplacecontext);
            if (blockstate == null)
            {
                return InteractionResult.FAIL;
            }
            else if (! this.placeBlock(blockplacecontext, blockstate))
            {
                return InteractionResult.FAIL;
            }
            BlockPos blockpos = blockplacecontext.getClickedPos();
            Level level = blockplacecontext.getLevel();
            Player player = blockplacecontext.getPlayer();
            ItemStack itemstack = blockplacecontext.getItemInHand();
            BlockState blockstate1 = level.getBlockState(blockpos);
            if (blockstate1.is(blockstate.getBlock()))
            {
                blockstate1.getBlock().setPlacedBy(level, blockpos, blockstate1, player, itemstack);
                if (player instanceof ServerPlayer sp)
                {
                    CriteriaTriggers.PLACED_BLOCK.trigger(sp, blockpos, itemstack);
                }
            }
            level.gameEvent(player, GameEvent.BLOCK_PLACE, blockpos);
            //SoundType soundtype = blockstate1.getSoundType(level, blockpos, blockplacecontext.getPlayer());
            //level.playSound(player, blockpos, Blocks.GRAVEL.getSoundType(blockstate1, level, blockpos, null).getPlaceSound(), SoundSource.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
            SoundType soundType = Blocks.GRAVEL.defaultBlockState().getSoundType();
            level.playSound(player, blockpos, soundType.getPlaceSound(), SoundSource.BLOCKS, (soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
            if (player == null || ! player.getAbilities().instabuild)
            {
                itemstack.shrink(1);
            }
            return level.isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER;
        }
    }

    @Nullable
    protected BlockState getPlacementState(BlockPlaceContext context)
    {
        BlockState blockstate = Blocks.GRAVEL.defaultBlockState();  // cant call getStateForPlacement(context) here doe to our mixin
        return blockstate != null && this.canPlace(context, blockstate) ? blockstate : null;
    }

    protected boolean canPlace(BlockPlaceContext p_40611_, BlockState p_40612_)
    {
        Player player = p_40611_.getPlayer();
        CollisionContext collisioncontext = player == null ? CollisionContext.empty() : CollisionContext.of(player);
        return (p_40612_.canSurvive(p_40611_.getLevel(), p_40611_.getClickedPos())) && p_40611_.getLevel().isUnobstructed(p_40612_, p_40611_.getClickedPos(), collisioncontext);
    }

    protected boolean placeBlock(BlockPlaceContext p_40578_, BlockState p_40579_)
    {
        return p_40578_.getLevel().setBlock(p_40578_.getClickedPos(), p_40579_, 11);
    }
}
