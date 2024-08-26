package com.silentevermore.rotp_whitesnake.client.render.projectile;

import com.github.standobyte.jojo.client.render.entity.renderer.SimpleEntityRenderer;
import com.github.standobyte.jojo.client.standskin.StandSkinsManager;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.projectile.DiscProjectile;
import com.silentevermore.rotp_whitesnake.entity.projectile.MeltHeartProjectile;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;

public class MeltHeartProjectileRenderer extends SimpleEntityRenderer<MeltHeartProjectile, MeltHeartProjectileModel> {

    public MeltHeartProjectileRenderer(EntityRendererManager renderManager) {
        super(renderManager, new MeltHeartProjectileModel(), new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "textures/entity/projectiles/melt_heart.png"));
    }

    @Override
    public ResourceLocation getTextureLocation(MeltHeartProjectile entity) {
        return StandSkinsManager.getInstance()
                .getRemappedResPath(manager -> manager.getStandSkin(entity.getStandSkin()), texPath);
    }

}
