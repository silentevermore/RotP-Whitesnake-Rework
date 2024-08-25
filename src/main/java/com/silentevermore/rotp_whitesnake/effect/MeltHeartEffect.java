package com.silentevermore.rotp_whitesnake.effect;

import com.github.standobyte.jojo.potion.UncurableEffect;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.init.InitEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class MeltHeartEffect extends UncurableEffect {

    private int lastDuration = -1;
    public MeltHeartEffect(EffectType type, int liquidColor) {
        super(type, liquidColor);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int level) {
        lastDuration = duration;
        return duration > 0 && duration % 50 == 0;
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int level) {
        if (entity.tickCount >= 100) {
            entity.hurt(DamageSource.WITHER, Math.min(level + 1, Math.round(lastDuration / 20F)));
            entity.level.addParticle(ParticleTypes.CLOUD, entity.getRandomX(1.0), entity.getRandomY(), entity.getRandomZ(1.0), 0, 0, 0);
        }
    }
    public static void applyEffect(LivingEntity entity, int level, int duration) {
        if (!entity.level.isClientSide()){
                duration = duration / 2;
                level = Math.round((float) level / 2);
            }
            entity.addEffect(new EffectInstance(InitEffects.MELT_HEART_EFFECT.get(), duration, level));
        }
    @Mod.EventBusSubscriber(modid = RotpWhitesnakeAddon.MOD_ID)
    public static class Events {
        @SubscribeEvent
        public static void onLivingHeal(LivingHealEvent event) {
            LivingEntity entity = (LivingEntity) event.getEntity();
            if (entity.hasEffect(InitEffects.MELT_HEART_EFFECT.get()))
                event.setCanceled(true);
        }
    }
}

