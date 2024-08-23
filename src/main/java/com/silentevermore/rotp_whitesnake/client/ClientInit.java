package com.silentevermore.rotp_whitesnake.client;

import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.client.render.WhitesnakeRenderer;

import com.silentevermore.rotp_whitesnake.client.render.projectile.DiscRenderer;
import com.silentevermore.rotp_whitesnake.init.InitEntities;
import com.silentevermore.rotp_whitesnake.init.InitStands;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(modid = RotpWhitesnakeAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(
                InitStands.WHITESNAKE.getEntityType(), WhitesnakeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.WS_DISC.get(), DiscRenderer::new);
    }
}
