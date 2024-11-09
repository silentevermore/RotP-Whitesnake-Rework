package com.silentevermore.rotp_whitesnake.client.ui;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.InputHandler;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import com.silentevermore.rotp_whitesnake.init.InitEntities;
import com.silentevermore.rotp_whitesnake.init.InitStands;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class FormChoiceUI extends Screen{
    //constants
    private final ResourceLocation MENU_BG=new ResourceLocation("/textures/gui/fmcui_bg.png");
    private Optional<Entity> currentlyHovered=Optional.empty();
    private final ArrayList<Entity> totalEntities=new ArrayList<>();
    private final ArrayList<FormChoiceUI.SelectorWidget> slots=new ArrayList<>();
    //builder
    public FormChoiceUI(Minecraft mc){
        super(NarratorChatListener.NO_TITLE);
    }
    //methods
    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers){
        final InputHandler.MouseButton button = InputHandler.MouseButton.getButtonFromId(pKeyCode);
        if (button==InputHandler.MouseButton.LEFT){
            return true;
        }
        return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }
    @Override
    protected void init(){
        super.init();
    }
    @Override
    public void render(MatrixStack matrixStack, int x, int y, float var1){
        final Minecraft minecraft=this.getMinecraft();
        if (minecraft!=null){
            matrixStack.pushPose();
            RenderSystem.enableBlend();
            minecraft.getTextureManager().bind(MENU_BG);
            matrixStack.popPose();
            super.render(matrixStack, x, y, var1);
            this.currentlyHovered.ifPresent(
                    (something)->drawCenteredString(matrixStack, this.font, something.getDisplayName(), this.width/2, this.height/2 - 30 - 20, -1));
        }
        for (FormChoiceUI.SelectorWidget selectorWidget : this.slots){
            selectorWidget.render(matrixStack, x, y, var1);
        }
    }
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int buttonId){
        if (super.mouseClicked(mouseX, mouseY, buttonId))
            return true;
        final InputHandler.MouseButton button=InputHandler.MouseButton.getButtonFromId(buttonId);
        if (button== InputHandler.MouseButton.LEFT)
            switchToHovered(this.getMinecraft(), this.currentlyHovered);
        return false;
    }
    private void switchToHovered(Minecraft minecraft, Optional<Entity> hovered){
        hovered.ifPresent(
                (entity)->{
                   final RayTraceResult target=InputHandler.getInstance().mouseTarget;
                   final ActionTarget actionTarget=ActionTarget.fromRayTraceResult(target);
                   final PlayerEntity player=ClientUtil.getClientPlayer();
                   minecraft.setScreen(null);
                });
    }
    //classes
    public class SelectorWidget extends Widget{
        private Optional<Entity> selected=Optional.empty();
        public SelectorWidget(int x, int y){
            super(x, y, 25, 25, new TranslationTextComponent(""));
        }
        public void setSelected(Entity selected){
            this.selected=Optional.of(selected);
        }
        public boolean selectionEquals(Entity target){
            return this.selected.map(
                    (entity)->{return target.equals(entity);}).orElse(false);
        }
        public void renderButton(MatrixStack matrixStack, int x, int y, float var1){
            final Minecraft minecraft=Minecraft.getInstance();
            this.drawSlot(matrixStack, minecraft.getTextureManager());
        }
        private void drawSlot(MatrixStack matrixStack, TextureManager textureManager){
            textureManager.bind(MENU_BG);
            matrixStack.pushPose();
            matrixStack.translate(this.x, this.y, 0d);
            blit(matrixStack, 0, 0, 0f, 0f, 25, 25, 128, 128);
            matrixStack.popPose();
        }
        private void drawSelection(MatrixStack matrixStack, TextureManager textureManager){
            textureManager.bind(MENU_BG);
            matrixStack.pushPose();
            matrixStack.translate(this.x, this.y, 0d);
            blit(matrixStack, 0, 0, 100f, 0f, 25, 25, 128, 128);
            matrixStack.popPose();
        }
    }
}
