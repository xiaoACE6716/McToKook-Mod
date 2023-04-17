package com.xiaoace.mctokook.listener;

import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.settings.Settings;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import snw.jkook.config.file.FileConfiguration;
import snw.jkook.entity.User;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.channel.ChannelMessageEvent;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.TextComponent;

public class KookListener implements Listener {

    //Kook消息监听器
    @EventHandler
    public void onKookMessage(ChannelMessageEvent channelMessageEvent){

        User kookUser = null;
        TextChannelMessage kookMessage = null;

        if (channelMessageEvent.getChannel().getId().equals(Settings.channel_ID)){

            String KookUserUUID = channelMessageEvent.getMessage().getSender().getId();
            //Kook消息发送者
            kookUser = channelMessageEvent.getMessage().getSender();
            kookMessage = channelMessageEvent.getMessage();

            String kookUserNickName = kookUser.getNickName(channelMessageEvent.getChannel().getGuild());

            BaseComponent component = kookMessage.getComponent();
            //将要发送至mc里的消息
            //没错，只有文字消息会被发到mc
            if (component instanceof TextComponent){

                TextComponent textComponent = (TextComponent) component;

                //测试用的
                McToKook.logger.info("来自KOOK的消息: " + textComponent);

                MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();

                String needFormatMessage = "用户: %s 说: %s";

                TextComponentString message = new TextComponentString(String.format(needFormatMessage, kookUserNickName, textComponent));
                server.getPlayerList().sendMessage(message);

            }
        }
    }

}
