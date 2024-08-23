package com.silentevermore.rotp_whitesnake.entity.projectile;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.client.sound.ClientTickingSoundsHelper;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;

import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.power.impl.nonstand.INonStandPower;
import com.github.standobyte.jojo.util.general.GeneralUtil;
import com.github.standobyte.jojo.util.mc.damage.DamageUtil;
import com.silentevermore.rotp_whitesnake.init.InitEntities;
import com.silentevermore.rotp_whitesnake.init.InitSounds;
import net.minecraft.client.audio.Sound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
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

    @Override
    public int ticksLifespan() {
        return 100;
    }


    protected void breakProjectile(ActionTarget.TargetType targetType, RayTraceResult hitTarget) {
        if (targetType != ActionTarget.TargetType.ENTITY || ((EntityRayTraceResult) hitTarget).getEntity() instanceof LivingEntity) {
            super.breakProjectile(targetType, hitTarget);
        }
    }

    @Override
    protected boolean hurtTarget(Entity target, @Nullable LivingEntity owner) {
        if (target instanceof LivingEntity) {
            LivingEntity targetLiving = (LivingEntity) target;
            targetLiving.addEffect(new EffectInstance(Effects.BLINDNESS, 100, 0, true, false));
            targetLiving.addEffect(new EffectInstance(Effects.CONFUSION, 100, 0, true, false));
            targetLiving.addEffect(new EffectInstance(ModStatusEffects.STUN.get(), 100, 0, true, false));
            level.playSound(null, getX(), getY(), getZ(), InitSounds.WHITESNAKE_PUNCH_LIGHT.get(), getSoundSource(), 1.0F, 1.0F);
            remove();
            return false;
        }
        return super.hurtTarget(target, owner);
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
        return 0.75F;
    }
}


