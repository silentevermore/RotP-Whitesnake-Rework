package com.silentevermore.rotp_whitesnake.entity;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityType;

import com.silentevermore.rotp_whitesnake.client.render.WhitesnakeModel;
import com.silentevermore.rotp_whitesnake.client.render.WhitesnakeRenderer;
import net.minecraft.world.World;

public class WhitesnakeEntity extends StandEntity{
    //builder
    public WhitesnakeEntity(StandEntityType<WhitesnakeEntity> type, World world) {
        super(type, world);
    }
    //methods
}
