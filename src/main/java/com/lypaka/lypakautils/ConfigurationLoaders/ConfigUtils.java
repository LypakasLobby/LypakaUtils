package com.lypaka.lypakautils.ConfigurationLoaders;

import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ConfigUtils {

    /**
     *Used for player account configuration loader
     */
    public static Map<String, Map<UUID, Path>> playerConfig = new HashMap<>();
    public static Map<String, Map<UUID, ConfigurationLoader<CommentedConfigurationNode>>> playerConfigLoad = new HashMap<>();
    public static Map<String, Map<UUID, CommentedConfigurationNode>> playerConfigNode = new HashMap<>();

    public static Path checkDir (Path dir) {

        if (!Files.exists(dir)) {

            try {

                Files.createDirectories(dir);

            } catch (IOException e) {

                throw new RuntimeException("Error creating dir! " + dir, e);

            }

        }

        return dir;

    }

}
