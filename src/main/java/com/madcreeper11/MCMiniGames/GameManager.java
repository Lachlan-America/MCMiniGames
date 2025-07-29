package com.madcreeper11.MCMiniGames;

// import java.io.File;

// import org.bukkit.configuration.file.FileConfiguration;
// import org.bukkit.configuration.file.YamlConfiguration;

public class GameManager {
    private Minigame currentGame;

    public void switchGame(Minigame newGame) {
        if (currentGame != null) currentGame.stop();
        currentGame = newGame;
        currentGame.start();
    }

    public void stopGame() {
        if (currentGame != null) {
            currentGame.stop();
            currentGame = null;
        }
    }

    public Minigame getCurrentGame() {
        return currentGame;
    }

    // public FileConfiguration loadGameConfig(String gameName) {
    //     File configFile = new File(getDataFolder(), "DeathSwap.yml");

    //     if (!configFile.exists()) {
    //         // Create parent directory if needed
    //         getDataFolder().mkdirs();

    //         try {
    //     configFile.createNewFile();
    //     File file = new File(PluginMain.getInstance().getDataFolder(), gameName + ".yml");
    //     if (!file.exists()) PluginMain.getInstance().saveResource(gameName + ".yml", false);
    //     return YamlConfiguration.loadConfiguration(file);
    // }
}