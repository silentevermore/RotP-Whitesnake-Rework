package com.silentevermore.rotp_whitesnake.action;

import com.silentevermore.rotp_whitesnake.init.InitStands;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.ActionTarget.TargetType;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public class WhitesnakeRemovingMemoryDisk extends StandEntityAction {
    public static final StandPose WIND_BLOW=new StandPose("WHITESNAKE_REMOVE_STAND_DISC");

    public WhitesnakeRemovingMemoryDisk(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world,StandEntity standEntity,IStandPower userPower,StandEntityTask task) {
        final ActionTarget target=task.getTarget();
        final LivingEntity victim=StandUtil.getStandUser((LivingEntity) target.getEntity());
        if (!world.isClientSide() && target.getType()==TargetType.ENTITY) {
            if (victim instanceof LivingEntity && victim.isAlive()){
                victim.addEffect(new EffectInstance(ModStatusEffects.STUN.get(), 100, 9, false, false, false));
                victim.addEffect(new EffectInstance(Effects.BLINDNESS, 100, 9, false, false, false));
                victim.addEffect(new EffectInstance(Effects.CONFUSION, 100, 9, false, false, false));
                IStandPower.getStandPowerOptional(victim).ifPresent(power->{
                    if (power.isActive() && power.getStandManifestation() instanceof StandEntity) {
                        ((StandEntity) power.getStandManifestation()).addEffect(new EffectInstance(ModStatusEffects.STUN.get(), 100, 9, false, false, false));
                        ((StandEntity) power.getStandManifestation()).addEffect(new EffectInstance(Effects.BLINDNESS, 100, 9, false, false, false));
                        ((StandEntity) power.getStandManifestation()).addEffect(new EffectInstance(Effects.CONFUSION, 100, 9, false, false, false));
                    }
                });
                final Action<?> RemovingMemoryDisk=InitStands.REMOVING_THE_MEMORY_DISK.get();
                userPower.setCooldownTimer(RemovingMemoryDisk,60);
            }
        }
    }

    @Override
    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task, int ticks){
        if (task.getPhase()==Phase.BUTTON_HOLD && !standEntity.isManuallyControlled()){
            //constants
            final ActionTarget target=task.getTarget();
            final LivingEntity entity=(LivingEntity) target.getEntity();
            final LivingEntity user=standPower.getUser();
            //sanity check
            if (entity instanceof LivingEntity && entity.isAlive() && user instanceof LivingEntity){
                //constants
                final Vector3d dir_difference=entity.position().subtract(user.position());
                final Vector3d normal_dir=dir_difference.normalize();
                //stuff
                standEntity.lookAt(EntityAnchorArgument.Type.EYES,normal_dir);
                standEntity.moveTo(entity.position().subtract(normal_dir));
            }
        }
    }

    @Override
    protected boolean standKeepsTarget(ActionTarget target) {
        return target.getType()==TargetType.ENTITY && target.getEntity() instanceof LivingEntity && !(target.getEntity() instanceof StandEntity);
    }

    @Override
    public TargetRequirement getTargetRequirement() {
        return TargetRequirement.ENTITY;
    }

    @Override
    public boolean cancelHeldOnGettingAttacked(IStandPower power,DamageSource dmgSource,float dmgAmount) {
        return true;
    }

    @Override
    public boolean noAdheringToUserOffset(IStandPower standPower,StandEntity standEntity) {
        return true;
    }
}
