package com.madcreeper11.MCMiniGames.huntersVsSpeedrunners;

import com.madcreeper11.MCMiniGames.Minigame;
import com.madcreeper11.MCMiniGames.PluginMain;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.commands.GameCommand;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.commands.TeamAssignCommand;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.commands.TeamsCommand;

public class HuntersVsSpeedrunners extends Minigame {
	
	private static GameManager gameManager;
	private static GameSettings gameSettings;
	
	public HuntersVsSpeedrunnersGameType() {
		gameManager = new GameManager();
		gameSettings = new GameSettings();
	}
	
	@Override
	public void init() {
		
        PluginMain.getInstance().getCommand("hvs").setExecutor(new GameCommand());
        PluginMain.getInstance().getCommand("teams").setExecutor(new TeamsCommand());
        PluginMain.getInstance().getCommand("team").setExecutor(new TeamAssignCommand()); 
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
