package com.silentevermore.rotp_whitesnake.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;

import com.silentevermore.rotp_whitesnake.entity.projectile.DiscProjectile;
import net.minecraft.world.World;

public class WhitesnakeThrowDisc extends StandEntityAction {

    public WhitesnakeThrowDisc(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            DiscProjectile disc = new DiscProjectile(standEntity, world);
            disc.setShootingPosOf(standEntity);
            standEntity.shootProjectile(disc, 0.75F, 0.25F);
        }
    }
}

