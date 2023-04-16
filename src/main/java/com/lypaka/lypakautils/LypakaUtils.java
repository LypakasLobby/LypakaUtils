package com.lypaka.lypakautils;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(LypakaUtils.MOD_ID)
public class LypakaUtils {

    public static final String VERSION = "1.19.2-0.0.9";
    public static final String MOD_ID = "lypakautils";
    public static final String MOD_NAME = "LypakaUtils";
    public static final Logger logger = LogManager.getLogger(MOD_NAME);

    public LypakaUtils() {

        logger.info("Loading LypakaUtils version: " + VERSION);
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.addListener(LypakaUtils::onServerStarted);

    }

    public static void onServerStarted (ServerStartedEvent event) {

        WorldMap.load();

    }

}
