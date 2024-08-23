package com.silentevermore.rotp_whitesnake.client.render.projectile;

import com.github.standobyte.jojo.entity.damaging.projectile.HGEmeraldEntity;
import com.github.standobyte.jojo.util.general.MathUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import com.silentevermore.rotp_whitesnake.entity.projectile.DiscProjectile;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

// Made with Blockbench 3.9.2


public class DiscModel extends EntityModel<DiscProjectile> {
    private final ModelRenderer disc;

    public DiscModel() {
        texWidth = 64;
        texHeight = 64;
        disc = new ModelRenderer(this);
        disc.setPos(0.0F, -1.0F, 0.0F);
        disc.texOffs(0, 0).addBox(7.0F, -1.5F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        disc.texOffs(0, 5).addBox(-7.0F, -1.5F, -2.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);
        disc.texOffs(0, 20).addBox(6.0F, -1.5F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        disc.texOffs(20, 0).addBox(-6.0F, -1.5F, -3.0F, 1.0F, 1.0F, 6.0F, 0.0F, false);
        disc.texOffs(0, 11).addBox(3.0F, -1.5F, -4.0F, 3.0F, 1.0F, 8.0F, 0.0F, false);
        disc.texOffs(14, 12).addBox(-5.0F, -1.5F, -4.0F, 3.0F, 1.0F, 8.0F, 0.0F, false);
        disc.texOffs(0, 0).addBox(-2.0F, -1.5F, -5.0F, 5.0F, 1.0F, 10.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(DiscProjectile entity, float walkAnimPos, float walkAnimSpeed, float ticks, float yRotationOffset, float xRotation) {
        if (entity.canUpdate()) {
            yRotationOffset = (yRotationOffset + ticks * 36.0F) % 360.0F;
        }
        disc.yRot = yRotationOffset * MathUtil.DEG_TO_RAD;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        disc.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
