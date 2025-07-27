package MCMiniGames.huntersVsSpeedrunners;

import org.bukkit.configuration.file.FileConfiguration;

import MCMiniGames.PluginMain;

public class GameSettings {

	private boolean gameRunning;
	private int countdownTime;
	
	// Default settings for the game type
	public GameSettings() {
		
		this.gameRunning = false;
		this.countdownTime = 30;	
	}
	
	public void loadSettings() {
		FileConfiguration config = PluginMain.getInstance().getConfig();
		
		countdownTime = config.getInt("countdownTime");
		gameRunning = config.getBoolean("gameRunning");
	}
	
	public void saveSettings() {
		FileConfiguration config = PluginMain.getInstance().getConfig();
		
		config.set("countdownTime", countdownTime);
		config.set("gameRunning", gameRunning);
	}
	
	public boolean getGameState() {
		return gameRunning;
	}

	public void setGameState(boolean setRunning) {
		gameRunning = setRunning;
	}

	public int getCountdownTime() {
		return countdownTime;
	}

	public void setCountdownTime(int countdownTime) {
		this.countdownTime = countdownTime;
	}
}
