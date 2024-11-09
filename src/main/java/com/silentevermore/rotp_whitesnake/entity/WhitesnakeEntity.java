package com.silentevermore.rotp_whitesnake.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class WhitesnakeEntity extends StandEntity {
    //constants
    private float LAST_SHAPESHIFT=0f;
    private boolean DISGUISED=false;
    private LivingEntity DISGUISE_ENTITY=null;
    //builder
    public WhitesnakeEntity(StandEntityType<WhitesnakeEntity> type, World world) {
        super(type, world);
    }
    //methods
    public @Nullable LivingEntity getEntityForDisguise(){
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
    public void setEntityForDisguise(LivingEntity entity){
        this.DISGUISE_ENTITY=StandUtil.getStandUser(entity);
    }
    public boolean isEntityDisguise(LivingEntity target){
        if (this.getEntityForDisguise()!=null){
            return this.getEntityForDisguise().equals(StandUtil.getStandUser(target));
        }
        return false;
    }
}
