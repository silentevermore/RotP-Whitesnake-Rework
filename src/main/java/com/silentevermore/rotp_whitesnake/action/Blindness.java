package com.silentevermore.rotp_whitesnake.action;

import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.github.standobyte.jojo.util.general.LazySupplier;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Blindness extends StandEntityAction{
    //constants
    private final LazySupplier<ResourceLocation> disableTex=new LazySupplier<>(()->makeIconVariant(this,"_disable"));
    //builder
    public Blindness(StandEntityAction.Builder builder) {
        super(builder);
    }
    //methods
    @Override
    public void standPerform(World world, StandEntity standEntity, IStandPower userPower, StandEntityTask task) {
        if (!world.isClientSide()){
            LivingEntity user=userPower.getUser();
            if (!user.hasEffect(Effects.BLINDNESS)){
                user.addEffect(new EffectInstance(Effects.BLINDNESS,(int)1e5,1,false,false,false));
            }else{
                user.removeEffect(Effects.BLINDNESS);
            }
        }
    }

    @Override
    public IFormattableTextComponent getTranslatedName(IStandPower power, String key){
        if (power!=null && power.getUser().hasEffect(Effects.BLINDNESS)){
            return new TranslationTextComponent("action.rotp_whitesnake.blindness_disable");
        }
        return new TranslationTextComponent(key);
    }

    @Override
    public ResourceLocation getIconTexturePath(@Nullable IStandPower power) {
        if (power!=null && power.getUser().hasEffect(Effects.BLINDNESS)){
            return this.disableTex.get();
        }
        return super.getIconTexturePath(power);
    }
}
