package MCMiniGames.randomItemPVP;
import MCMiniGames.GameType;
import MCMiniGames.PluginMain;
import MCMiniGames.randomItemPVP.commands.GameCommand;

public class RandomItemPVPGame implements GameType {

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
