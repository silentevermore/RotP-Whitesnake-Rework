package com.silentevermore.rotp_whitesnake.client.glint;

import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class WhitesnakeGlint extends RenderType {
    private static final ResourceLocation rotP_Whitesnake_Rework$sosalTexture = new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "textures/glint.png");

    private WhitesnakeGlint() {
        super(null, null, 0, 0, false, false, null, null);
    }
    public static IVertexBuilder getFoilBufferDirect(IRenderTypeBuffer pBuffer, RenderType pRenderType) {
        return VertexBuilderUtils.create(pBuffer.getBuffer(wsGlintDirect()), pBuffer.getBuffer(pRenderType));
    }

    private static final RenderType WS_GLINT_DIRECT = RenderType.create("rotp_ws_glint_direct", DefaultVertexFormats.POSITION_TEX, 7, 256,
            RenderType.State.builder()
                    .setTextureState(new RenderState.TextureState(rotP_Whitesnake_Rework$sosalTexture, true, false))
                    .setWriteMaskState(COLOR_WRITE)
                    .setCullState(NO_CULL)
                    .setDepthTestState(EQUAL_DEPTH_TEST)
                    .setTransparencyState(GLINT_TRANSPARENCY)
                    .setTexturingState(GLINT_TEXTURING)
                    .createCompositeState(false));

    public static RenderType wsGlintDirect() {
        return WS_GLINT_DIRECT;
    }
}
