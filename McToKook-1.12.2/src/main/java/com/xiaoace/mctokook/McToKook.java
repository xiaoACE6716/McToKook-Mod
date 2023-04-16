package com.xiaoace.mctokook;

import com.xiaoace.mctokook.settings.Settings;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import snw.jkook.JKook;
import snw.jkook.config.file.YamlConfiguration;
import snw.jkook.entity.channel.TextChannel;
import snw.kookbc.impl.CoreImpl;
import snw.kookbc.impl.KBCClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Mod(
        modid = McToKook.MOD_ID,
        name = McToKook.MOD_NAME,
        version = McToKook.VERSION
)

public class McToKook {

    public static final String MOD_ID = "mctokook";
    public static final String MOD_NAME = "McToKook";
    public static final String VERSION = "1.0";

    private static File kbcSetting = new File(".","config/McToKook/kbc.yml");
    private static File configFolder = new File(".","config/McToKook");

    static KBCClient kbcClient = null;
    //提供一个get KookBC的方法
    public static KBCClient getKbcClient() {
        return kbcClient;
    }

    public static Logger logger = LogManager.getLogger();

    //主类实例工厂
    @Mod.Instance(MOD_ID)
    public static McToKook INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event){

        logger.info("Hello Forge! Here is McToKooK");

        if (!configFolder.exists()){
            configFolder.mkdir();
        }
        //KookBC保存基础配置文件
        saveKBCConfig();

        YamlConfiguration config = new YamlConfiguration();

        CoreImpl core = new CoreImpl();
        JKook.setCore(core);

        //读取配置拿必要的东西
        String bot_token = Settings.bot_token;
        String channel_ID = Settings.channel_ID;

        if (bot_token.equals("No token provided")){
            logger.info("你没有提供bot-token或者bot-token不正确");
            //getLogger().log(Level.SEVERE,bot_token);
            return;
        } else {
            if (channel_ID.equals("No channel ID provided")){
                logger.info("你没有提供channel ID或channel ID不正确");
                logger.info("你所提供的channel_ID: " + channel_ID);
                return;
            }
        }

        kbcClient = new KBCClient(core,config,null,bot_token);

        kbcClient.start();
        TextChannel channel = (TextChannel) kbcClient.getCore().getHttpAPI().getChannel(channel_ID);
        channel.sendComponent("您正在使用McToKook-Mod版");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {
    }

    //KookBC保存配置文件 爱来自夏夜
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