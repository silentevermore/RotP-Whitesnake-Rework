package com.silentevermore.rotp_whitesnake.network.packets.server;

import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.silentevermore.rotp_whitesnake.entity.WhitesnakeEntity;
import com.silentevermore.rotp_whitesnake.network.PacketHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class WhitesnakeRenderPacket{
    //constants
    private final ResourceLocation entityRes;
    private final int standId;
    //builder
    public WhitesnakeRenderPacket(ResourceLocation entityRes, int whitesnake){
        this.entityRes=entityRes;
        this.standId=whitesnake;
    }
    //other
    public static class Handler implements IModPacketHandler<WhitesnakeRenderPacket>{
        @Override
        public void encode(WhitesnakeRenderPacket msg, PacketBuffer buf){
            buf.writeResourceLocation(msg.entityRes);
            buf.writeInt(msg.standId);
        }
        @Override
        public WhitesnakeRenderPacket decode(PacketBuffer buf){
            return new WhitesnakeRenderPacket(buf.readResourceLocation(), buf.readInt());
        }
        @Override
        public void handle(WhitesnakeRenderPacket msg, Supplier<NetworkEvent.Context> ctx){
            final PlayerEntity player=ctx.get().getSender();
            final Entity stand=ClientUtil.getEntityById(msg.standId);
            if (stand instanceof WhitesnakeEntity){
                final WhitesnakeEntity whitesnake=(WhitesnakeEntity) stand;
                //send data to all clients
                if (player!=null && !player.level.isClientSide()) PacketHandler.sendGlobally(new WhitesnakeRenderPacket(msg.entityRes, whitesnake.getId()), player.level.dimension());
                //assign values
                whitesnake.setEntityForDisguise(ForgeRegistries.ENTITIES.getValue(msg.entityRes));
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
