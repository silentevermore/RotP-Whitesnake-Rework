package com.silentevermore.rotp_whitesnake.client.render.projectile;

import com.github.standobyte.jojo.util.general.MathUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.silentevermore.rotp_whitesnake.entity.projectile.DiscProjectile;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class DiscModel extends EntityModel<DiscProjectile> {
    private final ModelRenderer disc;
    private final ModelRenderer cube_r1;

    public DiscModel() {
        texWidth = 64;
        texHeight = 64;

        disc = new ModelRenderer(this);
        disc.setPos(0.0F, -1.0F, 0.0F);
        disc.texOffs(0, 3).addBox(-3.0F, -1.5F, 0.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
        disc.texOffs(0, 0).addBox(-9.0F, -1.0F, -7.0F, 16.0F, 0.0F, 16.0F, 0.0F, false);

        cube_r1 = new ModelRenderer(this);
        cube_r1.setPos(-1.0F, -1.5F, 1.0F);
        disc.addChild(cube_r1);
        setRotationAngle(cube_r1, 0.0F, -1.5708F, 0.0F);
        cube_r1.texOffs(0, 6).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
    }

    @Override
    public void setupAnim(DiscProjectile entity, float walkAnimPos, float walkAnimSpeed, float ticks, float yRotationOffset, float xRotation) {
        disc.xRot = xRotation * MathUtil.DEG_TO_RAD;
        disc.yRot = yRotationOffset * MathUtil.DEG_TO_RAD;
    }

    @Override
    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        disc.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}