package com.lypaka.lypakautils.ConfigurationLoaders;

import org.apache.logging.log4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.loader.ConfigurationLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * Used to create a more complex configuration system, which creates files and folders on the fly
 * Uses a list of config names to create files off a base template stored in the project's assets directory
 * WARNING: This configuration loader system depends on use of the BasicConfigManager being used to generate and store a config file for the list of file names.
 * Do not call this init method without first having initialized a BasicConfigManager object!
 * Inspired by original design by landonjw of Pokemon Cobbled.
 * @author landonjw, BurstingFire, Lypaka
 *
 */
public class ComplexConfigManager {

    private List<String> fileNames;
    private final String folderName;
    private final String fileName;
    private final Path configPath;
    private final Class mainClass;
    private final String modName;
    private final String modID;
    private final Logger logger;
    private Path[] filePath;
    private final Path filesDir;
    private ArrayList<ConfigurationLoader<CommentedConfigurationNode>> configLoad;
    private CommentedConfigurationNode[] configNode;

    public ComplexConfigManager (List<String> fileNames, String folderName, String fileName, Path configPath, Class mainClass, String modName, String modID, Logger logger) {

        this.fileNames = fileNames;
        this.folderName = folderName;
        this.fileName = fileName;
        this.configPath = configPath;
        this.mainClass = mainClass;
        this.modName = modName;
        this.modID = modID;
        this.logger = logger;
        this.filePath = new Path[this.fileNames.size()];
        this.filesDir = ConfigUtils.checkDir(this.configPath.resolve(this.folderName));
        this.configLoad = new ArrayList<>(this.fileNames.size());
        this.configNode = new CommentedConfigurationNode[this.fileNames.size()];

    }

    public void init() {

        if (this.fileNames.size() > 0) {

            load();

        } else {

            this.logger.info("Detected the calling of a complex configuration loader with an empty list of config files!");

        }

    }

    public void setFileNames (List<String> names) {

        this.fileNames = names;

    }

    public void load() {

        this.filePath = new Path[this.fileNames.size()];
        this.configLoad = new ArrayList<>(this.fileNames.size());
        this.configNode = new CommentedConfigurationNode[this.fileNames.size()];

        if (this.fileNames.size() > 0) {

            for (int i = 0; i < this.fileNames.size(); i++) {

                this.filePath[i] = this.filesDir.resolve(this.fileNames.get(i));
                Path filePathString = Paths.get(this.configPath + "//" + this.folderName + "//" + this.fileNames.get(i));
                if (!this.filePath[i].toFile().exists()) {

                    try {

                        Files.copy(this.mainClass.getClassLoader().getResourceAsStream("assets/" + this.modID + "/" + this.fileName), filePathString, StandardCopyOption.REPLACE_EXISTING);

                    } catch (IOException er) {

                        this.logger.error(this.modName + " could not load file " + this.fileNames.get(i) + ".");

                    }

                }

                HoconConfigurationLoader loader = HoconConfigurationLoader.builder().path(this.filePath[i]).build();
                try {

                    this.configLoad.add(i, loader);
                    this.configNode[i] = loader.load();

                } catch (IOException e) {

                    e.printStackTrace();

                }

            }

        } else {

            this.logger.info("Detected the calling of a complex configuration loader with an empty list of config files!");

        }

    }

    public void save() {

        for (int i = 0; i < this.fileNames.size(); i++) {

            try{

                this.configLoad.get(i).save(this.configNode[i]);

            } catch (IOException e) {

                this.logger.error(this.modName + " could not save configuration file " + this.fileNames.get(i) + ".");
                e.printStackTrace();

            }

        }

    }

    public CommentedConfigurationNode getConfigNode (int index, Object... node) {

        return this.configNode[index].node(node);

    }

}
