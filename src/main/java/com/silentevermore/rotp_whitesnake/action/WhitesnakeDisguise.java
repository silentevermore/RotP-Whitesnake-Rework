package com.silentevermore.rotp_whitesnake.action;

import com.github.standobyte.jojo.action.ActionTarget;
import com.github.standobyte.jojo.action.stand.StandEntityAction;
import com.github.standobyte.jojo.entity.stand.StandEntity;
import com.github.standobyte.jojo.entity.stand.StandEntityTask;
import com.github.standobyte.jojo.power.impl.stand.IStandPower;
import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import com.silentevermore.rotp_whitesnake.init.InitStands;
import com.silentevermore.rotp_whitesnake.network.PacketHandler;
import com.silentevermore.rotp_whitesnake.network.packets.server.WhitesnakeRenderPacket;
import com.silentevermore.rotp_whitesnake.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class WhitesnakeDisguise extends StandEntityAction{
    public WhitesnakeDisguise(Builder builder) {
        super(builder);
    }
    //methods
    @Override
    public void standPerform(World world, StandEntity stand, IStandPower userPower, StandEntityTask task){
        final WhitesnakeEntity whitesnake=(WhitesnakeEntity) stand;
        final ActionTarget actionTarget=task.getTarget();
        if (actionTarget.getEntity() instanceof LivingEntity){
            final LivingEntity victim=(LivingEntity) actionTarget.getEntity();

            if (!world.isClientSide())
                PacketHandler.sendGlobally(new WhitesnakeRenderPacket(victim.getId(), whitesnake.getId()), world.dimension());

            whitesnake.setDisguisedOnce(true);
            userPower.consumeStamina(100);
            userPower.setCooldownTimer(InitStands.DISGUISE.get(), 60);
        }else{
            whitesnake.setEntityForDisguise(null);
        }
    }
    @Override
    protected boolean standKeepsTarget(ActionTarget target){
        return target.getType()==ActionTarget.TargetType.ENTITY && target.getEntity() instanceof LivingEntity && !(target.getEntity() instanceof StandEntity);
    }
}
