package com.silentevermore.rotp_whitesnake.init;

import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.projectile.DiscProjectile;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpWhitesnakeAddon.MOD_ID);

    public static final RegistryObject<EntityType<DiscProjectile>> WS_DISC = ENTITIES.register("disc",
            () -> EntityType.Builder.<DiscProjectile>of(DiscProjectile::new, EntityClassification.MISC).sized(0.5F, 0.25F).noSummon().noSave().setUpdateInterval(10)
                    .build(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "disc").toString()));
}
