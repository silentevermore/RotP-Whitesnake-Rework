package com.silentevermore.rotp_whitesnake.init;

import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.projectile.DiscProjectile;
import com.silentevermore.rotp_whitesnake.entity.projectile.MeltHeartProjectile;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEntities{
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, RotpWhitesnakeAddon.MOD_ID);

    public static final RegistryObject<EntityType<DiscProjectile>> WS_DISC = ENTITIES.register("disc",
            () -> EntityType.Builder.<DiscProjectile>of(DiscProjectile::new, EntityClassification.MISC).sized(0.25f, 0.25f).noSummon().noSave().setUpdateInterval(10)
                    .build(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "disc").toString()));

    public static final RegistryObject<EntityType<MeltHeartProjectile>> WS_MHPROJ = ENTITIES.register("melt_your_heart",
            () -> EntityType.Builder.<MeltHeartProjectile>of(MeltHeartProjectile::new, EntityClassification.MISC).sized(0.5f, 0.5f).noSummon().noSave().setUpdateInterval(10)
                    .build(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "melt_heart").toString()));
}
