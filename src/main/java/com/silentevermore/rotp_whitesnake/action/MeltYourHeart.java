package com.silentevermore.rotp_whitesnake.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.silentevermore.rotp_whitesnake.block.MeltHeartBlock;
import com.silentevermore.rotp_whitesnake.entity.projectile.DiscProjectile;
import com.silentevermore.rotp_whitesnake.entity.projectile.MeltHeartProjectile;
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
            final ThreadLocalRandom rng=ThreadLocalRandom.current();
            final MeltHeartProjectile proj=new MeltHeartProjectile(standEntity, world);
            final Vector3d origin=standEntity.position().add(
                    rng.nextDouble(-5,5),
                    5,
                    rng.nextDouble(-5,5)
            );
            //stuff
            proj.setPos(origin.x(), origin.y(), origin.z());
            proj.setDeltaMovement(new Vector3d(
                    0,
                    -5,
                    0
            ));
            proj.setSpeedFactor(1f);
            standEntity.shootProjectile(proj, 0f, .5f);
        }
    }
}
