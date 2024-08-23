package com.silentevermore.rotp_whitesnake.client.render.projectile;

import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.projectile.DiscProjectile;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class DiscRenderer extends SimpleEntityRenderer<DiscProjectile, DiscModel> {

    public DiscRenderer(EntityRendererManager renderManager) {
        super(renderManager, new DiscModel(), new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "textures/entity/projectiles/disc.png"));
    }

    @Override
    public ResourceLocation getTextureLocation(DiscProjectile entity) {
        return StandSkinsManager.getInstance()
                .getRemappedResPath(manager -> manager.getStandSkin(entity.getStandSkin()), texPath);
    }

}
