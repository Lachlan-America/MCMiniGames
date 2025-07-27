package com.madcreeper11.MCMiniGames;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin {

	private static PluginMain instance;
	private GameTypeRegistry gameTypeRegistry;
	
	public PluginMain() {		
		// Singleton pattern to ensure only one instance of the plugin exists
		if(instance == null) {
			instance = this;
		}
	}
	
	// Getter for the main plugin
    public static PluginMain getInstance() {
    	return instance;
    }

    @Override
    public void onEnable() {
    	this.getConfig().options().copyDefaults(true);
		this.gameTypeRegistry = new GameTypeRegistry();
    }
    
    @Override
    public void onDisable() {
    	//TODO: Save settings for individual game types
    }
}
