package com.xiaoace.mctokook.listener.minecraft;

import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.config.Config;
import net.minecraftforge.event.ServerChatEvent;
import snw.jkook.entity.channel.Channel;
import snw.jkook.entity.channel.TextChannel;
import snw.kookbc.impl.KBCClient;

import java.util.concurrent.CompletableFuture;

public class OnPlayerMessage {

    public static void onChat(ServerChatEvent event) {

        if (!Config.to_Kook.get()) {
            return;
        }

        CompletableFuture.runAsync(() -> {

            KBCClient kbcClient = McToKook.getKbcClient();

            String message = event.getMessage();
            String playerName = event.getUsername();

            String needFormatMessage = Config.to_Kook_Message.get();

            String formattedMessage = needFormatMessage.replaceAll("\\{playerName}", playerName).replaceAll("\\{message}", message);

            Channel channel = kbcClient.getCore().getHttpAPI().getChannel(Config.channel_ID.get());

            if (channel instanceof TextChannel) {
                TextChannel textChannel = (TextChannel) channel;
                textChannel.sendComponent(formattedMessage);
            }
        });
    }

}
