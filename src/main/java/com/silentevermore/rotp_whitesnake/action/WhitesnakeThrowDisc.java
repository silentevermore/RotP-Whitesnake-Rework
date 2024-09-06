package com.silentevermore.rotp_whitesnake.action;

import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.item.StandDiscItem;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandInstance;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.projectile.DiscProjectile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class WhitesnakeThrowDisc extends StandEntityAction {
    public static final StandPose THROW_DISC = new StandPose("WHITESNAKE_THROW_DISC");

    public WhitesnakeThrowDisc(StandEntityAction.Builder builder) {
        super(builder);
    }

    public static ItemStack getDisc(LivingEntity entity) {
        ItemStack itemStack;
        itemStack = entity.getMainHandItem();
        if (itemStack == ItemStack.EMPTY) {
            itemStack = entity.getOffhandItem();
        }
        return itemStack;
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity standEntity, IStandPower power, ActionTarget target) {
        if (!(getDisc(power.getUser()).getItem() instanceof StandDiscItem)) {
            return ActionConditionResult.NEGATIVE;
        }
        return ActionConditionResult.POSITIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            LivingEntity entity = userPower.getUser();
            DiscProjectile disc = new DiscProjectile(standEntity, world);
            ItemStack item = getDisc(entity);
            if (StandDiscItem.validStandDisc(item, world.isClientSide())) {
                if (entity instanceof PlayerEntity) {
                    if (!((PlayerEntity) entity).abilities.instabuild) {
                        item.shrink(item.getCount());
                    }
                }
                StandInstance stand_instance = StandDiscItem.getStandFromStack(item);
                RotpWhitesnakeAddon.getLogger().debug("Disc: " + stand_instance.getType().getRegistryName());
                disc.getTags().add(stand_instance.getType().getRegistryName().toString());
//                disc.getTags().add(stand_instance.getType().toString());
            }
            disc.setShootingPosOf(standEntity);
            standEntity.shootProjectile(disc, 2F, 0.25F);
        }
    }
}

