package com.silentevermore.rotp_whitesnake.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.layer.StandModelLayerRenderer;
import com.github.standobyte.jojo.client.render.rendertype.CustomRenderType;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.util.ResourceLocation;

public class GlintLayer<T extends WhitesnakeEntity, M extends StandEntityModel<T>> extends StandModelLayerRenderer<T, M> {
    public GlintLayer(IEntityRenderer entityRenderer, M model, ResourceLocation texture) {
        super(entityRenderer, model, texture);
    }
    private static final ResourceLocation GLINT_TEXTURE = new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "textures/glint.png");
    public RenderType getRenderType(T entity) {
        return CustomRenderType.hamonProjectileShield(GLINT_TEXTURE);
    }
}
