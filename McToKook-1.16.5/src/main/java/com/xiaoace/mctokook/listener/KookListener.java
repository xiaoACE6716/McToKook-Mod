package com.xiaoace.mctokook.listener;

import com.xiaoace.mctokook.config.Config;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import snw.jkook.entity.User;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.channel.ChannelMessageEvent;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.TextComponent;

import static com.xiaoace.mctokook.utils.MinecraftTextConverter.convertToMinecraftFormat;

public class KookListener implements Listener {

    @EventHandler
    public void onKookMessage(ChannelMessageEvent event) {

        if (!Config.to_Minecraft.get()) {
            return;
        }

        User kookUser;
        TextChannelMessage kookMessage;

        if (event.getChannel().getId().equals(Config.channel_ID.get())) {

            //Kook消息发送者
            kookUser = event.getMessage().getSender();
            kookMessage = event.getMessage();

            String kookUserNickName = kookUser.getNickName(event.getChannel().getGuild());

            BaseComponent component = kookMessage.getComponent();
            //将要发送至mc里的消息
            //没错，只有文字消息会被发到mc
            if (component instanceof TextComponent) {

                String the_message_from_kook = component.toString();

                String needFormatMessage = Config.to_Minecraft_Message.get();

                String formattedMessage = needFormatMessage
                        .replaceAll("\\{nickName}", kookUserNickName)
                        .replaceAll("\\{message}", convertToMinecraftFormat(the_message_from_kook));

                StringTextComponent message = new StringTextComponent(formattedMessage);

                ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach(player -> player.sendMessage(message, player.getUniqueID()));

            }
        }

    }

}
