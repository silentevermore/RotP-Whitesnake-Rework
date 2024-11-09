package com.silentevermore.rotp_whitesnake.util;

import com.silentevermore.rotp_whitesnake.client.ui.FormChoiceUI;
import net.minecraft.client.Minecraft;

public class ClientUtil{
    public static void openFormChoiceUI(Minecraft mc){
        Minecraft.getInstance().setScreen(new FormChoiceUI(mc));
    }
}
