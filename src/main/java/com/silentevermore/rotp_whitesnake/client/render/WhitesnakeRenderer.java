package com.silentevermore.rotp_whitesnake.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class WhitesnakeRenderer extends StandEntityRenderer<WhitesnakeEntity, StandEntityModel<WhitesnakeEntity>> {
    public WhitesnakeRenderer(EntityRendererManager renderManager) {
        super(renderManager,
                StandModelRegistry.registerModel(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake"), WhitesnakeModel::new),
                new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "textures/entity/stand/whitesnake.png"), 0);
    }
}
