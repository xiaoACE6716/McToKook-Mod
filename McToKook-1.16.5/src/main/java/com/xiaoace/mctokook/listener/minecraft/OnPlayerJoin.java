package com.xiaoace.mctokook.listener.minecraft;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.config.Config;
import net.minecraftforge.event.entity.player.PlayerEvent;
import snw.jkook.entity.abilities.Accessory;
import snw.jkook.entity.channel.Channel;
import snw.jkook.entity.channel.TextChannel;
import snw.jkook.message.component.card.CardBuilder;
import snw.jkook.message.component.card.MultipleCardComponent;
import snw.jkook.message.component.card.Size;
import snw.jkook.message.component.card.Theme;
import snw.jkook.message.component.card.element.ImageElement;
import snw.jkook.message.component.card.element.MarkdownElement;
import snw.jkook.message.component.card.module.SectionModule;
import snw.kookbc.impl.KBCClient;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.xiaoace.mctokook.utils.PlayerIcon.getPlayerIconUr;

public class OnPlayerJoin {

    public static void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {

        if (!Config.join_Message.get()) {
            return;
        }

        CompletableFuture.runAsync(() -> {
            KBCClient kbcClient = McToKook.getKbcClient();
            Channel channel = kbcClient.getCore().getHttpAPI().getChannel(Config.channel_ID.get());
            if (channel instanceof TextChannel) {
                TextChannel textChannel = (TextChannel) channel;
                textChannel.sendComponent(buildCard(event.getPlayer().getName().getString(), event.getPlayer().getGameProfile().getId().toString()));
            }
        });

    }

    private static MultipleCardComponent buildCard(String playerName, String playerUUID) {

        Map<String, String> map = MapUtil.builder(new HashMap<String, String>())
                .put("playerName", playerName)
                .map();

        return new CardBuilder().setTheme(Theme.SUCCESS).setSize(Size.LG)
                .addModule(new SectionModule(
                        new MarkdownElement(StrUtil.format(Config.player_Join_Message.get(), map)),
                        new ImageElement(getPlayerIconUr(playerUUID), null, Size.SM, false),
                        Accessory.Mode.LEFT))
                .build();

    }

}
