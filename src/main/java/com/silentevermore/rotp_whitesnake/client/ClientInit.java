package com.silentevermore.rotp_whitesnake.client;

import com.github.standobyte.jojo.client.ClientEventHandler;
import com.github.standobyte.jojo.client.playeranim.PlayerAnimationHandler;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.client.render.WhitesnakeRenderer;

import com.silentevermore.rotp_whitesnake.client.render.entity.layerrenderer.MeltHeartLayer;
import com.silentevermore.rotp_whitesnake.client.render.projectile.DiscRenderer;
import com.silentevermore.rotp_whitesnake.client.render.projectile.MeltHeartProjectileRenderer;
import com.silentevermore.rotp_whitesnake.init.InitEntities;
import com.silentevermore.rotp_whitesnake.init.InitStands;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.util.Map;

@EventBusSubscriber(modid = RotpWhitesnakeAddon.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientInit {
    @SubscribeEvent
    public static void onFMLClientSetup(FMLClientSetupEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(
                InitStands.WHITESNAKE.getEntityType(), WhitesnakeRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.WS_DISC.get(), DiscRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(InitEntities.WS_MHPROJ.get(), MeltHeartProjectileRenderer::new);

        event.enqueueWork(() -> {
            Minecraft mc = event.getMinecraftSupplier().get();

            ClientEventHandler.init(mc);

            Map<String, PlayerRenderer> skinMap = mc.getEntityRenderDispatcher().getSkinMap();
            addLayers(skinMap.get("default"), false);
            addLayers(skinMap.get("slim"), true);
            mc.getEntityRenderDispatcher().renderers.values().forEach(ClientInit::addLayersToEntities);

            PlayerAnimationHandler.initAnimator();
        });
    }

    private static void addLayers(PlayerRenderer renderer, boolean slim) {
        addLivingLayers(renderer);
        addBipedLayers(renderer);
    }

    private static <T extends LivingEntity, M extends BipedModel<T>> void addLayersToEntities(EntityRenderer<?> renderer) {
        if (renderer instanceof LivingRenderer<?, ?>) {
            addLivingLayers((LivingRenderer<T, ?>) renderer);
            if (((LivingRenderer<?, ?>) renderer).getModel() instanceof BipedModel<?>) {
                addBipedLayers((LivingRenderer<T, M>) renderer);
            }
        }
    }

    private static <T extends LivingEntity, M extends EntityModel<T>> void addLivingLayers(LivingRenderer<T, M> renderer) {
        renderer.addLayer(new MeltHeartLayer<>(renderer));
    }

    private static <T extends LivingEntity, M extends BipedModel<T>> void addBipedLayers(LivingRenderer<T, M> renderer) {
    }
}
