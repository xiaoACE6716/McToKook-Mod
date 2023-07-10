package com.xiaoace.mctokook.listener.minecraft;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.xiaoace.mctokook.McToKook;
import com.xiaoace.mctokook.settings.Settings;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
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

    static KBCClient kbcClient = McToKook.getKbcClient();

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent loggedInEvent) {

        if (!Settings.join_Message) {
            return;
        }

        CompletableFuture.runAsync(() -> {

            Channel channel = kbcClient.getCore().getHttpAPI().getChannel(Settings.channel_ID);

            if (channel instanceof TextChannel) {
                TextChannel textChannel = (TextChannel) channel;
                textChannel.sendComponent(buildCard(loggedInEvent.player.getName(), loggedInEvent.player.getGameProfile().getId().toString()));
            }
        });

    }

    private static MultipleCardComponent buildCard(String playerName, String playerUUID) {

        Map<String, String> map = MapUtil.builder(new HashMap<String, String>())
                .put("playerName", playerName)
                .map();

        return new CardBuilder().setTheme(Theme.SUCCESS).setSize(Size.LG)
                .addModule(new SectionModule(
                        new MarkdownElement(StrUtil.format(Settings.player_Join_Message, map)),
                        new ImageElement(getPlayerIconUr(playerUUID), null, Size.SM, false),
                        Accessory.Mode.LEFT))
                .build();

    }

}
