package com.silentevermore.rotp_whitesnake.network.packets.client;

import com.github.standobyte.jojo.network.packets.IModPacketHandler;
import com.silentevermore.rotp_whitesnake.WhitesnakeConfig;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class ResetSyncedCommonConfigPacket {
    public ResetSyncedCommonConfigPacket() {
    }

    public static class Handler implements IModPacketHandler<ResetSyncedCommonConfigPacket> {
        public Handler() {
        }

        public void encode(ResetSyncedCommonConfigPacket msg, PacketBuffer buf) {
        }

        public ResetSyncedCommonConfigPacket decode(PacketBuffer buf) {
            return new ResetSyncedCommonConfigPacket();
        }

        public void handle(ResetSyncedCommonConfigPacket msg, Supplier<NetworkEvent.Context> ctx) {
            WhitesnakeConfig.Common.SyncedValues.resetConfig();
        }

        public Class<ResetSyncedCommonConfigPacket> getPacketClass() {
            return ResetSyncedCommonConfigPacket.class;
        }
    }
}