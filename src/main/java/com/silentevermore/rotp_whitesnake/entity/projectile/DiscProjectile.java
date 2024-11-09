package com.silentevermore.rotp_whitesnake.entity.projectile;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.init.ModItems;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.init.power.JojoCustomRegistries;
import com.github.standobyte.jojo.item.StandDiscItem;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandInstance;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.init.InitBlocks;
import com.silentevermore.rotp_whitesnake.init.InitEntities;
import com.silentevermore.rotp_whitesnake.init.InitItems;
import com.silentevermore.rotp_whitesnake.init.InitSounds;
import com.silentevermore.rotp_whitesnake.item.MemoryDiscItem;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class DiscProjectile extends ModdedProjectileEntity {
    public DiscProjectile(LivingEntity shooter, World world) {
        super(InitEntities.WS_DISC.get(), shooter, world);
    }

    public DiscProjectile(EntityType<? extends DiscProjectile> type, World world) {
        super(type, world);
    }

    private ItemStack withStand(){
        final CompoundNBT disc_nbt=this.getPersistentData();
        final CompoundNBT disc_stand=disc_nbt.getCompound("disc_stand");
        final StandInstance stand=StandInstance.fromNBT(disc_stand);
        return StandDiscItem.withStand(new ItemStack(ModItems.STAND_DISC.get()),stand);
    }

    private ItemStack withMemory(){
        final CompoundNBT disc_nbt=this.getPersistentData();
        return MemoryDiscItem.withTargetNbt(new ItemStack(InitItems.MEMORY_DISC_ITEM.get()), disc_nbt.getCompound("player_data"));
    }

    @Override
    public int ticksLifespan() {
        return 100;
    }

    private void dropDisc(){
        if (!level.isClientSide()){
            final String disc_type=this.getPersistentData().getString("TYPE");
            final ItemEntity itemEntity=new ItemEntity(level, getX(), getY(), getZ());
            if (disc_type.equals("stand_disc")){
                itemEntity.setItem(withStand());
            }else if (disc_type.equals("memory_disc")){
                itemEntity.setItem(withMemory());
            }
            level.addFreshEntity(itemEntity);
        }
    }

    protected void breakProjectile(ActionTarget.TargetType targetType, RayTraceResult hitTarget) {
        super.breakProjectile(targetType, hitTarget);
        level.playSound(null, getX(), getY(), getZ(), InitSounds.WHITESNAKE_PUNCH_LIGHT.get(), getSoundSource(), 1.0F, 1.0F);

        if (targetType==ActionTarget.TargetType.BLOCK) dropDisc();
    }

    @Override
    protected boolean hurtTarget(Entity target, @Nullable LivingEntity owner){
        if (!target.level.isClientSide()){
            if (target instanceof LivingEntity){
                final LivingEntity targetLiving=(LivingEntity) target;
                final String disc_type=this.getPersistentData().getString("TYPE");
                if (disc_type.equals("stand_disc")){
                    final boolean is_stand_compatible=IStandPower.getStandPowerOptional(targetLiving).isPresent();
                    if (!is_stand_compatible) dropDisc();
                    IStandPower.getStandPowerOptional(targetLiving).ifPresent(power -> {
                        targetLiving.addEffect(new EffectInstance(Effects.BLINDNESS, 20, 0, true, false));
                        targetLiving.addEffect(new EffectInstance(Effects.BLINDNESS, 20, 0, true, false));
                        targetLiving.addEffect(new EffectInstance(Effects.CONFUSION, 100, 0, true, false));
                        giveStand(targetLiving);
                    });
                }else if (disc_type.equals("memory_disc")){
                    final CompoundNBT disc_nbt=this.getPersistentData().getCompound("player_data");
                    if (disc_nbt.getString("PLAYER_ID").equals(targetLiving.getUUID().toString())){
                        targetLiving.getPersistentData().putBoolean("MEMORY_DISK_AFFECTED",false);
                    }else{
                        dropDisc();
                    }
                }
                this.remove();
            }
        }
        return super.hurtTarget(target, owner);
    }

    private void giveStand(LivingEntity targetLiving) {
        final CompoundNBT disc_nbt=this.getPersistentData();
        final CompoundNBT disc_stand=disc_nbt.getCompound("disc_stand");
        if (disc_stand instanceof CompoundNBT){
            final StandInstance stand=StandInstance.fromNBT(disc_stand);
            IStandPower.getStandPowerOptional(targetLiving).ifPresent(power->{
                power.putOutStand().ifPresent(prevStand -> {
                    MCUtil.giveItemTo(targetLiving, StandDiscItem.withStand(new ItemStack(ModItems.STAND_DISC.get()), prevStand), false);
                });
                power.giveStandFromInstance(stand,false);
            });
        }
    }

    @Override
    protected float getMaxHardnessBreakable() {
        return 0;
    }

    @Override
    public boolean standDamage() {
        return false;
    }

    @Override
    public float getBaseDamage() {
        return 1F;
    }
}


