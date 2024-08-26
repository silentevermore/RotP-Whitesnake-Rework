package com.silentevermore.rotp_whitesnake.entity.projectile;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.entity.damaging.projectile.ModdedProjectileEntity;
import com.github.standobyte.jojo.init.ModItems;
import com.github.standobyte.jojo.init.ModStatusEffects;
import com.github.standobyte.jojo.item.StandDiscItem;
import com.github.standobyte.jojo.util.mc.MCUtil;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.silentevermore.rotp_whitesnake.block.MeltHeartBlock;
import com.silentevermore.rotp_whitesnake.init.InitBlocks;
import com.silentevermore.rotp_whitesnake.init.InitEntities;
import com.silentevermore.rotp_whitesnake.init.InitSounds;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.Direction;
import net.minecraft.util.math.*;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

import java.util.concurrent.ThreadLocalRandom;

import static com.silentevermore.rotp_whitesnake.block.MeltHeartBlock.LAYERS;

public class MeltHeartProjectile extends ModdedProjectileEntity {
    //constants
    private boolean HIT_CEILING=false;
    //builder
    public MeltHeartProjectile(LivingEntity shooter, World world) {
        super(InitEntities.WS_MHPROJ.get(), shooter, world);
    }
    public MeltHeartProjectile(EntityType<? extends MeltHeartProjectile> type, World world) {
        super(type, world);
    }
    //methods
    @Override
    public int ticksLifespan() {
        return 100;
    }
    @Override
    protected double getInertia() {
        return .1d;
    }
    @Override
    protected boolean hasGravity() {
        return true;
    }
    @Override
    protected double getGravityAcceleration() {
        return .1d;
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

    @Override
    public void tick() {
        //calling super method
        super.tick();
        //constants
        final float ticks=(float)tickCount;
        final float lifetime=ticksLifespan()/4f;
        final float tick_difference=MathHelper.clamp(ticks/lifetime,0,1);
        final Vector3d delta_move=getDeltaMovement();
        //stuff
        if (!HIT_CEILING){
            setDeltaMovement(
                    delta_move.x(),
                    (2-Math.exp(tick_difference)),
                    delta_move.z()
            );
        }
    }

    protected void breakProjectile(ActionTarget.TargetType targetType, RayTraceResult hitTarget) {
        final BlockPos blockPos=blockPosition();
        final Vector3d delta_move=getDeltaMovement();
        //sanity check
        if (level.getBlockState(blockPos.above()).getBlock()!=Blocks.AIR) HIT_CEILING=true;
        if (level.getBlockState(blockPos.below()).getBlock()!=Blocks.AIR) super.breakProjectile(targetType, hitTarget);
        if (HIT_CEILING){
            setDeltaMovement(
                    delta_move.x()*1.5f,
                    -1,
                    delta_move.z()*1.5f
            );
        }
        if (!level.isClientSide()){
            //constants
            final ThreadLocalRandom rng=ThreadLocalRandom.current();
            final ServerWorld serverWorld=(ServerWorld) level;
            final MeltHeartBlock MYH_BLOCK= InitBlocks.MELT_HEART_BLOCK.get();
            //stuff
            if (level.getBlockState(blockPos.below()).getBlock()!=Blocks.AIR
                    && level.getBlockState(blockPos).getBlock()==Blocks.AIR
                    && level.getBlockState(blockPos.below()).getBlock()!=InitBlocks.MELT_HEART_BLOCK.get())
            {
                if (level.getBlockState(blockPos).getBlock()!=MYH_BLOCK){
                    level.setBlockAndUpdate(blockPos, MYH_BLOCK.defaultBlockState().setValue(LAYERS, 1));
                }
                level.getBlockStates(new AxisAlignedBB(blockPos)).forEach(state->{
                    if (state.getBlock()==MYH_BLOCK && state.getValue(LAYERS)<8 && rng.nextFloat()<.1){
                        level.setBlockAndUpdate(blockPos, state
                                .setValue(LAYERS,state.getValue(LAYERS)+1));
                    }
                });
            }
        }
    }
}


