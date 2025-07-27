package com.madcreeper11.MCMiniGames.randomItemPVP;
import com.madcreeper11.MCMiniGames.Minigame;
import com.madcreeper11.MCMiniGames.PluginMain;
import com.madcreeper11.MCMiniGames.randomItemPVP.commands.GameCommand;

public class RandomItemPVP extends Minigame {

	private static GameManager gameManager;
	private static GameSettings gameSettings;
	
	public RandomItemPVPGame() {
		gameManager = new GameManager();
		gameSettings = new GameSettings();
	}
	
	@Override
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
	public void loadGameSettings() {
		
	}	
	@Override
	public void saveGameSettings() {
	
	}
}
