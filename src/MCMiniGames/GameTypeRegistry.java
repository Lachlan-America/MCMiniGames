package MCMiniGames;

import MCMiniGames.deathSwap.DeathSwapGame;
import MCMiniGames.huntersVsSpeedrunners.HuntersVsSpeedrunnersGameType;
import MCMiniGames.randomItemPVP.RandomItemPVPGame;

public class GameTypeRegistry {
	
	private HuntersVsSpeedrunnersGameType huntersVsSpeedrunners;
	private RandomItemPVPGame randomItemPVP;
	private DeathSwapGame deathswap;
	
	// Register all different game types
	public GameTypeRegistry() {
		
		huntersVsSpeedrunners = new HuntersVsSpeedrunnersGameType();
		randomItemPVP = new RandomItemPVPGame();
		deathswap = new DeathSwapGame();
		
		huntersVsSpeedrunners.init();
		randomItemPVP.init();
		deathswap.init();
	}
}
