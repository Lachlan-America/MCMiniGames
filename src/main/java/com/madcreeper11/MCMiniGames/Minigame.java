package com.madcreeper11.MCMiniGames;

import org.bukkit.configuration.file.FileConfiguration;

public interface Minigame {
    void start();
    void stop();
    String getName();
}
