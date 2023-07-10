package com.xiaoace.mctokook.listener.minecraft;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.settings.Settings;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snw.jkook.entity.channel.Channel;
import snw.jkook.entity.channel.TextChannel;
import snw.kookbc.impl.KBCClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class OnPlayerMessage {

    static KBCClient kbcClient = McToKook.getKbcClient();

    @SubscribeEvent
    public void onChat(ServerChatEvent event) {

        if (!Settings.to_Kook){
            return;
        }

        CompletableFuture.runAsync(() -> {
            Channel channel = kbcClient.getCore().getHttpAPI().getChannel(Settings.channel_ID);
            if (channel instanceof TextChannel) {

                Map<String, String> map = MapUtil.builder(new HashMap<String, String>())
                        .put("playerName", event.getUsername())
                        .put("message", event.getMessage())
                        .map();

                TextChannel textChannel = (TextChannel) channel;
                textChannel.sendComponent(StrUtil.format(Settings.to_Kook_Message, map));
            }
        });
    }

}
