package com.lypaka.lypakautils;

import com.lypaka.lypakautils.ConfigurationLoaders.BasicConfigManager;
import com.lypaka.lypakautils.ConfigurationLoaders.ConfigUtils;
import com.lypaka.lypakautils.ConfigurationLoaders.PlayerConfigManager;
import com.lypaka.lypakautils.Listeners.TickListener;
import com.lypaka.lypakautils.WorldStuff.WorldMap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod(LypakaUtils.MOD_ID)
public class LypakaUtils {

    public static final String MOD_ID = "lypakautils";
    public static final String MOD_NAME = "LypakaUtils";
    public static final Logger logger = LogManager.getLogger("LypakaUtils");
    public static BasicConfigManager configManager;
    public static PlayerConfigManager playerConfigManager;
    public static Map<UUID, LPPlayer> playerMap = new HashMap<>();

    public LypakaUtils() throws IOException, ObjectMappingException {

        logger.info("Loading LypakaUtils");
        MinecraftForge.EVENT_BUS.register(this);
        Path dir = ConfigUtils.checkDir(Paths.get("./config/lypakautils"));
        String[] files = new String[]{"lypakautils.conf", "permission-groups.conf"};
        configManager = new BasicConfigManager(files, dir, LypakaUtils.class, MOD_NAME, MOD_ID, logger);
        configManager.init();
        playerConfigManager = new PlayerConfigManager("account.conf", "accounts", dir, LypakaUtils.class, MOD_NAME, MOD_ID, logger);
        playerConfigManager.init();
        ConfigGetters.load();
        MinecraftForge.EVENT_BUS.addListener(LypakaUtils::onServerStarted);

    }

    public static void onServerStarted (FMLServerStartingEvent event) {

        WorldMap.load();
        if (ConfigGetters.tickListenerEnabled) {

            MinecraftForge.EVENT_BUS.register(new TickListener());

        }

    }

}
