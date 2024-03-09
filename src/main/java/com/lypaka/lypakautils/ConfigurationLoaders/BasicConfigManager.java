package com.lypaka.lypakautils.ConfigurationLoaders;

import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;

/**
 *
 * Used to create your more basic configuration loader system. No player-specific files, no create-as-you-go systems.
 * Builds config files stored in the project's assets directory and loads them into a configuration loader to return commented configuration nodes
 * Based on original design by landonjw of Cobblemon.
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

    public void init() {

        String currentFile = "";
        for (int i = 0; i < this.fileNames.length; i++) {

            currentFile = this.fileNames[i];
            try {

                this.config[i] = this.configPath.resolve(this.fileNames[i]);
                Path filePathString = Paths.get(this.configPath + "//" + this.fileNames[i]);
                if (!this.config[i].toFile().exists()) {

                    try {

                        Files.copy(this.mainClass.getClassLoader().getResourceAsStream("assets/" + this.modID + "/" + this.fileNames[i]), filePathString, StandardCopyOption.REPLACE_EXISTING);

                    } catch (DirectoryNotEmptyException er) {


                    }

                }

                HoconConfigurationLoader.builder().setPath(this.configPath).setFile(new File(this.fileNames[i])).build();
                config[i] = this.configPath.resolve(this.fileNames[i]);

            } catch (Exception e) {

                this.logger.error("Could not initialize file: " + currentFile + ".");

            }

        }

        load();

    }



    public void load() {

        String currentFile = "";
        try {

            for (int i = 0; i < this.fileNames.length; i++) {

                currentFile = this.fileNames[i];
                configLoad.add(i, HoconConfigurationLoader.builder().setPath(config[i]).build());
                configNode[i] = HoconConfigurationLoader.builder().setPath(config[i]).build().load();

            }

        } catch (IOException e) {

            this.logger.error(this.modName + " configuration could not load file: " + currentFile + ".");
            e.printStackTrace();

        }

    }

    public void save() {

        String currentFile = "";
        for (int i = 0; i < this.fileNames.length; i++) {

            try {

                currentFile = this.fileNames[i];
                configLoad.get(i).save(configNode[i]);

            } catch (IOException e) {

                this.logger.error("Could not save file: " + currentFile + ".");
                e.printStackTrace();

            }

        }

    }

    public CommentedConfigurationNode getConfigNode (int index, Object... node) {

        return configNode[index].getNode(node);

    }

}
