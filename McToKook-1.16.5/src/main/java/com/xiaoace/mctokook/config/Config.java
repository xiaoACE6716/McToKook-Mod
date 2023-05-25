package com.xiaoace.mctokook.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.ConfigValue<String> bot_token;
    public static ForgeConfigSpec.ConfigValue<String> channel_ID;
    public static ForgeConfigSpec.BooleanValue join_Message;
    public static ForgeConfigSpec.ConfigValue<String> player_Join_Message;
    public static ForgeConfigSpec.BooleanValue quit_Message;
    public static ForgeConfigSpec.ConfigValue<String> player_Quit_Message;
    public static ForgeConfigSpec.BooleanValue to_Kook;
    public static ForgeConfigSpec.ConfigValue<String> to_Kook_Message;
    public static ForgeConfigSpec.BooleanValue to_Minecraft;
    public static ForgeConfigSpec.ConfigValue<String> to_Minecraft_Message;

    static {

        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("McToKook的基本配置").push("general");
        bot_token = COMMON_BUILDER
                .comment("这里应该填入bot的token", "注意! 如果不填写此项服务端会直接崩溃")
                .define("bot_token", "No token provided");

        channel_ID = COMMON_BUILDER
                .comment("这里应该填入要进行McToKook的Kook频道ID", "注意! 如果不填写此项服务端会直接崩溃")
                .define("channel_ID", "No channel ID provided");

        join_Message = COMMON_BUILDER
                .comment("是否启用玩家进入游戏提示", "默认为true 如果想关闭请改为false")
                .define("join_Message", true);

        player_Join_Message = COMMON_BUILDER
                .comment("自定义MC玩家上线消息格式 你可以按照默认给出的样式去自定义，记得保留带{}的内容", "默认为: {playerName}偷偷的溜进了服务器")
                .define("player_Join_Message", "{playerName}偷偷的溜进了服务器");

        quit_Message = COMMON_BUILDER
                .comment("是否启用玩家退出游戏提示", "默认为true 如果想关闭请改为false")
                .define("quit_Message", true);

        player_Quit_Message = COMMON_BUILDER
                .comment("自定义MC玩家下线消息格式 你可以按照默认给出的样式去自定义，记得保留带{}的内容", "默认为: 肝帝{playerName}歇逼了")
                .define("player_Quit_Message", "肝帝{playerName}歇逼了");

        to_Kook = COMMON_BUILDER
                .comment("是否启用Minecraft to Kook", "默认为true 如果想关闭请改为false")
                .define("to_Kook", true);

        to_Kook_Message = COMMON_BUILDER
                .comment("自定义KOOK消息格式 你可以按照默认给出的样式去自定义，记得保留带{}的内容", "默认为: 玩家: {playerName} 说: {message}")
                .define("to_Kook_Message", "玩家: {playerName} 说: {message}");

        to_Minecraft = COMMON_BUILDER
                .comment("是否启用Kook to Minecraft", "默认为true 如果想关闭请改为false")
                .define("to_Minecraft", true);

        to_Minecraft_Message = COMMON_BUILDER
                .comment("自定义MC消息格式 你可以按照默认给出的样式去自定义，记得保留带{}的内容", "默认为: 用户: {nickName} 说: {message}")
                .define("to_Minecraft_Message", "用户: {nickName} 说: {message}");

        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }

}
