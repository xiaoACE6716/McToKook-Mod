package com.xiaoace.mctokook.listener;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.emoji.EmojiUtil;
import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.settings.Settings;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import snw.jkook.entity.User;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.channel.ChannelMessageEvent;
import snw.jkook.message.TextChannelMessage;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.TextComponent;

import java.util.HashMap;
import java.util.Map;

public class KookListener implements Listener {

    private final McToKook mod;

    public KookListener(McToKook mod) {
        this.mod = mod;
    }

    //Kook消息监听器
    @EventHandler
    public void onKookMessage(ChannelMessageEvent channelMessageEvent) {

        if (!Settings.to_Minecraft) {
            return;
        }

        if (channelMessageEvent.getMessage().getSender().isBot()) {
            return;
        }

        User kookUser = null;
        TextChannelMessage kookMessage = null;

        if (channelMessageEvent.getChannel().getId().equals(Settings.channel_ID)) {

            //Kook消息发送者
            kookUser = channelMessageEvent.getMessage().getSender();
            kookMessage = channelMessageEvent.getMessage();

            String kookUserNickName = kookUser.getNickName(channelMessageEvent.getChannel().getGuild());

            BaseComponent component = kookMessage.getComponent();
            //将要发送至mc里的消息
            //没错，只有文字消息会被发到mc
            if (component instanceof TextComponent) {

                //将kook的消息里的emoji转换成短码形式
                //然后再将它转成对应的韩文
                String the_message_from_kook = mod.getEmojiHandler().toEmoji(EmojiUtil.toAlias(component.toString()));

                Map<String, String> map = MapUtil.builder(new HashMap<String, String>())
                        .put("nickName", kookUserNickName)
                        .put("message", the_message_from_kook)
                        .map();

                FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(new TextComponentString(StrUtil.format(Settings.to_Minecraft_Message, map)));
            }
        }
    }

}
