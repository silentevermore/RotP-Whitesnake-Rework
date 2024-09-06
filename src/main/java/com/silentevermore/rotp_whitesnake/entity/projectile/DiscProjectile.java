package com.silentevermore.rotp_whitesnake.entity.projectile;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.init.ModItems;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.init.power.JojoCustomRegistries;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.init.InitBlocks;
import com.silentevermore.rotp_whitesnake.init.InitEntities;
import com.silentevermore.rotp_whitesnake.init.InitSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
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

    public static ItemStack withStand(ItemStack discStack, String tag) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("StandType", JojoCustomRegistries.STANDS.getKeyAsString(JojoCustomRegistries.STANDS.getRegistry().getValue(new ResourceLocation(tag))));
        discStack.getOrCreateTag().put("Stand", nbt);
        return discStack;
    }

    @Override
    public int ticksLifespan() {
        return 100;
    }

    protected void breakProjectile(ActionTarget.TargetType targetType, RayTraceResult hitTarget) {
        if (targetType != ActionTarget.TargetType.ENTITY || ((EntityRayTraceResult) hitTarget).getEntity() instanceof LivingEntity) {
            super.breakProjectile(targetType, hitTarget);
            level.playSound(null, getX(), getY(), getZ(), InitSounds.WHITESNAKE_PUNCH_LIGHT.get(), getSoundSource(), 1.0F, 1.0F);
        }
        BlockPos blockPos = blockPosition();
        if (level.getBlockState(blockPos.below()).getBlock() != Blocks.AIR
                && level.getBlockState(blockPos).getBlock() == Blocks.AIR
                && level.getBlockState(blockPos.below()).getBlock() != InitBlocks.MELT_HEART_BLOCK.get()) {
            for (String tag : this.getTags()) {
                ItemEntity itemEntity = new ItemEntity(level, getX(), getY(), getZ());
                itemEntity.setItem(withStand(new ItemStack(ModItems.STAND_DISC.get()), tag));
                level.addFreshEntity(itemEntity);
            }
        }
    }

    @Override
    protected boolean hurtTarget(Entity target, @Nullable LivingEntity owner) {
        if (!target.level.isClientSide()) {
            if (target instanceof LivingEntity) {
                LivingEntity targetLiving = (LivingEntity) target;
                if (targetLiving instanceof PlayerEntity) {
                    IStandPower.getStandPowerOptional(targetLiving).ifPresent(standPower -> {
                        for (String tag : this.getTags()) {
                            RotpWhitesnakeAddon.getLogger().debug("Tag: " + tag);
//                            StandType<?> standType = JojoCustomRegistries.STANDS.getRegistry().getValue(new ResourceLocation(tag));
                            if (!standPower.hasPower()) {
                                MCUtil.runCommand(targetLiving, "stand clear @s");
                                giveStandByTag(targetLiving, tag);
                                targetLiving.addEffect(new EffectInstance(Effects.BLINDNESS, 100, 0, true, false));
                                targetLiving.addEffect(new EffectInstance(Effects.CONFUSION, 100, 0, true, false));
                                targetLiving.addEffect(new EffectInstance(ModStatusEffects.STUN.get(), 100, 0, true, false));
                            } else {
                                MCUtil.giveItemTo(standPower.getUser(), withStand(new ItemStack(ModItems.STAND_DISC.get()), tag), true);
                                giveStandByTag(targetLiving, tag);
                            }
                        }
                    });
                }
                this.remove();
                return false;
            }
        }
        return super.hurtTarget(target, owner);
    }

    private void giveStandByTag(LivingEntity targetLiving, String tag) {
        MCUtil.runCommand(targetLiving, "stand give @s " + tag + " false");
        if (targetLiving instanceof PlayerEntity) {
            if (!((PlayerEntity) targetLiving).abilities.instabuild) {
                MCUtil.runCommand(targetLiving, "standlevel set @s 0");
            }
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
        return 0.75F;
    }
}


