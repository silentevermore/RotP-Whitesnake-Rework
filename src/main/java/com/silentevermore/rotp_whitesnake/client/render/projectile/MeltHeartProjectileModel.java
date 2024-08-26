package com.silentevermore.rotp_whitesnake.client.render.projectile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.silentevermore.rotp_whitesnake.entity.projectile.MeltHeartProjectile;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.math.vector.Vector3d;

public class MeltHeartProjectileModel extends EntityModel<MeltHeartProjectile> {
	private final ModelRenderer cum;
	private final ModelRenderer cube_r1;

	public MeltHeartProjectileModel() {
		texWidth = 32;
		texHeight = 32;

		cum = new ModelRenderer(this);
		cum.setPos(0.0F, 24.0F, 0.0F);
		cum.texOffs(0, 8).addBox(0.0F, -6.0F, -3.0F, 0.0F, 8.0F, 6.0F, 0.0F, false);

		cube_r1 = new ModelRenderer(this);
		cube_r1.setPos(0.0F, -3.0F, 0.0F);
		cum.addChild(cube_r1);
		setRotationAngle(cube_r1, 0.0F, -1.5708F, 0.0F);
		cube_r1.texOffs(0, 8).addBox(0.0F, -3.0F, -3.0F, 0.0F, 8.0F, 6.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(MeltHeartProjectile entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		final Vector3d movement=entity.getDeltaMovement();

		cum.xRot=(float)(movement.y()*Math.PI);
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		cum.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}