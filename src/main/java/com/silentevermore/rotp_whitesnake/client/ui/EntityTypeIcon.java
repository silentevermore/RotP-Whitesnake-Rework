package com.silentevermore.rotp_whitesnake.client.ui;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.client.ui.BlitFloat;
import com.github.standobyte.jojo.util.mc.EntityTypeToInstance;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class EntityTypeIcon{
    private static final Map<EntityType<?>, ResourceLocation> ICONS_CACHE = new HashMap();
    public static final ResourceLocation UNKNOWN = new ResourceLocation("textures/entity_icon/unknown.png");

    public EntityTypeIcon() {
    }

    public static void renderIcon(EntityType<?> entityType, MatrixStack matrixStack, float x, float y) {
        renderIcon(entityType, matrixStack, x, y, true);
    }

    public static void renderIcon(EntityType<?> entityType, MatrixStack matrixStack, float x, float y, boolean missingIconLetters) {
        ResourceLocation icon = getIcon(entityType);
        if (icon != UNKNOWN) {
            Minecraft.getInstance().getTextureManager().bind(icon);
            BlitFloat.blitFloat(matrixStack, x, y, 0.0F, 0.0F, 16.0F, 16.0F, 16.0F, 16.0F);
        } else if (missingIconLetters) {
            String name = entityType.getDescription().getString();
            if (!name.isEmpty()) {
                FontRenderer font = Minecraft.getInstance().font;
                ITextComponent firstLetter = StringTextComponent.EMPTY;
                int widthNext = 0;

                for(int i = 1; i <= name.length() && widthNext < 12; ++i) {
                    firstLetter = new StringTextComponent(name.substring(0, i));
                    widthNext = font.width(firstLetter);
                }

                RenderSystem.disableDepthTest();
                float var10003 = x + (float)((16 - widthNext) / 2);
                font.getClass();
                font.draw(matrixStack, firstLetter, var10003, y + (float)((16 - 9 + 1) / 2), 16777215);
                RenderSystem.enableDepthTest();
                RenderSystem.enableBlend();
            }
        }

    }

    public static ResourceLocation getIcon(EntityType<?> entityType) {
        return (ResourceLocation)ICONS_CACHE.computeIfAbsent(entityType, EntityTypeIcon::createIconPath);
    }

    private static ResourceLocation createIconPath(EntityType<?> entityType) {
        Minecraft mc = Minecraft.getInstance();
        ResourceLocation entityTex = getEntityTexture(entityType);
        if (entityTex == null) {
            return UNKNOWN;
        } else {
            String path = entityTex.getPath();
            if (path.contains("/entity/")) {
                if (path.contains("/model/entity/")) {
                    path = path.replace("/model/entity/", "/entity_icon/");
                } else {
                    path = path.replace("/entity/", "/entity_icon/");
                }

                entityTex = new ResourceLocation(entityTex.getNamespace(), path);
                if (mc.getResourceManager().hasResource(entityTex)) {
                    return entityTex;
                }
            }

            return UNKNOWN;
        }
    }

    @Nullable
    private static <T extends Entity> ResourceLocation getEntityTexture(EntityType<T> entityType) {
        Minecraft mc = Minecraft.getInstance();
        EntityRenderer<? super T> renderer = (EntityRenderer)mc.getEntityRenderDispatcher().renderers.get(entityType);
        T entity = EntityTypeToInstance.getEntityInstance(entityType, mc.level);

        try {
            return renderer.getTextureLocation(entity);
        } catch (Exception e) {
            JojoMod.getLogger().error(e);
            return null;
        }
    }

    public static void onResourceReload() {
        ICONS_CACHE.clear();
    }
}
