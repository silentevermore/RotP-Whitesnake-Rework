package com.silentevermore.rotp_whitesnake.client.render;

import com.github.standobyte.jojo.client.render.entity.model.stand.StandEntityModel;
import com.github.standobyte.jojo.client.render.entity.model.stand.StandModelRegistry;
import com.github.standobyte.jojo.client.render.entity.renderer.stand.StandEntityRenderer;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.HeldItemLayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.entity.model.ZombieModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;

public class WhitesnakeRenderer extends StandEntityRenderer<WhitesnakeEntity, WhitesnakeModel>{
    public WhitesnakeRenderer(EntityRendererManager renderManager){
        super(renderManager,
                StandModelRegistry.registerModel(new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "whitesnake"), WhitesnakeModel::new),
                new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "textures/entity/stand/whitesnake.png"), 0);
    }
    //methods
    public static int getOverlayCoordsForEntity(LivingEntity entity, float p_229117_1_) {
        return OverlayTexture.pack(OverlayTexture.u(p_229117_1_), OverlayTexture.v(entity.hurtTime > 0 || entity.deathTime > 0));
    }
    @Override
    public void renderFirstPersonArms(MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight, WhitesnakeEntity entity, float partialTick){
        if (entity.getEntityForDisguise()==null)
            super.renderFirstPersonArms(matrixStack, buffer, packedLight, entity, partialTick);
    }
    @Override
    public void render(WhitesnakeEntity entity, float yRotation, float partialTick, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight){
        float ticks = entity.tickCount + partialTick;
        float tf_progress = MathHelper.clamp(entity.shapeshiftTickDifference(ticks)/20f, 0, 1);

        if (entity.getEntityForDisguise()!=null){
            final LivingEntity living=entity.getEntityForDisguise();

            final LivingRenderer<LivingEntity, EntityModel<LivingEntity>> renderer=
                    (LivingRenderer<LivingEntity, EntityModel<LivingEntity>>) entityRenderDispatcher.getRenderer(living);
            final EntityModel<LivingEntity> model=renderer.getModel();
            final ResourceLocation texture = renderer.getTextureLocation(living);

            float yHeadRotation = MathHelper.rotLerp(partialTick, entity.yHeadRotO, entity.yHeadRot);
            float yBodyRotation = MathHelper.rotLerp(partialTick, entity.yBodyRotO, entity.yBodyRot);
            float f2 = yHeadRotation - yBodyRotation;

            float xRotation = MathHelper.lerp(partialTick, entity.xRotO, entity.xRot);
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - yBodyRotation));

            float walkAnimSpeed = 0;
            float walkAnimPos = 0;
            float attackTime = 0;

            matrixStack.pushPose();
            matrixStack.scale(-1, -1, 1);
            matrixStack.scale(tf_progress, tf_progress, tf_progress);
            matrixStack.translate(0d, -1.501d, 0d);
            model.young=living.isBaby();
            model.riding=false;
            model.attackTime=attackTime;
            //animations
            model.prepareMobModel(living, walkAnimSpeed, walkAnimPos, partialTick);
            model.setupAnim(living, walkAnimSpeed, walkAnimPos, ticks, f2, xRotation);
            if (texture != null) {
                final RenderType rendertype = model.renderType(texture);
                if (rendertype != null) {
                    final IVertexBuilder ivertexbuilder = buffer.getBuffer(rendertype);
                    float prog_pow = 5f * tf_progress;
                    int overlay = getOverlayCoordsForEntity(living, 0); //getOverlayCoords(entity, 1 - prog_pow);
                    model.renderToBuffer(matrixStack, ivertexbuilder, packedLight, overlay, 1f, 1f, 1f, this.calcAlpha(entity, partialTick));
                }
            }
            matrixStack.popPose();
            MinecraftForge.EVENT_BUS.post(new RenderLivingEvent.Post<LivingEntity, EntityModel<LivingEntity>>(entity, renderer, partialTick, matrixStack, buffer, packedLight));
        }else{
            super.render(entity, yRotation, partialTick, matrixStack, buffer, packedLight);
        }
    }
}