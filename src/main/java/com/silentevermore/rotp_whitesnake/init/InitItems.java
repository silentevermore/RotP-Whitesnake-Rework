package com.silentevermore.rotp_whitesnake.init;

import com.github.standobyte.jojo.JojoMod;
import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.init.power.JojoCustomRegistries;
import com.github.standobyte.jojo.util.mod.JojoModUtil;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import com.silentevermore.rotp_whitesnake.block.MeltHeartBlock;
import com.silentevermore.rotp_whitesnake.item.MemoryDiscItem;
import net.minecraft.block.Block;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Rarity;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class InitItems {
    //constants
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RotpWhitesnakeAddon.MOD_ID);

    public static final RegistryObject<MemoryDiscItem> MEMORY_DISC_ITEM = ITEMS.register("memory_disc_item",
            () -> new MemoryDiscItem(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .stacksTo(1)
                    .tab(JojoMod.MAIN_TAB)
            )
    );
}
