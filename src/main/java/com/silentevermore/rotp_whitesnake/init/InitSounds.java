package com.silentevermore.rotp_whitesnake.init;

import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.util.mc.OstSoundList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class InitSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, RotpWhitesnakeAddon.MOD_ID);
    
    public static final RegistryObject<SoundEvent> PUCCI_WHITESNAKE = SOUNDS.register("pucci_whitesnake", 
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "pucci_whitesnake")));

    public static final Supplier<SoundEvent> WHITESNAKE_SUMMON = ModSounds.STAND_SUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> WHITESNAKE_UNSUMMON = ModSounds.STAND_UNSUMMON_DEFAULT;
    
    public static final Supplier<SoundEvent> WHITESNAKE_PUNCH_LIGHT = ModSounds.STAND_PUNCH_LIGHT;
    
    public static final Supplier<SoundEvent> WHITESNAKE_PUNCH_HEAVY = ModSounds.STAND_PUNCH_HEAVY;
    
    public static final Supplier<SoundEvent> WHITESNAKE_BARRAGE = SOUNDS.register("whitesnake_barrage",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_barrage")));

    public static final RegistryObject<SoundEvent> WHITESNAKE_REMOVE_DISC = SOUNDS.register("whitesnake_remove_disc",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_remove_disc")));

    public static final RegistryObject<SoundEvent> WHITESNAKE_USHYA = SOUNDS.register("whitesnake_ushya",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_ushya")));

    public static final RegistryObject<SoundEvent> PUCCI_REMOVE_DISK = SOUNDS.register("pucci_remove_disk",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "pucci_remove_disk")));

    static final OstSoundList WHITESNAKE_OST = new OstSoundList(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_ost"), SOUNDS);

}
