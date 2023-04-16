package com.xiaoace.mctokook.settings;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class SettingsManager {

    public static Configuration config;

    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfig();
        }
    }

    private static void loadConfig() {
        config.load();

        String botToken = Settings.bot_token;
        String channelID = Settings.channel_ID;

        System.out.println("botToken: " +botToken);
        System.out.println("channelID: " +channelID);

        // 使用 botToken 和 channelID 值做进一步处理

        if (config.hasChanged()) {
            config.save();
        }
    }
}
