package com.xiaoace.mctokook.listener.minecraft;

import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.config.Config;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import snw.jkook.entity.channel.Channel;
import snw.jkook.entity.channel.TextChannel;
import snw.kookbc.impl.KBCClient;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = McToKook.MOD_ID)
public class OnPlayerQuit {

    @SubscribeEvent
    public static void onPlayerQuit(PlayerEvent.PlayerLoggedOutEvent event){

        if (!Config.quit_Message.get()){
            return;
        }

        CompletableFuture.runAsync(() ->{

            KBCClient kbcClient = McToKook.getKbcClient();

            String playerName = event.getPlayer().getName().getString();
            String needFormatMessage = Config.player_Quit_Message.get();
            String formattedMessage = needFormatMessage.replaceAll("\\{playerName}", playerName);

            Channel channel = kbcClient.getCore().getHttpAPI().getChannel(Config.channel_ID.get());

            if (channel instanceof TextChannel){
                TextChannel textChannel = (TextChannel) channel;
                textChannel.sendComponent(formattedMessage);
            }

        });

    }

}
