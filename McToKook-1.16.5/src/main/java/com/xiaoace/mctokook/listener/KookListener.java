package com.xiaoace.mctokook.listener;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaoace.mctokook.config.Config;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import snw.jkook.event.EventHandler;
import snw.jkook.event.Listener;
import snw.jkook.event.channel.ChannelMessageEvent;
import snw.jkook.message.component.BaseComponent;
import snw.jkook.message.component.TextComponent;

import java.util.HashMap;
import java.util.Map;

public class KookListener implements Listener {

    @EventHandler
    public void onKookMessage(ChannelMessageEvent event) {

        if (!Config.to_Minecraft.get()) {
            return;
        }

        if (event.getMessage().getSender().isBot()) {
            return;
        }

        if (event.getChannel().getId().equals(Config.channel_ID.get())) {

            BaseComponent component = event.getMessage().getComponent();

            if (component instanceof TextComponent) {

                Map<String,String> map = MapUtil.builder(new HashMap<String, String>())
                        .put("nickName", event.getMessage().getSender().getNickName(event.getChannel().getGuild()))
                        .put("message", component.toString())
                        .map();

                ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers().forEach(player -> player.sendMessage(
                        new StringTextComponent(StrUtil.format(Config.to_Minecraft_Message.get(),map)),
                        player.getUniqueID()));
            }
        }

    }

}
