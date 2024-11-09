package com.silentevermore.rotp_whitesnake.network.packets.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class WhitesnakeRenderPacket{
    //constants
    private final int victimId;
    private final int standId;
    //builder
    public WhitesnakeRenderPacket(int victim, int whitesnake){
        this.victimId=victim;
        this.standId=whitesnake;
    }
    //other
    public static class Handler implements IModPacketHandler<WhitesnakeRenderPacket>{
        @Override
        public void encode(WhitesnakeRenderPacket msg, PacketBuffer buf){
            buf.writeInt(msg.victimId);
            buf.writeInt(msg.standId);
        }
        @Override
        public WhitesnakeRenderPacket decode(PacketBuffer buf){
            return new WhitesnakeRenderPacket(buf.readInt(), buf.readInt());
        }
        @Override
        public void handle(WhitesnakeRenderPacket msg, Supplier<NetworkEvent.Context> ctx){
            final PlayerEntity player=ctx.get().getSender();
            final Entity stand=ClientUtil.getEntityById(msg.standId);
            final Entity victim=ClientUtil.getEntityById(msg.victimId);

            if (stand instanceof WhitesnakeEntity && victim instanceof LivingEntity){
                final WhitesnakeEntity whitesnake=(WhitesnakeEntity) stand;
                final LivingEntity living=(LivingEntity) victim;
                whitesnake.setEntityForDisguise(living);
                whitesnake.setDisguisedOnce(true);
                whitesnake.setShapeshiftTick(whitesnake.tickCount);
            }
        }
        @Override
        public Class<WhitesnakeRenderPacket> getPacketClass(){
            return WhitesnakeRenderPacket.class;
        }
    }
}
