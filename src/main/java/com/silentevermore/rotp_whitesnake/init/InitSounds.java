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

    public static final Supplier<SoundEvent> WHITESNAKE_SUMMON = SOUNDS.register("whitesnake_summon",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_summon")));

    public static final Supplier<SoundEvent> WHITESNAKE_UNSUMMON = SOUNDS.register("whitesnake_unsummon",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_unsummon")));

    public static final Supplier<SoundEvent> WHITESNAKE_PUNCH_LIGHT = SOUNDS.register("whitesnake_punch",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_punch")));

    public static final Supplier<SoundEvent> WHITESNAKE_PUNCH_HEAVY = SOUNDS.register("whitesnake_punch_heavy",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_punch_heavy")));

    public static final Supplier<SoundEvent> WHITESNAKE_BARRAGE = SOUNDS.register("whitesnake_barrage",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_barrage")));

    public static final RegistryObject<SoundEvent> WHITESNAKE_REMOVE_DISC = SOUNDS.register("whitesnake_remove_disc",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_remove_disc")));

    public static final RegistryObject<SoundEvent> WHITESNAKE_USHYA = SOUNDS.register("whitesnake_ushya",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_ushya")));

    public static final RegistryObject<SoundEvent> PUCCI_REMOVE_DISK = SOUNDS.register("pucci_remove_disk",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "pucci_remove_disk")));

    public static final RegistryObject<SoundEvent> PUCCI_PUNCH = SOUNDS.register("pucci_punch",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "pucci_punch")));

    public static final RegistryObject<SoundEvent> WHITESNAKE_PUNCH_COMBO = SOUNDS.register("whitesnake_punch_combo",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_punch_combo")));

    public static final RegistryObject<SoundEvent> WHITESNAKE_MELT_HEART = SOUNDS.register("whitesnake_melt_heart",
            () -> new SoundEvent(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_melt_heart")));

    static final OstSoundList WHITESNAKE_OST = new OstSoundList(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake_ost"), SOUNDS);

}
