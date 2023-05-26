package com.xiaoace.mctokook.listener.minecraft;

import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.config.Config;
import net.minecraftforge.event.entity.player.PlayerEvent;
import snw.jkook.entity.channel.Channel;
import snw.jkook.entity.channel.TextChannel;
import snw.kookbc.impl.KBCClient;

import java.util.concurrent.CompletableFuture;

public class OnPlayerJoin {

    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {

        if (!Config.join_Message.get()) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            KBCClient kbcClient = McToKook.getKbcClient();

            String playerName = event.getPlayer().getName().getString();
            String needFormatMessage = Config.player_Join_Message.get();
            String formattedMessage = needFormatMessage.replaceAll("\\{playerName}", playerName);

            Channel channel = kbcClient.getCore().getHttpAPI().getChannel(Config.channel_ID.get());
            if (channel instanceof TextChannel) {
                TextChannel textChannel = (TextChannel) channel;
                textChannel.sendComponent(formattedMessage);
            }
        });

    }

}
