package com.silentevermore.rotp_whitesnake.block;

import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import com.silentevermore.rotp_whitesnake.init.InitStands;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.pathfinding.PathType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;

public class MeltHeartBlock extends Block {
    public static final IntegerProperty LAYERS = BlockStateProperties.LAYERS;
    protected static final VoxelShape[] SHAPE_BY_LAYER = new VoxelShape[]{VoxelShapes.empty(), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D), Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D)};
    public static int ticks = 0;

    public MeltHeartBlock(Properties p_i48328_1_) {
        super(p_i48328_1_);
        this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, Integer.valueOf(1)));
    }

    @Override
    public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && !(entity instanceof WhitesnakeEntity) && entity.isAlive()) {
            final LivingEntity livingEntity = (LivingEntity) entity;
            if (!IStandPower.getStandPowerOptional(livingEntity).map(power -> power.getType() == InitStands.WHITESNAKE.getStandType()).orElse(false)) {
                entity.makeStuckInBlock(state, new Vector3d(0.8D, 0.75D, 0.8D));
                livingEntity.addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 100, 2, true, false));
                livingEntity.addEffect(new EffectInstance(Effects.BLINDNESS, 100, 0, true, false));
                livingEntity.addEffect(new EffectInstance(Effects.CONFUSION, 100, 0, true, false));
                //livingEntity.addEffect(new EffectInstance(Effects.WITHER, 100, 0, true, false));
                livingEntity.setSwimming(true);
                if (livingEntity.isAlive()) livingEntity.hurt(DamageSource.WITHER,.5f);
            }
        }
    }

    public boolean isPathfindable(BlockState p_196266_1_, IBlockReader p_196266_2_, BlockPos p_196266_3_, PathType p_196266_4_) {
        switch (p_196266_4_) {
            case LAND:
                return p_196266_1_.getValue(LAYERS) < 5;
            case WATER:
                return false;
            case AIR:
                return false;
            default:
                return false;
        }
    }

    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE_BY_LAYER[p_220053_1_.getValue(LAYERS)];
    }

    public VoxelShape getCollisionShape(BlockState p_220071_1_, IBlockReader p_220071_2_, BlockPos p_220071_3_, ISelectionContext p_220071_4_) {
        return SHAPE_BY_LAYER[p_220071_1_.getValue(LAYERS) - 1];
    }

    public VoxelShape getBlockSupportShape(BlockState p_230335_1_, IBlockReader p_230335_2_, BlockPos p_230335_3_) {
        return SHAPE_BY_LAYER[p_230335_1_.getValue(LAYERS)];
    }

    public VoxelShape getVisualShape(BlockState p_230322_1_, IBlockReader p_230322_2_, BlockPos p_230322_3_, ISelectionContext p_230322_4_) {
        return SHAPE_BY_LAYER[p_230322_1_.getValue(LAYERS)];
    }

    public boolean useShapeForLightOcclusion(BlockState p_220074_1_) {
        return true;
    }

    public boolean canSurvive(BlockState p_196260_1_, IWorldReader p_196260_2_, BlockPos p_196260_3_) {
        return true;
    }

    public BlockState updateShape(BlockState state, Direction dir, BlockState state2, IWorld abstWorld, BlockPos bPos, BlockPos bPos2) {
        return !state.canSurvive(abstWorld, bPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, dir, state2, abstWorld, bPos, bPos2);
    }

    @Override
    public void tick(BlockState state, ServerWorld world, BlockPos pos, Random rng) {
        final int value = state.getValue(LAYERS);
        state.setValue(LAYERS, Math.max(value - 1, 1));
        if (state.getValue(LAYERS) <= 1) {
            world.destroyBlock(pos, false);
        }
        ticks++;
        if (ticks == 5) {
            world.destroyBlock(pos, false);
            ticks = 0;
        }
    }

    public boolean canBeReplaced(BlockState state, BlockItemUseContext ctx){
        return false;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockItemUseContext ctx){
        BlockState blockstate = ctx.getLevel().getBlockState(ctx.getClickedPos());
        if (blockstate.is(this)) {
            int i = blockstate.getValue(LAYERS);
            return blockstate.setValue(LAYERS, Integer.valueOf(Math.min(8, i + 1)));
        } else {
            return super.getStateForPlacement(ctx);
        }
    }

    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> p_206840_1_) {
        p_206840_1_.add(LAYERS);
    }
}


