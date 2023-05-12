package com.xiaoace.mctokook;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(McToKook.MOD_ID)
public class McToKook {

    public static final String MOD_ID = "mctokook";

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger("McToKook");

    private void setup(final FMLCommonSetupEvent event) {

        LOGGER.info("Hello Forge! Here is McToKooK");

    }

}
