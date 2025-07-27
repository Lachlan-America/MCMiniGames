package com.madcreeper11.MCMiniGames;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {

	private static PluginMain instance;
	private static final GameManager gameManager = new GameManager();
	
	// Getter for the main plugin
    public static PluginMain getInstance() {
    	return instance;
    }
	public static GameManager getGameManager() {
		return gameManager;
	}
	
    @Override
    public void onEnable() {
		instance = this;
    }
    @Override
    public void onDisable() {

    }
}
