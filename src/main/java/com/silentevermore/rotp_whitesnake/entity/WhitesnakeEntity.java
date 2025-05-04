package com.silentevermore.rotp_whitesnake.entity;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Optional;

public class WhitesnakeEntity extends StandEntity{
    //constants
    private float LAST_SHAPESHIFT=0f;
    private boolean DISGUISED=false;
    private Optional<EntityType<?>> DISGUISE_ENTITY=Optional.empty();
    //builder
    public WhitesnakeEntity(StandEntityType<WhitesnakeEntity> type, World world){super(type, world);}
    //methods
    public Optional<EntityType<?>> getEntityForDisguise(){
        return this.DISGUISE_ENTITY;
    }
    public boolean isDisguisedOnce(){
        return this.DISGUISED;
    }
    public void setDisguisedOnce(boolean value){
        this.DISGUISED=value;
    }
    public float getShapeshiftTick(){
        return this.LAST_SHAPESHIFT;
    }
    public void setShapeshiftTick(float tick){
        this.LAST_SHAPESHIFT=tick;
    }
    public float shapeshiftTickDifference(float now){
        return now-this.getShapeshiftTick();
    }
    public void setEntityForDisguise(EntityType<?> entity){
        this.DISGUISE_ENTITY=Optional.ofNullable(entity);
    }
    //overriden methods
}
