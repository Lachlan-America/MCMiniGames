package com.madcreeper11.MCMiniGames.randomItemPVP;
import org.bukkit.configuration.file.FileConfiguration;

import com.madcreeper11.MCMiniGames.Minigame;
import com.madcreeper11.MCMiniGames.PluginMain;
import com.madcreeper11.MCMiniGames.randomItemPVP.commands.GameCommand;

public class RandomItemPVP implements Minigame {

	private static GameManager gameManager;
	private static GameSettings gameSettings;
	
	public RandomItemPVP() {
		gameManager = new GameManager();
		gameSettings = new GameSettings();
	}

	public void init() {
		 PluginMain.getInstance().getCommand("randomItemPVP").setExecutor(new GameCommand());
	}

	public static GameManager getGameManager() {
		return gameManager;
	}
	
	public static GameSettings getGameSettings() {
		return gameSettings;
	}
	
	@Override
	public void start(FileConfiguration config) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'start'");
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'stop'");
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getName'");
	}
}
