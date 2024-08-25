package com.silentevermore.rotp_whitesnake.client.render.projectile;

import com.github.standobyte.jojo.util.general.MathUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.silentevermore.rotp_whitesnake.entity.projectile.MeltHeartProjectile;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

public class MeltHeartProjectileModel extends EntityModel<MeltHeartProjectile> {
    private final ModelRenderer disc;
    private final ModelRenderer cube_r1;

    public MeltHeartProjectileModel() {
        this.texWidth = 64;
        this.texHeight = 64;
        this.disc = new ModelRenderer(this);
        this.disc.setPos(0.0F, 24.0F, 0.0F);
        this.disc.texOffs(0, 3).addBox(-3.0F, -1.5F, 0.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
        this.disc.texOffs(0, 0).addBox(-9.0F, -1.0F, -7.0F, 16.0F, 0.0F, 16.0F, 0.0F, false);
        this.cube_r1 = new ModelRenderer(this);
        this.cube_r1.setPos(-1.0F, -1.5F, 1.0F);
        this.disc.addChild(this.cube_r1);
        this.setRotationAngle(this.cube_r1, 0.0F, -1.5708F, 0.0F);
        this.cube_r1.texOffs(0, 6).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 1.0F, 2.0F, 0.0F, false);
    }

    public void setupAnim(MeltHeartProjectile entity, float walkAnimPos, float walkAnimSpeed, float ticks, float yRotationOffset, float xRotation) {
        this.disc.yRot = yRotationOffset * MathUtil.DEG_TO_RAD;
    }

    public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        this.disc.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.xRot = x;
        modelRenderer.yRot = y;
        modelRenderer.zRot = z;
    }
}