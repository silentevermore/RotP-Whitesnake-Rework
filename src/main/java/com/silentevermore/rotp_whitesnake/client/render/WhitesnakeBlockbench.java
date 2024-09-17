package com.silentevermore.rotp_whitesnake.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class WhitesnakeBlockbench extends EntityModel<Entity> {
	private final ModelRenderer head;
	private final ModelRenderer body;
	private final ModelRenderer upperPart;
	private final ModelRenderer torso;
	private final ModelRenderer leftArm;
	private final ModelRenderer leftArmJoint;
	private final ModelRenderer leftForeArm;
	private final ModelRenderer leftKnuckledusters;
	private final ModelRenderer rightArm;
	private final ModelRenderer rightArmJoint;
	private final ModelRenderer rightForeArm;
	private final ModelRenderer rightKnuckledusters;
	private final ModelRenderer leftLeg;
	private final ModelRenderer leftLegJoint;
	private final ModelRenderer leftLowerLeg;
	private final ModelRenderer rightLeg;
	private final ModelRenderer rightLowerLeg;
	private final ModelRenderer rightLegJoint;

	public WhitesnakeBlockbench() {
		texWidth = 128;
		texHeight = 128;

		head = new ModelRenderer(this);
		head.setPos(0.0F, 0.0F, 0.0F);
		head.texOffs(0, 19).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);
		head.texOffs(0, 0).addBox(-4.0F, -11.0F, -4.0F, 8.0F, 11.0F, 8.0F, 0.25F, false);

		body = new ModelRenderer(this);
		body.setPos(0.0F, 0.0F, 0.0F);
		

		upperPart = new ModelRenderer(this);
		upperPart.setPos(0.0F, 12.0F, 0.0F);
		body.addChild(upperPart);
		

		torso = new ModelRenderer(this);
		torso.setPos(0.0F, -12.0F, 0.0F);
		upperPart.addChild(torso);
		torso.texOffs(32, 0).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);
		torso.texOffs(28, 31).addBox(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.25F, false);

		leftArm = new ModelRenderer(this);
		leftArm.setPos(6.0F, -10.0F, 0.0F);
		upperPart.addChild(leftArm);
		leftArm.texOffs(56, 10).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		leftArm.texOffs(24, 47).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);

		leftArmJoint = new ModelRenderer(this);
		leftArmJoint.setPos(-0.5F, 4.0F, 0.0F);
		leftArm.addChild(leftArmJoint);
		leftArmJoint.texOffs(60, 20).addBox(-1.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		leftForeArm = new ModelRenderer(this);
		leftForeArm.setPos(-0.5F, 4.0F, 0.0F);
		leftArm.addChild(leftForeArm);
		leftForeArm.texOffs(28, 57).addBox(-1.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		leftForeArm.texOffs(0, 47).addBox(-1.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);

		leftKnuckledusters = new ModelRenderer(this);
		leftKnuckledusters.setPos(2.25F, 5.75F, 0.0F);
		leftForeArm.addChild(leftKnuckledusters);
		leftKnuckledusters.texOffs(3, 5).addBox(-0.5F, -0.5F, -1.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftKnuckledusters.texOffs(0, 4).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		leftKnuckledusters.texOffs(3, 3).addBox(-0.5F, -0.5F, 0.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		rightArm = new ModelRenderer(this);
		rightArm.setPos(-6.0F, -10.0F, 0.0F);
		upperPart.addChild(rightArm);
		rightArm.texOffs(0, 35).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		rightArm.texOffs(28, 16).addBox(-2.0F, -2.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);

		rightArmJoint = new ModelRenderer(this);
		rightArmJoint.setPos(0.5F, 4.0F, 0.0F);
		rightArm.addChild(rightArmJoint);
		rightArmJoint.texOffs(16, 35).addBox(-2.0F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		rightForeArm = new ModelRenderer(this);
		rightForeArm.setPos(0.5F, 4.0F, 0.0F);
		rightArm.addChild(rightForeArm);
		rightForeArm.texOffs(44, 16).addBox(-2.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		rightForeArm.texOffs(12, 41).addBox(-2.5F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);

		rightKnuckledusters = new ModelRenderer(this);
		rightKnuckledusters.setPos(-2.25F, 5.75F, 0.0F);
		rightForeArm.addChild(rightKnuckledusters);
		rightKnuckledusters.texOffs(3, 1).addBox(-0.5F, -0.5F, -1.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rightKnuckledusters.texOffs(0, 2).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, 0.0F, false);
		rightKnuckledusters.texOffs(0, 0).addBox(-0.5F, -0.5F, 0.75F, 1.0F, 1.0F, 1.0F, 0.0F, false);

		leftLeg = new ModelRenderer(this);
		leftLeg.setPos(2.0F, 12.0F, 0.0F);
		body.addChild(leftLeg);
		leftLeg.texOffs(16, 63).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		leftLeg.texOffs(56, 0).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);

		leftLegJoint = new ModelRenderer(this);
		leftLegJoint.setPos(0.0F, 6.0F, 0.0F);
		leftLeg.addChild(leftLegJoint);
		leftLegJoint.texOffs(44, 63).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);

		leftLowerLeg = new ModelRenderer(this);
		leftLowerLeg.setPos(0.0F, 6.0F, 0.0F);
		leftLeg.addChild(leftLowerLeg);
		leftLowerLeg.texOffs(0, 59).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		leftLowerLeg.texOffs(52, 53).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);

		rightLeg = new ModelRenderer(this);
		rightLeg.setPos(-2.0F, 12.0F, 0.0F);
		body.addChild(rightLeg);
		rightLeg.texOffs(52, 26).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);
		rightLeg.texOffs(40, 47).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);

		rightLowerLeg = new ModelRenderer(this);
		rightLowerLeg.setPos(0.0F, 6.0F, 0.0F);
		rightLeg.addChild(rightLowerLeg);
		rightLowerLeg.texOffs(12, 53).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.25F, false);
		rightLowerLeg.texOffs(52, 36).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F, 0.0F, false);

		rightLegJoint = new ModelRenderer(this);
		rightLegJoint.setPos(0.0F, 6.0F, 0.0F);
		rightLeg.addChild(rightLegJoint);
		rightLegJoint.texOffs(56, 46).addBox(-1.5F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, 0.0F, false);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		//previously the render function, render code was moved to a method below
	}

	@Override
	public void renderToBuffer(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		head.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
		body.render(matrixStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.xRot = x;
		modelRenderer.yRot = y;
		modelRenderer.zRot = z;
	}
}