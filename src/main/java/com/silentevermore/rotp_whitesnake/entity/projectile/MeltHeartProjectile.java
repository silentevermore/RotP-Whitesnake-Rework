package com.silentevermore.rotp_whitesnake.entity.projectile;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.silentevermore.rotp_whitesnake.block.MeltHeartBlock;
import com.silentevermore.rotp_whitesnake.init.InitBlocks;
import com.silentevermore.rotp_whitesnake.init.InitEntities;
import com.silentevermore.rotp_whitesnake.init.InitSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

import java.util.concurrent.ThreadLocalRandom;

import static com.silentevermore.rotp_whitesnake.block.MeltHeartBlock.LAYERS;

public class MeltHeartProjectile extends ModdedProjectileEntity {

    public MeltHeartProjectile(LivingEntity shooter, World world) {
        super(InitEntities.WS_MHPROJ.get(), shooter, world);
    }

    public MeltHeartProjectile(EntityType<? extends MeltHeartProjectile> type, World world) {
        super(type, world);
    }

    @Override
    public int ticksLifespan() {
        return 100;
    }
    @Override
    protected boolean hasGravity() {
        return true;
    }
    @Override
    protected double getGravityAcceleration() {
        return .5D;
    }

    protected void breakProjectile(ActionTarget.TargetType targetType, RayTraceResult hitTarget) {
        if (targetType != ActionTarget.TargetType.ENTITY || ((EntityRayTraceResult) hitTarget).getEntity() instanceof LivingEntity) {
            super.breakProjectile(targetType, hitTarget);
            //sanity check
            if (!level.isClientSide()){
                //constants
                final ThreadLocalRandom rng=ThreadLocalRandom.current();
                final Vector3d pos=position();
                final ServerWorld serverWorld=(ServerWorld) level;
                final MeltHeartBlock MYH_BLOCK= InitBlocks.MELT_HEART_BLOCK.get();
                final BlockPos blockPos=new BlockPos(pos.x(), pos.y(), pos.z());
                //stuff
                if (level.getBlockState(blockPos).getBlock()!=Blocks.AIR
                        && level.getBlockState(blockPos.below()).isFaceSturdy(level, blockPos.above(), Direction.UP))
                {
                    if (level.getBlockState(blockPos.below()).getBlock()!=MYH_BLOCK){
                        level.setBlockAndUpdate(blockPos, MYH_BLOCK.defaultBlockState().setValue(LAYERS, 1));
                    }
                    level.getBlockStates(new AxisAlignedBB(blockPos)).forEach(state->{
                        if (state.getBlock()==MYH_BLOCK && state.getValue(LAYERS)<8 && rng.nextFloat()<.1){
                            level.setBlockAndUpdate(blockPos, state.setValue(LAYERS,state.getValue(LAYERS)+1));
                        }
                    });
                }
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
        return 0f;
    }
}


