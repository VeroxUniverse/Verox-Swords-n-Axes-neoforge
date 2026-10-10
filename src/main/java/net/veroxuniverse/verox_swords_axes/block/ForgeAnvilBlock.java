package net.veroxuniverse.verox_swords_axes.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.veroxuniverse.verox_swords_axes.block.entity.ForgeAnvilBlockEntity;
import net.veroxuniverse.verox_swords_axes.registry.ModTags;

import javax.annotation.Nullable;

public class ForgeAnvilBlock extends BaseEntityBlock {

    public static final MapCodec<ForgeAnvilBlock> CODEC = simpleCodec(ForgeAnvilBlock::new);
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape BASE = Block.box(2.0, 0.0, 2.0, 14.0, 4.0, 14.0);
    private static final VoxelShape X_AXIS = Shapes.or(BASE,
            Block.box(3.0, 4.0, 4.0, 13.0, 5.0, 12.0),
            Block.box(4.0, 5.0, 6.0, 12.0, 10.0, 10.0),
            Block.box(0.0, 10.0, 3.0, 16.0, 16.0, 13.0));
    private static final VoxelShape Z_AXIS = Shapes.or(BASE,
            Block.box(4.0, 4.0, 3.0, 12.0, 5.0, 13.0),
            Block.box(6.0, 5.0, 4.0, 10.0, 10.0, 12.0),
            Block.box(3.0, 10.0, 0.0, 13.0, 16.0, 16.0));

    public ForgeAnvilBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getClockWise());
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(FACING).getAxis() == Direction.Axis.X ? X_AXIS : Z_AXIS;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeAnvilBlockEntity(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player,
                                              InteractionHand hand, BlockHitResult hitResult) {
        if (stack.isEmpty()) return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        if (!(level.getBlockEntity(pos) instanceof ForgeAnvilBlockEntity anvil)) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (stack.is(ModTags.Items.SMITHING_HAMMERS)) {
            if (!level.isClientSide) {
                if (player.isShiftKeyDown()) {
                    anvil.removeLastUpgrade(player);
                } else {
                    anvil.strike(player, stack, hand);
                }
            }
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        }

        if (!anvil.canAccept(stack)) {
            return anvil.isEmpty() ? ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION
                    : ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }
        if (!level.isClientSide) {
            anvil.insert(player, stack);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof ForgeAnvilBlockEntity anvil) || anvil.isEmpty()) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide) {
            anvil.takeTop(player);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean movedByPiston) {
        if (!state.is(newState.getBlock()) && level.getBlockEntity(pos) instanceof ForgeAnvilBlockEntity anvil) {
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), anvil.getBase().copy());
            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), anvil.getAddition().copy());
        }
        super.onRemove(state, level, pos, newState, movedByPiston);
    }
}
