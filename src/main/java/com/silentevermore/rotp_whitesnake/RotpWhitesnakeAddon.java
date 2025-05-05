package com.silentevermore.rotp_whitesnake;

import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.util.ForgeBusEventSubscriber;
import com.silentevermore.rotp_whitesnake.init.*;
import com.silentevermore.rotp_whitesnake.network.PacketHandler;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Mod(RotpWhitesnakeAddon.MOD_ID)
public class RotpWhitesnakeAddon{
    //constants
    private static final LinkedList<EntityType<?>> ENTITIES_FOR_DISGUISE=new LinkedList<>();
    public static final String MOD_ID = "rotp_whitesnake";
    private static final Logger LOGGER = LogManager.getLogger();

    public RotpWhitesnakeAddon(){
        final IEventBus modEventBus=FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, WhitesnakeConfig.commonSpec);

        InitEntities.ENTITIES.register(modEventBus);
        InitSounds.SOUNDS.register(modEventBus);
        InitStands.ACTIONS.register(modEventBus);
        InitStands.STANDS.register(modEventBus);
        InitBlocks.BLOCKS.register(modEventBus);
        InitItems.ITEMS.register(modEventBus);

        modEventBus.addListener(this::preInit);
    }
    public static LinkedList<EntityType<?>> getEntitiesForDisguise(){
        return ENTITIES_FOR_DISGUISE;
    }
    private void preInit(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            PacketHandler.init();
            //assemble entities suitable for disguise ability
            ForgeRegistries.ENTITIES.getEntries().forEach(entry->{
                EntityType<?> entityType=entry.getValue();
                String categ=entityType.getCategory().getName();
                if (entityType.canSummon()
                        && (categ.equals("creature") || categ.equals("monster"))
                        && !entityType.equals(EntityType.ENDER_DRAGON)){
                    getLogger().debug(entityType.toString() + ": " + categ);
                    ENTITIES_FOR_DISGUISE.add(entityType);
                }
            });
        });
    }
    public static Logger getLogger() {
        return LOGGER;
    }
}
