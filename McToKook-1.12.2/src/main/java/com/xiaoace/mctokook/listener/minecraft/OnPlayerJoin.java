package com.xiaoace.mctokook.listener.minecraft;

import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.settings.Settings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import snw.jkook.entity.channel.Channel;
import snw.jkook.entity.channel.TextChannel;
import snw.kookbc.impl.KBCClient;

import java.util.concurrent.CompletableFuture;

public class OnPlayerJoin {

    static KBCClient kbcClient = McToKook.getKbcClient();

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent loggedInEvent) {

        if (!Settings.join_Message) {
            return;
        }

        CompletableFuture.runAsync(() -> {

            String playerName = loggedInEvent.player.getName();

            String needFormatMessage = Settings.player_Join_Message;

            String formattedMessage = needFormatMessage.replaceAll("\\{playerName}", playerName);

            Channel channel = kbcClient.getCore().getHttpAPI().getChannel(Settings.channel_ID);

            if (channel instanceof TextChannel) {
                TextChannel textChannel = (TextChannel) channel;
                textChannel.sendComponent(formattedMessage);
            }
        });

    }
}
