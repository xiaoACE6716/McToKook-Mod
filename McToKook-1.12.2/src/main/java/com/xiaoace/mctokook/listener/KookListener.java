package com.xiaoace.mctokook.listener;

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

import static com.xiaoace.mctokook.utils.MinecraftTextConverter.convertToMinecraftFormat;

public class KookListener implements Listener {

    private final McToKook mod;

    public KookListener(McToKook mod){
        this.mod = mod;
    }

    //Kook消息监听器
    @EventHandler
    public void onKookMessage(ChannelMessageEvent channelMessageEvent) {

        if (!Settings.to_Minecraft) {
            return;
        }

        User kookUser = null;
        TextChannelMessage kookMessage = null;

        if (channelMessageEvent.getChannel().getId().equals(Settings.channel_ID)) {

            String KookUserUUID = channelMessageEvent.getMessage().getSender().getId();
            //Kook消息发送者
            kookUser = channelMessageEvent.getMessage().getSender();
            kookMessage = channelMessageEvent.getMessage();

            String kookUserNickName = kookUser.getNickName(channelMessageEvent.getChannel().getGuild());

            BaseComponent component = kookMessage.getComponent();
            //将要发送至mc里的消息
            //没错，只有文字消息会被发到mc
            if (component instanceof TextComponent) {

                TextComponent textComponent = (TextComponent) component;

                //先将kook的消息里的emoji转换成短码形式
                String the_message_from_kook = EmojiUtil.toAlias(component.toString());
                //然后再将它转成对应的韩文
                the_message_from_kook = mod.getEmojiHandler().toEmoji(the_message_from_kook);

                //测试用的
                McToKook.logger.info("来自KOOK的消息: " + textComponent);

                String needFormatMessage = Settings.to_Minecraft_Message;

                String formattedMessage = needFormatMessage
                        .replaceAll("\\{nickName}", kookUserNickName)
                        .replaceAll("\\{message}", convertToMinecraftFormat(the_message_from_kook));

                TextComponentString message = new TextComponentString(formattedMessage);

                FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().sendMessage(message);
            }
        }
    }

}
