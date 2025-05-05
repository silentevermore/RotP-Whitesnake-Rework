package com.silentevermore.rotp_whitesnake;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.InMemoryCommentedFormat;
import com.github.standobyte.jojo.JojoModConfig;
import com.github.standobyte.jojo.client.ClientUtil;
import com.github.standobyte.jojo.network.PacketManager;
import com.silentevermore.rotp_whitesnake.network.packets.client.CommonConfigPacket;
import com.silentevermore.rotp_whitesnake.network.packets.client.ResetSyncedCommonConfigPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(modid="rotp_whitesnake", bus=Mod.EventBusSubscriber.Bus.MOD)
public class WhitesnakeConfig{
    //constants
    static final ForgeConfigSpec commonSpec;
    private static final WhitesnakeConfig.Common COMMON_FROM_FILE;
    private static final WhitesnakeConfig.Common COMMON_SYNCED_TO_CLIENT;
    //builder
    public WhitesnakeConfig(){}
    //methods
    private static boolean isElementNonNegativeFloat(Object num, boolean moreThanZero) {
        if (!(num instanceof Double)) {
            return false;
        } else {
            Double numDouble = (Double)num;
            return (numDouble > (double)0.0F || !moreThanZero && numDouble == (double)0.0F) && Float.isFinite(numDouble.floatValue());
        }
    }
    public static WhitesnakeConfig.Common getCommonConfigInstance(boolean isClientSide) {
        return isClientSide && !ClientUtil.isLocalServer() ? COMMON_SYNCED_TO_CLIENT : COMMON_FROM_FILE;
    }
    //events
    @SubscribeEvent
    public static void onConfigLoad(ModConfig.ModConfigEvent event) {
        ModConfig config = event.getConfig();
        if ("rotp_whitesnake".equals(config.getModId()) && config.getType()==ModConfig.Type.COMMON) {
            COMMON_FROM_FILE.onLoadOrReload();
        }
    }
    @SubscribeEvent
    public static void onConfigReload(ModConfig.Reloading event) {
        ModConfig config = event.getConfig();
        if ("rotp_whitesnake".equals(config.getModId()) && config.getType()==ModConfig.Type.COMMON) {
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server != null) {
                server.getPlayerList().getPlayers().forEach((player) -> WhitesnakeConfig.Common.SyncedValues.syncWithClient(player));
            }
        }
    }
    static {
        Pair<WhitesnakeConfig.Common, ForgeConfigSpec> specPair = (new ForgeConfigSpec.Builder()).configure(Common::new);
        commonSpec = (ForgeConfigSpec)specPair.getRight();
        COMMON_FROM_FILE = (WhitesnakeConfig.Common)specPair.getLeft();
        Pair<WhitesnakeConfig.Common, ForgeConfigSpec> syncedSpecPair = (new ForgeConfigSpec.Builder()).configure((builder) -> new WhitesnakeConfig.Common(builder, "synced"));
        CommentedConfig config = CommentedConfig.of(InMemoryCommentedFormat.defaultInstance());
        ForgeConfigSpec syncedSpec = (ForgeConfigSpec)syncedSpecPair.getRight();
        syncedSpec.correct(config);
        syncedSpec.setConfig(config);
        COMMON_SYNCED_TO_CLIENT = (WhitesnakeConfig.Common)syncedSpecPair.getLeft();
    }
    //the whole config
    public static class Common {
        //constants
        private boolean loaded;
        public final ForgeConfigSpec.BooleanValue showGlintOnWhitesnake;
        //methods
        private Common(ForgeConfigSpec.Builder builder) {
            this(builder, (String)null);
        }

        private Common(ForgeConfigSpec.Builder builder, @Nullable String mainPath) {
            this.loaded = false;
            if (mainPath!=null) builder.push(mainPath);

            builder.push("Whitesnake glint effect");
            this.showGlintOnWhitesnake = builder.translation("rotp_whitesnake.glint_config").define("showGlintOnWhitesnake", true);
            builder.pop();

            if (mainPath!=null) builder.pop();
        }

        public boolean isConfigLoaded(){
            return this.loaded;
        }

        private void onLoadOrReload(){
            this.loaded=true;
        }

        public static class SyncedValues{
            private final boolean showGlintOnWhitesnake;
            public SyncedValues(PacketBuffer buf){
                this.showGlintOnWhitesnake = buf.readBoolean();
            }
            public void writeToBuf(PacketBuffer buf){
                buf.writeBoolean(this.showGlintOnWhitesnake);
            }
            private SyncedValues(WhitesnakeConfig.Common config){
                this.showGlintOnWhitesnake = (Boolean) config.showGlintOnWhitesnake.get();
            }
            public void changeConfigValues(){
                WhitesnakeConfig.COMMON_SYNCED_TO_CLIENT.showGlintOnWhitesnake.set(this.showGlintOnWhitesnake);
            }
            public static void resetConfig(){
                WhitesnakeConfig.COMMON_SYNCED_TO_CLIENT.showGlintOnWhitesnake.clearCache();
            }
            public static void syncWithClient(ServerPlayerEntity player) {
                PacketManager.sendToClient(new CommonConfigPacket(new WhitesnakeConfig.Common.SyncedValues(WhitesnakeConfig.COMMON_FROM_FILE)), player);
            }
            public static void onPlayerLogout(ServerPlayerEntity player) {
                PacketManager.sendToClient(new ResetSyncedCommonConfigPacket(), player);
            }
        }
    }
}