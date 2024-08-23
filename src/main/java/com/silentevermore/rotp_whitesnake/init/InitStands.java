package com.silentevermore.rotp_whitesnake.init;

import com.silentevermore.rotp_whitesnake.action.*;
import com.silentevermore.rotp_whitesnake.client.render.WhitesnakeModel;
import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.github.standobyte.jojo.action.Action;
import com.github.standobyte.jojo.action.stand.*;
import com.github.standobyte.jojo.entity.stand.StandEntityType;
import com.github.standobyte.jojo.init.ModSounds;
import com.github.standobyte.jojo.init.power.stand.EntityStandRegistryObject;
import com.github.standobyte.jojo.init.power.stand.ModStandsInit;
import com.github.standobyte.jojo.power.impl.stand.StandInstance.StandPart;
import com.github.standobyte.jojo.power.impl.stand.stats.StandStats;
import com.github.standobyte.jojo.power.impl.stand.type.EntityStandType;
import com.github.standobyte.jojo.power.impl.stand.type.StandType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

public class InitStands {
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<Action<?>> ACTIONS = DeferredRegister.create(
            (Class<Action<?>>) ((Class<?>) Action.class), RotpWhitesnakeAddon.MOD_ID);
    @SuppressWarnings("unchecked")
    public static final DeferredRegister<StandType<?>> STANDS = DeferredRegister.create(
            (Class<StandType<?>>) ((Class<?>) StandType.class), RotpWhitesnakeAddon.MOD_ID);

    // ======================================== Whitesnake ========================================

    public static final RegistryObject<StandEntityAction> WHITESNAKE_PUNCH = ACTIONS.register("whitesnake_punch",
            () -> new StandEntityLightAttack(new StandEntityLightAttack.Builder()
                    .punchSound(InitSounds.WHITESNAKE_PUNCH_LIGHT)
                    .shout(InitSounds.PUCCI_PUNCH)));

    public static final RegistryObject<StandEntityAction> WHITESNAKE_BARRAGE = ACTIONS.register("whitesnake_barrage",
            () -> new StandEntityMeleeBarrage(new StandEntityMeleeBarrage.Builder()
                    .barrageHitSound(InitSounds.WHITESNAKE_PUNCH_LIGHT)
                    .standSound(InitSounds.WHITESNAKE_USHYA)));

    public static final RegistryObject<StandEntityHeavyAttack> WHITESNAKE_COMBO_PUNCH = ACTIONS.register("whitesnake_combo_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.WHITESNAKE_PUNCH_HEAVY)
                    .standSound(InitSounds.WHITESNAKE_PUNCH_COMBO)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityHeavyAttack> WHITESNAKE_HEAVY_PUNCH = ACTIONS.register("whitesnake_heavy_punch",
            () -> new StandEntityHeavyAttack(new StandEntityHeavyAttack.Builder()
                    .punchSound(InitSounds.WHITESNAKE_PUNCH_HEAVY)
                    .standSound(InitSounds.WHITESNAKE_PUNCH_COMBO)
                    .partsRequired(StandPart.ARMS)
                    .setFinisherVariation(WHITESNAKE_COMBO_PUNCH)
                    .shiftVariationOf(WHITESNAKE_PUNCH).shiftVariationOf(WHITESNAKE_BARRAGE)));


    public static final RegistryObject<StandEntityAction> WHITESNAKE_BLOCK = ACTIONS.register("whitesnake_block",
            StandEntityBlock::new);

    public static final RegistryObject<MeltYourHeart> MELT_YOUR_HEART = ACTIONS.register("melt_your_heart",
            () -> new MeltYourHeart(new MeltYourHeart.Builder()
                    .holdType(100)
                    .cooldown(20,100,0)
                    .staminaCost(10)
                    .standUserWalkSpeed(.5f)
                    .autoSummonStand()
            ));

    public static final RegistryObject<Blindness> BLINDNESS = ACTIONS.register("blindness",
            () -> new Blindness(new Blindness.Builder()
                    .holdType()
                    .staminaCost(50)
                    .resolveLevelToUnlock(3)
                    .holdToFire(15, false)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<WhitesnakeRemovingMemoryDisk> REMOVING_THE_MEMORY_DISK = ACTIONS.register("removing_the_memory_disk",
            () -> new WhitesnakeRemovingMemoryDisk(new WhitesnakeRemovingMemoryDisk.Builder()
                    .standPose(WhitesnakeModel.REMOVE_DISC)
                    .standSound(InitSounds.WHITESNAKE_REMOVE_DISC)
                    .staminaCost(200)
                    .resolveLevelToUnlock(0).shout(InitSounds.PUCCI_REMOVE_DISK)
                    .holdToFire(10, false)
                    .partsRequired(StandPart.ARMS)));

    public static final RegistryObject<StandEntityAction> WHITESNAKE_REMOVE_STAND_DISC = ACTIONS.register("whitesnake_remove_stand_disc",
            () -> new WhitesnakeRemovingStandDisc(new WhitesnakeRemovingStandDisc.Builder()
                    .holdType()
                    .shout(InitSounds.PUCCI_REMOVE_DISK)
                    .resolveLevelToUnlock(2)
                    .standPose(WhitesnakeModel.REMOVE_DISC)
                    .holdToFire(30, false)
                    .standOffsetFromUser(0.667, 0.2, 0)
                    .partsRequired(StandPart.ARMS)
            ));
    public static final RegistryObject<WhitesnakeThrowDisc> WHITESNAKE_THROW_DISC = ACTIONS.register("whitesnake_throw_disc",
            () -> new WhitesnakeThrowDisc(new StandEntityAction.Builder()
                    .standSound(ModSounds.STAND_PUNCH_SWING)
                    .standPerformDuration(15)
                    .staminaCost(3)
                    .resolveLevelToUnlock(2)
                    .standOffsetFront()
                    .cooldown(300)
                    .partsRequired(StandPart.ARMS)));

    public static final EntityStandRegistryObject<EntityStandType<StandStats>, StandEntityType<WhitesnakeEntity>> WHITESNAKE =
            new EntityStandRegistryObject<>("whitesnake",
                    STANDS,
                    () -> new EntityStandType.Builder<StandStats>()
                            .color(0xD5D5D5)
                            .storyPartName(ModStandsInit.PART_6_NAME)
                            .leftClickHotbar(
                                    WHITESNAKE_PUNCH.get(),
                                    WHITESNAKE_BARRAGE.get(),
                                    WHITESNAKE_THROW_DISC.get()
                            )
                            .rightClickHotbar(
                                    WHITESNAKE_BLOCK.get(),
                                    REMOVING_THE_MEMORY_DISK.get(),
                                    WHITESNAKE_REMOVE_STAND_DISC.get(),
                                    BLINDNESS.get(),
                                    MELT_YOUR_HEART.get()
                            )
                            .defaultStats(StandStats.class, new StandStats.Builder()
                                    .power(14)
                                    .speed(14)
                                    .range(10, 20)
                                    .durability(16)
                                    .precision(14)
                                    .randomWeight(1.5)
                            )
                            .addSummonShout(InitSounds.PUCCI_WHITESNAKE)
                            .addOst(InitSounds.WHITESNAKE_OST)
                            .build(),

                    InitEntities.ENTITIES,
                    () -> new StandEntityType<WhitesnakeEntity>(WhitesnakeEntity::new, 0.65F, 1.95F)
                            .summonSound(InitSounds.WHITESNAKE_SUMMON)
                            .unsummonSound(InitSounds.WHITESNAKE_UNSUMMON))
                    .withDefaultStandAttributes();
}
