package com.silentevermore.rotp_whitesnake.network.packets.client;

import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.silentevermore.rotp_whitesnake.WhitesnakeConfig;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class CommonConfigPacket {
    private final WhitesnakeConfig.Common.SyncedValues values;

    public CommonConfigPacket(WhitesnakeConfig.Common.SyncedValues values) {
        this.values = values;
    }

    public static class Handler implements IModPacketHandler<CommonConfigPacket>{
        public Handler() {
        }

        public void encode(CommonConfigPacket msg, PacketBuffer buf) {
            msg.values.writeToBuf(buf);
        }

        public CommonConfigPacket decode(PacketBuffer buf) {
            return new CommonConfigPacket(new WhitesnakeConfig.Common.SyncedValues(buf));
        }

        public void handle(CommonConfigPacket msg, Supplier<NetworkEvent.Context> ctx) {
            msg.values.changeConfigValues();
        }

        public Class<CommonConfigPacket> getPacketClass() {
            return CommonConfigPacket.class;
        }
    }
}