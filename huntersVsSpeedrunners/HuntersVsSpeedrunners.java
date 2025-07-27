package com.madcreeper11.MCMiniGames.huntersVsSpeedrunners;

import org.bukkit.configuration.file.FileConfiguration;

import com.madcreeper11.MCMiniGames.Minigame;
import com.madcreeper11.MCMiniGames.PluginMain;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.commands.GameCommand;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.commands.TeamAssignCommand;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.commands.TeamsCommand;

public class HuntersVsSpeedrunners implements Minigame {
	
	private static GameManager gameManager;
	private static GameSettings gameSettings;
	
	public HuntersVsSpeedrunners() {
		gameManager = new GameManager();
		gameSettings = new GameSettings();
	}
	
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

