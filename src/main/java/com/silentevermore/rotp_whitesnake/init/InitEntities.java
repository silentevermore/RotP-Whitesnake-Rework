package com.silentevermore.rotp_whitesnake.init;

import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import net.minecraft.entity.EntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpWhitesnakeAddon.MOD_ID);
}
