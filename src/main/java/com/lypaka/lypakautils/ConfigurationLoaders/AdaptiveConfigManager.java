package com.lypaka.lypakautils.ConfigurationLoaders;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.apache.logging.log4j.Logger;
//import org.spongepowered.configurate.CommentedConfigurationNode;
//import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
//import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

/**
 *
 * Used to create essentially an "in-between" configuration loader between the BasicConfigManager and ComplexConfigManager
 * Creates configuration files from a template file like the ComplexConfigManager but is not stored as a list like the ComplexConfigManager
 * Able to be gotten individually like the BasicConfigManager
 * Has only one config file, one index
 * Based on original design by landonjw of Cobblemon.
 * @author landonjw, Lypaka
 *
 */
public class AdaptiveConfigManager {

    private final String fileName;
    private final String templateFile;
    private final Path configPath;
    private final Class mainClass;
    private final String modName;
    private final String modID;
    private final Logger logger;
    private final Path[] config;
    private final ArrayList<ConfigurationLoader<CommentedConfigurationNode>> configLoad;
    private final CommentedConfigurationNode[] configNode;

    public AdaptiveConfigManager (String fileName, String templateFile, Path configPath, Class mainClass, String modName, String modID, Logger logger) {

        this.fileName = fileName;
        this.templateFile = templateFile;
        this.configPath = configPath;
        this.mainClass = mainClass;
        this.modName = modName;
        this.modID = modID;
        this.logger = logger;

        this.config = new Path[1];
        this.configLoad = new ArrayList<ConfigurationLoader<CommentedConfigurationNode>>(1);
        this.configNode = new CommentedConfigurationNode[1];

    }

    public void init() throws IOException {

        this.config[0] = this.configPath.resolve(this.fileName);
        Path filePathString = Paths.get(this.configPath + "//" + this.fileName);
        if (!this.config[0].toFile().exists()) {

            try {

                Files.copy(this.mainClass.getClassLoader().getResourceAsStream("assets/" + this.modID + "/" + this.templateFile), filePathString, StandardCopyOption.REPLACE_EXISTING);

            } catch (DirectoryNotEmptyException er) {


            }

        }

        HoconConfigurationLoader.builder().setPath(this.configPath).setFile(new File(this.fileName)).build();
        config[0] = this.configPath.resolve(this.fileName);

        load();

    }



    public void load() {

        try {

            configLoad.add(0, HoconConfigurationLoader.builder().setPath(config[0]).build());
            configNode[0] = HoconConfigurationLoader.builder().setPath(config[0]).build().load();

        } catch (IOException e) {

            this.logger.error(this.modName + " configuration could not load.");
            e.printStackTrace();

        }

    }

    public void save() {

        try {

            configLoad.get(0).save(configNode[0]);

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    public CommentedConfigurationNode getConfigNode (Object... node) {

        return configNode[0].getNode(node);

    }

}
