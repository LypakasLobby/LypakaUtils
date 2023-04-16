package com.lypaka.lypakautils.ConfigurationLoaders;

import org.apache.logging.log4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

/**
 *
 * Used to create your more basic configuration loader system. No player-specific files, no create-as-you-go systems.
 * Builds config files stored in the project's assets directory and loads them into a configuration loader to return commented configuration nodes
 * Based on original design by landonjw of Pokemon Cobbled.
 * @author landonjw, Lypaka
 *
 */
public class BasicConfigManager {

    private final String[] fileNames;
    private final Path configPath;
    private final Class mainClass;
    private final String modName;
    private final String modID;
    private final Logger logger;
    private final Path[] config;
    private final ArrayList<ConfigurationLoader<CommentedConfigurationNode>> configLoad;
    private final CommentedConfigurationNode[] configNode;

    public BasicConfigManager (String[] fileNames, Path configPath, Class mainClass, String modName, String modID, Logger logger) {

        this.fileNames = fileNames;
        this.configPath = configPath;
        this.mainClass = mainClass;
        this.modName = modName;
        this.modID = modID;
        this.logger = logger;

        this.config = new Path[this.fileNames.length];
        this.configLoad = new ArrayList<ConfigurationLoader<CommentedConfigurationNode>>(this.fileNames.length);
        this.configNode = new CommentedConfigurationNode[this.fileNames.length];

    }

    public void init() throws IOException {

        for (int i = 0; i < this.fileNames.length; i++) {

            this.config[i] = this.configPath.resolve(this.fileNames[i]);
            Path filePathString = Paths.get(this.configPath + "//" + this.fileNames[i]);
            if (!this.config[i].toFile().exists()) {

                try {

                    Files.copy(this.mainClass.getClassLoader().getResourceAsStream("assets/" + this.modID + "/" + this.fileNames[i]), filePathString, StandardCopyOption.REPLACE_EXISTING);

                } catch (DirectoryNotEmptyException er) {


                }

            }

            HoconConfigurationLoader.builder().path(this.configPath).file(new File(this.fileNames[i])).build();
            config[i] = this.configPath.resolve(this.fileNames[i]);

        }

        load();

    }



    public void load() {

        try {

            for (int i = 0; i < this.fileNames.length; i++) {

                configLoad.add(i, HoconConfigurationLoader.builder().path(config[i]).build());
                configNode[i] = HoconConfigurationLoader.builder().path(config[i]).build().load();

            }

        } catch (IOException e) {

            this.logger.error(this.modName + " configuration could not load.");
            e.printStackTrace();

        }

    }

    public void save() {

        for (int i = 0; i < this.fileNames.length; i++) {

            try {

                configLoad.get(i).save(configNode[i]);

            } catch (IOException e) {

                e.printStackTrace();

            }

        }

    }

    public CommentedConfigurationNode getConfigNode (int index, Object... node) {

        return configNode[index].node(node);

    }

}
