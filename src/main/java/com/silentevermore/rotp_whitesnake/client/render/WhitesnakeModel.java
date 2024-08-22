package com.silentevermore.rotp_whitesnake.client.render;

import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.client.render.entity.model.stand.HumanoidStandModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.bb.BlockbenchStandModelHelper;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPose;
import com.github.standobyte.jojo.client.render.entity.pose.ModelPoseTransitionMultiple;
import com.github.standobyte.jojo.client.render.entity.pose.RotationAngle;
import com.github.standobyte.jojo.client.render.entity.pose.anim.PosedActionAnimation;
import com.github.standobyte.jojo.entity.stand.StandPose;

import javax.annotation.Nullable;

public class WhitesnakeModel extends HumanoidStandModel<WhitesnakeEntity>{
	//constants
	public static final StandPose REMOVE_DISC=new StandPose("WHITESNAKE_REMOVE_STAND_DISC");
	//builder
	public WhitesnakeModel(){
		super();
		BlockbenchStandModelHelper.fillFromBlockbenchExport(new WhitesnakeBlockbench(),this);
	}
	//methods
	@Override
	public void prepareMobModel(@Nullable WhitesnakeEntity entity, float walkAnimPos, float walkAnimSpeed, float partialTick) {
		super.prepareMobModel(entity, walkAnimPos, walkAnimSpeed, partialTick);
	}
	@Override
	protected RotationAngle[][] initSummonPoseRotations(){
		return new RotationAngle[][] {
				new RotationAngle[] {
						RotationAngle.fromDegrees(head, 10, -5, -5),
						RotationAngle.fromDegrees(body, 42.5, 0, 0),
						RotationAngle.fromDegrees(upperPart, 0, 0, 0),
						RotationAngle.fromDegrees(leftArm, -44.03249, 15.43388, -17.87164),
						RotationAngle.fromDegrees(leftForeArm, -10, 0, 0),
						RotationAngle.fromDegrees(rightArm, -40.45668, -40.95475, 50.18841),
						RotationAngle.fromDegrees(rightForeArm, -61.02177, 21.76063, -52.99488),
						RotationAngle.fromDegrees(leftLeg, -53.6138, -17.43362, 3.43655),
						RotationAngle.fromDegrees(leftLowerLeg, 32.5, 0, 0),
						RotationAngle.fromDegrees(rightLeg, -96.88938, -4.76887, 4.79739),
						RotationAngle.fromDegrees(rightLowerLeg, 75, 0, 0)
				}
		};
	}
	@Override
	protected void initActionPoses() {
		actionAnim.put(StandPose.RANGED_ATTACK, new PosedActionAnimation.Builder<WhitesnakeEntity>()
				.addPose(StandEntityAction.Phase.BUTTON_HOLD, new ModelPose<>(new RotationAngle[] {
						new RotationAngle(body, 0.0F, -0.48F, 0.0F),
						new RotationAngle(leftArm, 0.0F, 0.0F, 0.0F),
						new RotationAngle(leftForeArm, 0.0F, 0.0F, 0.0F),
						new RotationAngle(rightArm, -1.0908F, 0.0F, 1.5708F),
						new RotationAngle(rightForeArm, 0.0F, 0.0F, 0.0F)
				}))
				.build(idlePose));
		ModelPose<WhitesnakeEntity> handPose1 = new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(head, 7.07745, 17.67052, -0.24255),
				RotationAngle.fromDegrees(body, -5.36618, 44.85374, 2.17107),
				RotationAngle.fromDegrees(leftArm, -68.76766, 46.68301, -58.12778),
				RotationAngle.fromDegrees(leftArmJoint, -28.26919, -3.12516, 10.12488),
				RotationAngle.fromDegrees(leftForeArm, -51.40133, -1.40325, 17.91077),
				RotationAngle.fromDegrees(rightArm, -40.65653, -55.87912, 39.57735),
				RotationAngle.fromDegrees(rightArmJoint, -35, 0, 0),
				RotationAngle.fromDegrees(rightForeArm, -59.11573, -8.14866, -32.88084),
				RotationAngle.fromDegrees(leftLeg, -22.65707, 4.36733, -21.87297),
				RotationAngle.fromDegrees(leftLegJoint, 22.5, 0, 0),
				RotationAngle.fromDegrees(leftLowerLeg, 65, 0F, 0F),
				RotationAngle.fromDegrees(rightLeg, -4.82651, 19.91262, 0.09026),
				RotationAngle.fromDegrees(rightLegJoint, 30, 0, 0),
				RotationAngle.fromDegrees(rightLowerLeg, 47.5, 0, 0)
		});
		ModelPose<WhitesnakeEntity> handPose2 = new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(body, 23.42006, 87.28165, 30.44076),
				RotationAngle.fromDegrees(leftArm, -95.81017, 30.10619, -65.35952),
				RotationAngle.fromDegrees(leftForeArm, -39.67255, 7.8716, 9.79518),
				RotationAngle.fromDegrees(rightArm, -65.65653, -55.87912, 39.57735),
				RotationAngle.fromDegrees(rightForeArm, -59.11573, -8.14866, -32.88084),
				RotationAngle.fromDegrees(leftLeg, -40.15707, 4.36733, -21.87297),
				RotationAngle.fromDegrees(leftLegJoint, 32.5, 0, 0),
				RotationAngle.fromDegrees(leftLowerLeg, 72.5, 0, 0),
				RotationAngle.fromDegrees(rightLeg, 0.17349, 19.91262, 0.09026),
				RotationAngle.fromDegrees(rightLowerLeg, 47.5, 0, 0)
		});
		ModelPose<WhitesnakeEntity> handPose3 = new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(body,5.61655, -19.24988, -6.59941),
				RotationAngle.fromDegrees(leftArm, -44.39437, -7.21253, -100.1517),
				RotationAngle.fromDegrees(leftForeArm, -18.72132, 1.733, 0.51371),
				RotationAngle.fromDegrees(rightArm, 48.41451, -4.88845, 31.92263),
				RotationAngle.fromDegrees(leftLeg, 23.22511, -29.4987, -19.00839),
				RotationAngle.fromDegrees(leftLegJoint, 10, 0, 0),
				RotationAngle.fromDegrees(leftLowerLeg, 35, 0, 0F),
				RotationAngle.fromDegrees(rightLeg, -17.94067, -9.99038, 2.53852),
				RotationAngle.fromDegrees(rightLowerLeg, 60, 0, 0)
		});
		actionAnim.put(REMOVE_DISC, new PosedActionAnimation.Builder<WhitesnakeEntity>()
				.addPose(StandEntityAction.Phase.BUTTON_HOLD, new ModelPoseTransitionMultiple.Builder<>(idlePose)
						.addPose(0.1F, handPose1)
						.addPose(0.75F, handPose2)
						.build(handPose3))
				.addPose(StandEntityAction.Phase.PERFORM, new ModelPoseTransitionMultiple.Builder<>(handPose2)
						.addPose(0.5F, handPose3)
						.build(handPose3))
				.addPose(StandEntityAction.Phase.RECOVERY, new ModelPoseTransitionMultiple.Builder<>(handPose3)
						.addPose(0.5F, handPose3)
						.build(idlePose))
				.build(idlePose));

		super.initActionPoses();
	}
	@Override
	protected ModelPose<WhitesnakeEntity> initIdlePose() {
		return new ModelPose<>(new RotationAngle[] {
				RotationAngle.fromDegrees(head, 7.07745, 17.67052, -0.24255),
				RotationAngle.fromDegrees(body, -3.89307, 12.45167, 5.12076),
				RotationAngle.fromDegrees(upperPart, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(torso, 0.0F, 0.0F, 0.0F),
				RotationAngle.fromDegrees(leftArm, -47.5983, 68.38059, -33.58298),
				RotationAngle.fromDegrees(leftArmJoint, -28.26919, -3.12516, 10.12488),
				RotationAngle.fromDegrees(leftForeArm, -51.40133, -1.40325, 17.91077),
				RotationAngle.fromDegrees(rightArm, -40.65653, -55.87912, 39.57735),
				RotationAngle.fromDegrees(rightArmJoint, -35, 0, 0),
				RotationAngle.fromDegrees(rightForeArm, -59.11573, -8.14866, -32.88084),
				RotationAngle.fromDegrees(leftLeg, 5.72511, -29.4987, -19.00839),
				RotationAngle.fromDegrees(leftLowerLeg, 15, 0, 0),
				RotationAngle.fromDegrees(rightLeg, -0.44067, -9.99038, 2.53852),
				RotationAngle.fromDegrees(rightLowerLeg, 15, 0, 0)
		});
	}
	@Override
	protected ModelPose<WhitesnakeEntity> initIdlePose2Loop() {
		return new ModelPose<>(new RotationAngle[]{
				RotationAngle.fromDegrees(leftArm, -42.5983, 68.38059, -33.58298),
				RotationAngle.fromDegrees(leftForeArm, -61.40133, -1.40325, 17.91077),
				RotationAngle.fromDegrees(rightArm, -35.65653, -55.87912, 39.57735),
				RotationAngle.fromDegrees(rightForeArm, -66.11573, -8.14866, -32.88084),
		});
	}
}  
