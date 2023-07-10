package com.xiaoace.mctokook.listener.minecraft;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.config.Config;
import net.minecraftforge.event.ServerChatEvent;
import snw.jkook.entity.channel.Channel;
import snw.jkook.entity.channel.TextChannel;
import snw.kookbc.impl.KBCClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class OnPlayerMessage {

    public static void onChat(ServerChatEvent event) {

        if (!Config.to_Kook.get()) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            KBCClient kbcClient = McToKook.getKbcClient();
            Channel channel = kbcClient.getCore().getHttpAPI().getChannel(Config.channel_ID.get());
            if (channel instanceof TextChannel) {

                Map<String, String> map = MapUtil.builder(new HashMap<String, String>())
                        .put("playerName", event.getUsername())
                        .put("message", event.getMessage())
                        .map();

                TextChannel textChannel = (TextChannel) channel;
                textChannel.sendComponent(StrUtil.format(Config.to_Kook_Message.get(), map));
            }
        });
    }

}
