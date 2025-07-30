package com.madcreeper11.MCMiniGames;
import org.bukkit.plugin.java.JavaPlugin;

import com.madcreeper11.MCMiniGames.deathSwap.DeathSwap;
import com.madcreeper11.MCMiniGames.deathSwap.commands.DeathSwapGameCommand;
import com.madcreeper11.MCMiniGames.util.GameManager;

public class PluginMain extends JavaPlugin {

	private static PluginMain instance;
	private static final GameManager gameManager = new GameManager();
    private DeathSwap deathSwap;
	
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
		
        getCommand("ds").setExecutor(new DeathSwapGameCommand());

        DeathSwap deathSwap = new DeathSwap();
        getServer().getPluginManager().registerEvents(deathSwap, this);
    }

    public DeathSwap getDeathSwap() {
        return deathSwap;
    }

    @Override
    public void onDisable() {

    }
}
