package com.xiaoace.mctokook.listener.minecraft;

import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.settings.Settings;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import snw.jkook.entity.channel.Channel;
import snw.jkook.entity.channel.TextChannel;
import snw.kookbc.impl.KBCClient;

import java.util.concurrent.CompletableFuture;

public class OnPlayerMessage {

    static KBCClient kbcClient = McToKook.getKbcClient();
    private final McToKook mod;

    public OnPlayerMessage(McToKook mod){
        this.mod = mod;
    }

    @SubscribeEvent
    public void onChat(ServerChatEvent event) {

        CompletableFuture.runAsync(() -> {

            String message = event.getMessage();
            String playerName = event.getUsername();

            //测试用的
            McToKook.logger.info("来自游戏内的消息: " + message);

            String needFormatMessage = Settings.to_Kook_Message;

            String formattedMessage = needFormatMessage
                    .replaceAll("\\{playerName}", playerName)
                    .replaceAll("\\{message}", message);

            Channel channel = kbcClient.getCore().getHttpAPI().getChannel(Settings.channel_ID);

            if (channel instanceof TextChannel) {
                TextChannel textChannel = (TextChannel) channel;
                textChannel.sendComponent(formattedMessage);
            }
        });
    }

}
