package com.xiaoace.mctokook.settings;

import com.xiaoace.mctokook.McToKook;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = McToKook.MOD_ID)
@Mod.EventBusSubscriber(modid = McToKook.MOD_ID)
public class Settings {

    @Config.Comment("这里应该填入bot的token")
    @Config.RequiresMcRestart
    public static String bot_token = "No token provided";

    @Config.Comment("这里应该填入要进行McToKook的Kook频道ID")
    @Config.RequiresMcRestart
    public static String channel_ID = "No channel ID provided";

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent event) {
        if (event.getModID().equals(McToKook.MOD_ID)) {
            ConfigManager.sync(McToKook.MOD_ID, Config.Type.INSTANCE);
        }
    }

}
