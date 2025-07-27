package MCMiniGames.deathSwap;

import MCMiniGames.GameType;
import MCMiniGames.PluginMain;
import MCMiniGames.deathSwap.commands.DeathSwapGameCommand;

public class DeathSwapGame implements GameType {
	
	private static DeathSwapGameManager gameManager;
	private static DeathSwapGameSettings gameSettings;
	
	public DeathSwapGame() {
		gameSettings = new DeathSwapGameSettings();	
		gameManager = new DeathSwapGameManager();
	}
	
	@Override
	public void init() {
		
        PluginMain.getInstance().getCommand("ds").setExecutor(new DeathSwapGameCommand());
	}

	public static DeathSwapGameManager getGameManager() {
		return gameManager;
	}
	
	public static DeathSwapGameSettings getGameSettings() {
		return gameSettings;
	}
	
	@Override
	public void loadGameSettings() {
		
	}
	@Override
	public void saveGameSettings() {
		
	}
}
