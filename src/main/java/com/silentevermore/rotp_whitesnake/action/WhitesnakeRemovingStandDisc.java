package com.silentevermore.rotp_whitesnake.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.ActionTarget.TargetType;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.init.ModItems;
import com.github.standobyte.jojo.item.StandDiscItem;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandInstance;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.silentevermore.rotp_whitesnake.init.InitSounds;
import com.silentevermore.rotp_whitesnake.init.InitStands;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Optional;

public class WhitesnakeRemovingStandDisc extends StandEntityAction {
    public WhitesnakeRemovingStandDisc(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()) {
            ActionTarget target = task.getTarget();
            LivingEntity user = userPower.getUser();
            ItemStack held_item = WhitesnakeThrowDisc.getDisc(user);
            LivingEntity victim = (LivingEntity) target.getEntity();
            LivingEntity target_user = StandUtil.getStandUser(victim);
            if (target.getType() == TargetType.ENTITY && target_user instanceof LivingEntity && target_user.isAlive()) {
                IStandPower.getStandPowerOptional(target_user).ifPresent(power -> {
                    if (power.hasPower()) {
                        Optional<StandInstance> previousDiscStand = power.putOutStand();
                        userPower.consumeStamina(300);
                        previousDiscStand.ifPresent(prevStand ->
                                MCUtil.giveItemTo(userPower.getUser(), StandDiscItem.withStand(new ItemStack(ModItems.STAND_DISC.get()), prevStand), false));
                    } else {
                        if (StandDiscItem.validStandDisc(held_item, false)) {
                            StandInstance stand_instance = StandDiscItem.getStandFromStack(held_item);
                            power.giveStandFromInstance(stand_instance, true);
                            power.toggleSummon();
                            held_item.shrink(held_item.getCount());
                        }
                    }
                    standEntity.playSound(InitSounds.WHITESNAKE_REMOVE_DISC.get(), 1, 1);
                });
                userPower.setCooldownTimer(InitStands.WHITESNAKE_REMOVE_STAND_DISC.get(), 20);
            }
        }
    }

    @Override
    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task, int ticks) {
        if (task.getPhase() == Phase.BUTTON_HOLD && !standEntity.isManuallyControlled()) {
            ActionTarget target = task.getTarget();
            LivingEntity entity = (LivingEntity) target.getEntity();
            LivingEntity user = standPower.getUser();
            if (entity instanceof LivingEntity && entity.isAlive() && user instanceof LivingEntity) {
                Vector3d dir_difference = entity.position().subtract(user.position());
                Vector3d normal_dir = dir_difference.normalize();
                standEntity.lookAt(EntityAnchorArgument.Type.EYES, normal_dir);
                standEntity.moveTo(entity.position().subtract(normal_dir));
            }
        }
    }

    @Override
    protected boolean standKeepsTarget(ActionTarget target) {
        return target.getType() == TargetType.ENTITY && target.getEntity() instanceof LivingEntity && !(target.getEntity() instanceof StandEntity);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }

    @Override
    public boolean cancelHeldOnGettingAttacked(IStandPower power, DamageSource dmgSource, float dmgAmount) {
        return true;
    }

    @Override
    public boolean noAdheringToUserOffset(IStandPower standPower, StandEntity standEntity) {
        return true;
    }
}