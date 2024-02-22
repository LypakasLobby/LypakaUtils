package com.lypaka.lypakautils;


import io.leangen.geantyref.TypeToken;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Map;

public class ConfigGetters {

    public static boolean tickListenerEnabled;
    public static boolean loadPokemonTypeMap;
    public static Map<String, Map<String, String>> permissionGroups;

    public static void load() throws SerializationException {

        tickListenerEnabled = LypakaUtils.configManager.getConfigNode(0, "Enable-Tick-Listener").getBoolean();
        loadPokemonTypeMap = false;
        if (LypakaUtils.configManager.getConfigNode(0, "Load-Pokemon-Type-Map").virtual()) {

            LypakaUtils.configManager.getConfigNode(0, "Load-Pokemon-Type-Map").set(false);
            LypakaUtils.configManager.save();

        } else {

            loadPokemonTypeMap = LypakaUtils.configManager.getConfigNode(0, "Load-Pokemon-Type-Map").getBoolean();

        }
        permissionGroups = LypakaUtils.configManager.getConfigNode(1, "Groups").get(new TypeToken<Map<String, Map<String, String>>>() {});

    }

}
