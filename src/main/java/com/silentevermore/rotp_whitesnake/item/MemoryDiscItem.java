package com.silentevermore.rotp_whitesnake.item;

import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.power.impl.stand.StandUtil;
import com.silentevermore.rotp_whitesnake.RotpWhitesnakeAddon;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class MemoryDiscItem extends Item{
    //constants
    public static final String PLAYER_DATA_KEY="PLAYER_DATA";
    public static boolean TESTIFICATE=false;
    //builder
    public MemoryDiscItem(Properties properties) {
        super(properties);
    }
    //methods
    public static ItemStack getDiscInHand(PlayerEntity player){
        ItemStack item=player.getItemInHand(Hand.MAIN_HAND);
        if (!validMemoryDisc(item)) item=player.getItemInHand(Hand.OFF_HAND);
        return item;
    }

    public static boolean validMemoryDisc(ItemStack stack){
        return (!stack.isEmpty() && stack.getTagElement(PLAYER_DATA_KEY)!=null);
    }

    public static boolean discBelongsTo(ItemStack stack, LivingEntity target){
        final CompoundNBT nbt=stack.getTagElement(PLAYER_DATA_KEY);
        return (validMemoryDisc(stack) && nbt!=null && StandUtil.getStandUser(target).getUUID().toString().equals(nbt.getString("PLAYER_ID")));
    }

    public static boolean playerHasDisc(PlayerEntity player, LivingEntity target){
        int temp_count=0;
        for (ItemStack stack:player.inventory.items){
            if (discBelongsTo(stack,target)) temp_count++;
        }
        return (temp_count>0);
    }

    public static CompoundNBT getOrCreateTargetNBT(ItemStack stack, LivingEntity target){
        final CompoundNBT disc_nbt=stack.getOrCreateTagElement(PLAYER_DATA_KEY);
        disc_nbt.putString("PLAYER_ID",StandUtil.getStandUser(target).getUUID().toString());
        disc_nbt.putString("PLAYER",StandUtil.getStandUser(target).getDisplayName().getString());
        return disc_nbt;
    }

    public static String getTargetName(ItemStack stack, LivingEntity target){
        return getOrCreateTargetNBT(stack,target).getString("PLAYER");
    }

    public static String getTargetId(ItemStack stack, LivingEntity target){
        return getOrCreateTargetNBT(stack,target).getString("PLAYER_ID");
    }

    public static ItemStack withTargetNbt(ItemStack stack, LivingEntity target){
        getOrCreateTargetNBT(stack,target);
        return stack;
    }
    //overriden from Item's methods
    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand){
        super.use(world,player,hand);
        if (TESTIFICATE){
            player.getPersistentData().putBoolean("MEMORY_DISK_AFFECTED",!player.getPersistentData().getBoolean("MEMORY_DISK_AFFECTED"));
            player.displayClientMessage(new TranslationTextComponent("rotp_whitesnake.memory_disc.player_name",player.getPersistentData().getBoolean("MEMORY_DISK_AFFECTED")),true);
            IStandPower.getStandPowerOptional(player).ifPresent(stand_power->{
                if (stand_power.getStandManifestation() instanceof StandEntity){
                    final StandEntity stand_manifest=(StandEntity) stand_power.getStandManifestation();
                    stand_manifest.getPersistentData().putBoolean("MEMORY_DISK_AFFECTED",player.getPersistentData().getBoolean("MEMORY_DISK_AFFECTED"));
                }
            });
        }
        return ActionResult.fail(player.getItemInHand(hand));
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int p_77663_4_, boolean p_77663_5_){
        if (!world.isClientSide() && validMemoryDisc(stack)){
            final ServerWorld serverWorld=((ServerWorld) world);
            final CompoundNBT nbt=stack.getOrCreateTagElement(PLAYER_DATA_KEY);
            if (!nbt.getString("PLAYER_ID").isEmpty()){
                final Entity target=serverWorld.getEntity(UUID.fromString(nbt.getString("PLAYER_ID")));
                if (target==null || target instanceof LivingEntity && !target.isAlive()){
                    stack.shrink(stack.getCount());
                }
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        final CompoundNBT data=stack.getOrCreateTagElement(PLAYER_DATA_KEY);
        final String player=data.getString("PLAYER");
        if (!player.isEmpty()){
            tooltip.add(new TranslationTextComponent("rotp_whitesnake.memory_disc.current_player")
                    .withStyle(TextFormatting.DARK_PURPLE)
                    .append(new TranslationTextComponent("rotp_whitesnake.memory_disc.player_name", player)
                            .withStyle(TextFormatting.GOLD)
                    ));
        }else{
            tooltip.add(new TranslationTextComponent("rotp_whitesnake.memory_disc.no_current_player")
                    .withStyle(TextFormatting.DARK_PURPLE));
        }
    }
}
