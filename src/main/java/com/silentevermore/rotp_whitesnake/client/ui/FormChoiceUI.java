package com.silentevermore.rotp_whitesnake.client.ui;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.client.InputHandler;
import com.github.standobyte.jojo.client.ui.screen.widgets.HeightScaledSlider;
import com.github.standobyte.jojo.client.ui.screen.widgets.ImageVanillaButton;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import com.silentevermore.rotp_whitesnake.init.InitEntities;
import com.silentevermore.rotp_whitesnake.init.InitStands;
import com.silentevermore.rotp_whitesnake.network.PacketHandler;
import com.silentevermore.rotp_whitesnake.network.packets.server.WhitesnakeRenderPacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@OnlyIn(Dist.CLIENT)
public class FormChoiceUI extends Screen{
    //blit(matrix_stack, pos_x, pos_y, offset_x, offset_y, width, height)
    //constants
    private final int BUTTON_WIDTH=24;
    private final int BUTTON_HEIGHT=24;
    public static final ResourceLocation FMCUI_BG_LOCATION = new ResourceLocation(RotpWhitesnakeAddon.MOD_ID, "textures/gui/fmcui_bg.png");
    //static methods
    public static void openUI(Minecraft mc){
        if (mc!=null && mc.screen==null){
            mc.setScreen(new FormChoiceUI(mc));
        }
    }
    //builder
    public FormChoiceUI(Minecraft mc){
        super(NarratorChatListener.NO_TITLE);
    }
    //overriden methods
    @Override
    public boolean isPauseScreen(){
        return true;
    }
    @Override
    protected void init(){
        super.init();
        final Minecraft mc=Minecraft.getInstance();
        final LinkedList<EntityType<?>> tempEntityList=RotpWhitesnakeAddon.getEntitiesForDisguise();
        //buttons
        AtomicInteger i=new AtomicInteger(60);
        AtomicInteger j=new AtomicInteger(48);
        AtomicInteger btn_count=new AtomicInteger(0);
        tempEntityList.forEach(entityType->{
            i.set(i.get() + BUTTON_WIDTH + 1);
            if (i.get()>=BUTTON_WIDTH*15 + 1){
                i.set(60);
                j.set(j.get() + BUTTON_HEIGHT + 1);
            }
            FormButton btn=new FormButton(
                    entityType, i.get(), j.get(), BUTTON_WIDTH, BUTTON_HEIGHT,button -> {
                //should work, i guess..
                PlayerEntity clientPlayer=ClientUtil.getClientPlayer();
                IStandPower.getStandPowerOptional(clientPlayer).ifPresent(stand_power->{
                    if (stand_power.getStandManifestation() instanceof WhitesnakeEntity){
                        WhitesnakeEntity wt=(WhitesnakeEntity) stand_power.getStandManifestation();
                        PacketHandler.sendToServer(new WhitesnakeRenderPacket(entityType.getRegistryName(), wt.getId()));
                    }
                });
                mc.setScreen(null);
            });
            this.addButton(btn);
        });
    }
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks){
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
