package com.silentevermore.rotp_whitesnake.action;

import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.ActionConditionResult;
import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.ActionTarget.TargetType;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandPose;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.stand.IStandManifestation;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.init.InitItems;
import com.silentevermore.rotp_whitesnake.init.InitStands;
import com.silentevermore.rotp_whitesnake.item.MemoryDiscItem;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import oshi.hardware.Memory;

public class WhitesnakeRemovingMemoryDisk extends StandEntityAction {
    public static final StandPose WIND_BLOW = new StandPose("WHITESNAKE_REMOVE_STAND_DISC");

    public WhitesnakeRemovingMemoryDisk(StandEntityAction.Builder builder) {
        super(builder);
    }

    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        final ActionTarget target=task.getTarget();
        final PlayerEntity player=(PlayerEntity) StandUtil.getStandUser(standEntity);
        final LivingEntity victim=StandUtil.getStandUser((LivingEntity) target.getEntity());
        final ItemStack itemStack=MemoryDiscItem.getDiscInHand(player);
        if (!world.isClientSide() && target.getType()==TargetType.ENTITY) {
            if (victim!=null && victim.isAlive()){
                if (!MemoryDiscItem.discBelongsTo(itemStack,victim)){
                    if (victim instanceof PlayerEntity){
                        final PlayerEntity victim_player=(PlayerEntity) victim;
                        IStandPower.getStandPowerOptional(victim_player).ifPresent(stand_power->{
                            if (stand_power.getStandManifestation() instanceof StandEntity){
                                final StandEntity stand_manifest=(StandEntity) stand_power.getStandManifestation();
                                stand_manifest.getPersistentData().putBoolean("MEMORY_DISK_AFFECTED",true);
                            }
                        });
                    }
                    victim.getPersistentData().putBoolean("MEMORY_DISK_AFFECTED",true);
                    final ItemStack disc=MemoryDiscItem.withTargetNbt(new ItemStack(InitItems.MEMORY_DISC_ITEM.get()), victim);
                    MCUtil.giveItemTo(player, disc, true);
                }else{
                    if (victim instanceof PlayerEntity){
                        final PlayerEntity victim_player=(PlayerEntity) victim;
                        IStandPower.getStandPowerOptional(victim_player).ifPresent(stand_power->{
                            if (stand_power.getStandManifestation() instanceof StandEntity){
                                final StandEntity stand_manifest=(StandEntity) stand_power.getStandManifestation();
                                stand_manifest.getPersistentData().putBoolean("MEMORY_DISK_AFFECTED",false);
                            }
                        });
                    }
                    victim.getPersistentData().putBoolean("MEMORY_DISK_AFFECTED",false);
                    itemStack.shrink(itemStack.getCount());
                }
                userPower.setCooldownTimer(InitStands.WHITESNAKE_REMOVE_STAND_DISC.get(), 30);
                userPower.setCooldownTimer(InitStands.REMOVING_THE_MEMORY_DISK.get(), 30);
            }
        }
    }

    @Override
    protected ActionConditionResult checkStandConditions(StandEntity stand, IStandPower power, ActionTarget target) {
        //constants
        final LivingEntity user=power.getUser();
        final Entity entity=target.getEntity();
        //sanity check
        if (user instanceof PlayerEntity && entity instanceof LivingEntity){
            final PlayerEntity player=(PlayerEntity) user;
            final LivingEntity target_living=(LivingEntity) entity;
            if (!MemoryDiscItem.playerHasDisc(player,target_living) || MemoryDiscItem.discBelongsTo(MemoryDiscItem.getDiscInHand(player),target_living)) return ActionConditionResult.POSITIVE;
        }
        return ActionConditionResult.NEGATIVE;
    }

    @Override
    protected void holdTick(World world, LivingEntity user, IStandPower power, int ticksHeld, ActionTarget target, boolean requirementsFulfilled){

    }

    @Override
    public void onTaskSet(World world, StandEntity standEntity, IStandPower standPower, Phase phase, StandEntityTask task, int ticks) {
        if (task.getPhase()==Phase.BUTTON_HOLD && !standEntity.isManuallyControlled()) {
            //constants
            final ActionTarget target=task.getTarget();
            final LivingEntity entity=(LivingEntity) target.getEntity();
            final LivingEntity user=standPower.getUser();
            //sanity check
            if (entity instanceof LivingEntity && entity.isAlive() && user instanceof LivingEntity) {
                //constants
                final Vector3d dir_difference=entity.position().subtract(user.position());
                final Vector3d normal_dir=dir_difference.normalize();
                //stuff
                standEntity.lookAt(EntityAnchorArgument.Type.EYES, normal_dir);
                standEntity.moveTo(entity.position().subtract(normal_dir));
            }
        }
    }

    @Override
    protected boolean standKeepsTarget(ActionTarget target){
        return true;//target.getType()==TargetType.ENTITY && target.getEntity() instanceof LivingEntity && !(target.getEntity() instanceof StandEntity);
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
