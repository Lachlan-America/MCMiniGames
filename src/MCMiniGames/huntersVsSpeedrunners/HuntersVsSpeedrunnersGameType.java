package MCMiniGames.huntersVsSpeedrunners;

import MCMiniGames.GameType;
import MCMiniGames.PluginMain;
import MCMiniGames.huntersVsSpeedrunners.commands.GameCommand;
import MCMiniGames.huntersVsSpeedrunners.commands.TeamAssignCommand;
import MCMiniGames.huntersVsSpeedrunners.commands.TeamsCommand;

public class HuntersVsSpeedrunnersGameType implements GameType {
	
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
