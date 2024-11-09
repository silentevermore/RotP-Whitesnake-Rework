package com.silentevermore.rotp_whitesnake;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityLeaveWorldEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid=RotpWhitesnakeAddon.MOD_ID)
public class EventHandler{
    //entity's immobility after memory disk ejection
    @SubscribeEvent
    public static void EntityJoinWorldEvent(EntityJoinWorldEvent event){
        final World world=event.getWorld();
        if (event.getEntity() instanceof StandEntity){
            final StandEntity stand=(StandEntity) event.getEntity();
            final LivingEntity user=StandUtil.getStandUser(stand);
            if (user!=null){
                stand.getPersistentData().putBoolean("MEMORY_DISK_AFFECTED", user.getPersistentData().getBoolean("MEMORY_DISK_AFFECTED"));
            }
        }
    }
    @SubscribeEvent
    public static void EntityLeaveWorldEvent(EntityLeaveWorldEvent event){}
    @SubscribeEvent
    public static void LivingAttackEvent(LivingAttackEvent event){
        if (event.getSource().getEntity() instanceof LivingEntity){
            final LivingEntity source_living=(LivingEntity)event.getSource().getEntity();
            if (source_living.getPersistentData().getBoolean("MEMORY_DISK_AFFECTED")){
                event.setCanceled(true);
            }
        }
    }
}
