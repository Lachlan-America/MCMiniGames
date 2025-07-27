package com.madcreeper11.MCMiniGames;

import com.madcreeper11.MCMiniGames.deathSwap.DeathSwapGame;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.HuntersVsSpeedrunnersGameType;
import com.madcreeper11.MCMiniGames.randomItemPVP.RandomItemPVPGame;

// All of our mini games are registered here
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
