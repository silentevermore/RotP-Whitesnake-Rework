package com.silentevermore.rotp_whitesnake.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.silentevermore.rotp_whitesnake.block.MeltHeartBlock;
import com.silentevermore.rotp_whitesnake.init.InitBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import java.util.concurrent.ThreadLocalRandom;

import static com.silentevermore.rotp_whitesnake.block.MeltHeartBlock.LAYERS;

public class MeltYourHeart extends StandEntityAction{
    //builder
    public MeltYourHeart(StandEntityAction.Builder builder){
        super(builder);
    }
    //methods
    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target){
        return (power.getStamina()>=100) ? ActionConditionResult.POSITIVE : ActionConditionResult.NEGATIVE;
    }

    @Override
    public void standTickPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task){
        if (!world.isClientSide()){
            //constants
            final ServerWorld serverWorld=(ServerWorld) world;
            final MeltHeartBlock MYH_BLOCK=InitBlocks.MELT_HEART_BLOCK.get();
            final ThreadLocalRandom rng=ThreadLocalRandom.current();
            final Vector3d vec_pos=standEntity.position().add(
                    rng.nextDouble(-5,5),
                    0,
                    rng.nextDouble(-5,5)
            );
            final RayTraceResult ray=JojoModUtil.rayTrace(vec_pos, new Vector3d(0,-1,0), 5f, world, standEntity, null, 0, 16f);
            final Vector3d ray_location=ray.getLocation();
            final BlockPos pos=new BlockPos(ray_location.x(), ray_location.y(), ray_location.z());
            //stuff
            if (world.getBlockState(pos).getBlock()!=Blocks.AIR
                    && world.getBlockState(pos.above()).isFaceSturdy(world, pos.above(), Direction.UP))
            {
                if (world.getBlockState(pos.above()).getBlock()!=MYH_BLOCK){
                    world.setBlockAndUpdate(pos, MYH_BLOCK.defaultBlockState().setValue(LAYERS, 1));
                }
                world.getBlockStates(new AxisAlignedBB(standEntity.position(), standEntity.position()).inflate(5)).forEach(state -> {
                    if (state.getBlock()==MYH_BLOCK && state.getValue(LAYERS)<8 && rng.nextFloat()<.1){
                        world.setBlockAndUpdate(pos, state.setValue(LAYERS,state.getValue(LAYERS)+1));
                    }
                });
            }
        }
    }
}
