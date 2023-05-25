package com.xiaoace.mctokook;

import com.xiaoace.mctokook.emoji.EmojiHandler;
import com.xiaoace.mctokook.listener.KookListener;
import com.xiaoace.mctokook.listener.minecraft.OnPlayerJoin;
import com.xiaoace.mctokook.listener.minecraft.OnPlayerMessage;
import com.xiaoace.mctokook.listener.minecraft.OnPlayerQuit;
import com.xiaoace.mctokook.settings.Settings;
import net.minecraftforge.common.MinecraftForge;
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
        version = McToKook.VERSION,
        serverSideOnly = McToKook.serverSideOnly,
        acceptedMinecraftVersions = "1.12.2",
        acceptableRemoteVersions = "*"
)

public class McToKook {

    public static final String MOD_ID = "mctokook";
    public static final String MOD_NAME = "McToKook";
    public static final String VERSION = "1.0";
    public static final boolean serverSideOnly = true;

    private static final File kbcSetting = new File(".", "config/McToKook/kbc.yml");
    private static final File configFolder = new File(".", "config/McToKook");

    static KBCClient kbcClient = null;

    //提供一个get KookBC的方法
    public static KBCClient getKbcClient() {
        return kbcClient;
    }

    public static Logger logger = LogManager.getLogger("McToKook");

    //让xiaoACE emo的 emoji
    private EmojiHandler emojiHandler;

    //主类实例工厂
    @Mod.Instance(MOD_ID)
    public static McToKook INSTANCE;

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {

        logger.info("Hello Forge! Here is McToKooK");

        emojiHandler = new EmojiHandler(this);

        if (!configFolder.exists()) {
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

        if (bot_token.equals("No token provided")) {
            logger.info("你没有提供bot-token或者bot-token不正确");
            logger.info("McToKook-Mod将会停用");
            throw new Error("你没有提供bot-token或者bot-token不正确,McToKook-Mod将会停用,服务端即将崩溃");
        } else {
            if (channel_ID.equals("No channel ID provided")) {
                logger.info("你没有提供channel ID或channel ID不正确");
                logger.info("你所提供的channel_ID: " + channel_ID);
                throw new Error("你没有提供channel ID或channel ID不正确,McToKook-Mod将会停用,服务端即将崩溃");
            }
        }

        kbcClient = new KBCClient(core, config, null, bot_token);

        kbcClient.start();
        TextChannel channel = (TextChannel) kbcClient.getCore().getHttpAPI().getChannel(channel_ID);

        //注册KOOK消息监听器
        //夏夜说: 不要用InternalPlugin,但是我摆了！
        kbcClient.getCore().getEventManager().registerHandlers(kbcClient.getInternalPlugin(), new KookListener(this));
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void postinit(FMLPostInitializationEvent event) {

        MinecraftForge.EVENT_BUS.register(new OnPlayerMessage(this));
        MinecraftForge.EVENT_BUS.register(new OnPlayerJoin());
        MinecraftForge.EVENT_BUS.register(new OnPlayerQuit());
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

    public EmojiHandler getEmojiHandler(){
        return emojiHandler;
    }
}
