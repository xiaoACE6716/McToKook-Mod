package com.xiaoace.mctokook;

import com.xiaoace.mctokook.config.Config;
import com.xiaoace.mctokook.listener.KookListener;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snw.jkook.JKook;
import snw.jkook.config.file.YamlConfiguration;
import snw.kookbc.impl.CoreImpl;
import snw.kookbc.impl.KBCClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Mod(McToKook.MOD_ID)
public class McToKook {

    public static final String MOD_ID = "mctokook";

    private static final Logger LOGGER = LogManager.getLogger("McToKook");

    private static final File kbcSetting = new File(".", "config/McToKook/kbc.yml");
    private static final File configFolder = new File(".", "config/McToKook");
    static KBCClient kbcClient = null;

    //提供一个get KookBC的方法
    public static KBCClient getKbcClient() {
        return kbcClient;
    }

    public McToKook(){
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {

    }

    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event){
        LOGGER.info("Hello Server! Here is McToKook");


    }

    @SubscribeEvent
    public void onServerStarted(FMLServerStartedEvent event){

        if (!configFolder.exists()) {
            configFolder.mkdir();
        }
        //KookBC保存基础配置文件
        saveKBCConfig();
        YamlConfiguration config = new YamlConfiguration();

        CoreImpl core = new CoreImpl();
        JKook.setCore(core);

        //读取配置拿必要的东西
        String bot_token = Config.bot_token.get();
        String channel_ID = Config.channel_ID.get();

        if (bot_token.equals("No token provided")) {
            LOGGER.info("你没有提供bot-token或者bot-token不正确");
            LOGGER.info("McToKook-Mod将会停用");
            throw new Error("你没有提供bot-token或者bot-token不正确,McToKook-Mod将会停用,服务端即将崩溃");
        } else {
            if (channel_ID.equals("No channel ID provided")) {
                LOGGER.info("你没有提供channel ID或channel ID不正确");
                LOGGER.info("你所提供的channel_ID: " + channel_ID);
                throw new Error("你没有提供channel ID或channel ID不正确,McToKook-Mod将会停用,服务端即将崩溃");
            }
        }

        kbcClient = new KBCClient(core, config, null, bot_token);

        kbcClient.start();
        LOGGER.info("机器人启动！");

        //注册KOOK消息监听器
        //夏夜说: 不要用InternalPlugin,但是我摆了！
        kbcClient.getCore().getEventManager().registerHandlers(kbcClient.getInternalPlugin(), new KookListener());
        //((TextChannel)kbcClient.getCore().getHttpAPI().getChannel(Config.channel_ID.get())).sendComponent("服务器上线了");

    }

    private static void saveKBCConfig() {
        try (final InputStream stream = McToKook.class.getResourceAsStream("/kbc.yml")) {
            if (stream == null) {
                throw new Error("Unable to find kbc.yml");
            }
            if (kbcSetting.exists()) {
                return;
            }
            //noinspection ResultOfMethodCallIgnored
            kbcSetting.createNewFile();

            try (final FileOutputStream out = new FileOutputStream(kbcSetting)) {
                int index;
                byte[] bytes = new byte[1024];
                while ((index = stream.read(bytes)) != -1) {
                    out.write(bytes, 0, index);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
