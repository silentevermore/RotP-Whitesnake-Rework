package com.silentevermore.rotp_whitesnake.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.silentevermore.rotp_whitesnake.entity.projectile.MeltHeartProjectile;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.concurrent.ThreadLocalRandom;

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
            final ThreadLocalRandom rng=ThreadLocalRandom.current();
            final MeltHeartProjectile proj=new MeltHeartProjectile(standEntity, world);
            final Vector3d origin=standEntity.position().add(
                    0,
                    standEntity.getBbHeight()/2f,
                    0
            );
            proj.setPos(origin.x(), origin.y(), origin.z());
            standEntity.shootProjectile(proj, .1f, .5f);
            proj.setDeltaMovement(new Vector3d(
                    rng.nextDouble(-.2,.2),
                            0,
                    rng.nextDouble(-.2,.2)
            ));
        }
    }
}
