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
import com.silentevermore.rotp_whitesnake.item.MemoryDiscItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class WhitesnakeThrowDisc extends StandEntityAction {
    public static final StandPose THROW_DISC = new StandPose("WHITESNAKE_THROW_DISC");

    public WhitesnakeThrowDisc(StandEntityAction.Builder builder) {
        super(builder);
    }

    public static ItemStack getDisc(LivingEntity entity){
        ItemStack itemStack=entity.getItemInHand(Hand.MAIN_HAND);
        if (itemStack.isEmpty()){
            itemStack=entity.getItemInHand(Hand.OFF_HAND);
        }
        return itemStack;
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity standEntity, IStandPower power, ActionTarget target) {
        return (StandDiscItem.validStandDisc(getDisc(power.getUser()),standEntity.level.isClientSide()) ||
                MemoryDiscItem.validMemoryDisc(getDisc(power.getUser()))) ? ActionConditionResult.POSITIVE : ActionConditionResult.NEGATIVE;
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()){
            final PlayerEntity player=(PlayerEntity) userPower.getUser();
            final DiscProjectile disc=new DiscProjectile(standEntity, world);
            final ItemStack item=getDisc(player);
            if (StandDiscItem.validStandDisc(item, false)){
                if (!player.abilities.instabuild){
                    item.shrink(item.getCount());
                }
                final StandInstance stand_instance=StandDiscItem.getStandFromStack(item);
                disc.getPersistentData().putString("TYPE","stand_disc");
                disc.getPersistentData().put("disc_stand", stand_instance.writeNBT());
            }else if(MemoryDiscItem.validMemoryDisc(item)){
                if (!player.abilities.instabuild){
                    item.shrink(item.getCount());
                }
                disc.getPersistentData().putString("TYPE","memory_disc");
                disc.getPersistentData().put("player_data", item.getOrCreateTagElement("PLAYER_DATA"));
            }
            disc.setShootingPosOf(standEntity);
            standEntity.shootProjectile(disc, 2F, 0.25F);
        }
    }
}

