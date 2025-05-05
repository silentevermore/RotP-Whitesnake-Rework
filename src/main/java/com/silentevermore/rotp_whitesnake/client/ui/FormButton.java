package com.silentevermore.rotp_whitesnake.client.ui;

import com.github.standobyte.jojo.client.ui.BlitFloat;
import com.silentevermore.rotp_whitesnake.client.ui.EntityTypeIcon;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class FormButton extends Button{
    //constants
    public final EntityType<?> entityType;
    public final FormChoiceUI mainScreen;
    public static final ResourceLocation FMCUI_BG_LOCATION = new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "textures/gui/fmcui_bg.png");
    //constructor
    public FormButton(EntityType<?> entityType, FormChoiceUI screen, int x, int y, int w, int h, IPressable p_i232255_6_) {
        super(x, y, w, h, new TranslationTextComponent("rotp_whitesnake.memory_disc.player_name", entityType.getDescription().getString()), p_i232255_6_);
        this.entityType=entityType;
        this.mainScreen=screen;
    }
    //methods
    @Override
    public void renderButton(MatrixStack matrixStack, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        Minecraft minecraft = Minecraft.getInstance();
        FontRenderer fontrenderer = minecraft.font;
        minecraft.getTextureManager().bind(FMCUI_BG_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.isHovered() ? 1 : 0;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        int j = this.getFGColor();
        blit(matrixStack, this.x, this.y,0, 0, this.width, this.height, 128, 128);
        //icon render
        ResourceLocation entity_icon=EntityTypeIcon.getIcon(entityType);
        if (entity_icon!=EntityTypeIcon.UNKNOWN){
            Minecraft.getInstance().getTextureManager().bind(entity_icon);
            BlitFloat.blitFloat(matrixStack, x + 4, y + 4, 0.0F, 0.0F, 16f, 16f, 16.0F, 16.0F);
        }
        if (this.isHovered()){
            //render outline of the button
            minecraft.getTextureManager().bind(FMCUI_BG_LOCATION);
            blit(matrixStack, this.x, this.y,24, 0, this.width, this.height, 128, 128);
            if (!mainScreen.getHoveredButton().isPresent())
                mainScreen.setHoveredButton(this);
        }else{
            mainScreen.getHoveredButton().ifPresent(btn->{
                if (btn.equals(this))
                    mainScreen.setHoveredButton(null);
            });
        }
    }
}
