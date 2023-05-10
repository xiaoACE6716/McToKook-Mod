package com.xiaoace.mctokook.settings;

import com.xiaoace.mctokook.McToKook;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = McToKook.MOD_ID)
@Mod.EventBusSubscriber(modid = McToKook.MOD_ID)
public class Settings {

    @Config.Comment({"这里应该填入bot的token", "注意! 如果不填写此项服务端会直接崩溃"})
    @Config.RequiresMcRestart
    public static String bot_token = "No token provided";

    @Config.Comment({"这里应该填入要进行McToKook的Kook频道ID", "注意! 如果不填写此项服务端会直接崩溃"})
    @Config.RequiresMcRestart
    public static String channel_ID = "No channel ID provided";

    @Config.Comment(
            {"自定义MC消息格式 你可以按照默认给出的样式去自定义，记得保留带{}的内容"
                    , "默认为: 用户: {nickName} 说: {message}"}
    )
    @Config.RequiresMcRestart
    public static String to_Minecraft_Message = "用户: {nickName} 说: {message}";

    @Config.Comment(
            {"自定义KOOK消息格式 你可以按照默认给出的样式去自定义，记得保留带{}的内容"
                    , "默认为: 玩家: {playerName} 说: {message}"}
    )
    @Config.RequiresMcRestart
    public static String to_Kook_Message = "玩家: {playerName} 说: {message}";

    @Config.Comment(
            {"自定义MC玩家上线消息格式 你可以按照默认给出的样式去自定义，记得保留带{}的内容"
                    , "默认为: {playerName}偷偷的溜进了服务器"}
    )
    @Config.RequiresMcRestart
    public static String player_Join_Message = "{playerName}偷偷的溜进了服务器";

    @Config.Comment(
            {"自定义MC玩家下线消息格式 你可以按照默认给出的样式去自定义，记得保留带{}的内容"
                    , "默认为: 肝帝{playerName}歇逼了"}
    )
    @Config.RequiresMcRestart
    public static String player_Quit_Message = "肝帝{playerName}歇逼了";

    @Config.Comment(
            {"是否启用Kook to Minecraft"
                    , "默认为true 如果想关闭请改为false"}
    )
    @Config.RequiresMcRestart
    public static Boolean to_Minecraft = true;

    @Config.Comment(
            {"是否启用Minecraft to Kook"
                    , "默认为true 如果想关闭请改为false"}
    )
    @Config.RequiresMcRestart
    public static Boolean to_Kook = true;

    @Config.Comment(
            {"是否启用玩家进入游戏提示"
                    , "默认为true 如果想关闭请改为false"}
    )
    @Config.RequiresMcRestart
    public static Boolean join_Message = true;

    @Config.Comment(
            {"是否启用玩家退出游戏提示"
                    , "默认为true 如果想关闭请改为false"}
    )
    @Config.RequiresMcRestart
    public static Boolean quit_Message = true;

    @SubscribeEvent
    public static void onConfigChange(ConfigChangedEvent event) {
        if (event.getModID().equals(McToKook.MOD_ID)) {
            ConfigManager.sync(McToKook.MOD_ID, Config.Type.INSTANCE);
        }
    }

}
