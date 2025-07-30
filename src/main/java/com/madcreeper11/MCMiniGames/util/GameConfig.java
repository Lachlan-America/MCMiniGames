package com.madcreeper11.MCMiniGames.util;
import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;

import com.madcreeper11.MCMiniGames.PluginMain;

public abstract class GameConfig {
    protected YamlConfiguration config;
    protected File configFile;

    public GameConfig(String fileName) {
        this.configFile = new File(PluginMain.getInstance().getDataFolder(), fileName);
        if (!configFile.exists()) {
            PluginMain.getInstance().getDataFolder().mkdirs();

            config = new YamlConfiguration();
            setDefaults();
            save();
            return;
        }
        this.config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public abstract void setDefaults();
    public abstract void loadValues();
}
