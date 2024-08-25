package com.silentevermore.rotp_whitesnake.init;

import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.effect.MeltHeartEffect;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitEffects {

public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(
        ForgeRegistries.POTIONS, RotpWhitesnakeAddon.MOD_ID);

    public static final RegistryObject<Effect> MELT_HEART_EFFECT = EFFECTS.register("melt_heart_effect",
            () -> new MeltHeartEffect(EffectType.HARMFUL,0xe7d6ff));
}