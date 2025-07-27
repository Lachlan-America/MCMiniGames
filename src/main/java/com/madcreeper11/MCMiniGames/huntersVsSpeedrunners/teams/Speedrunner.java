package MCMiniGames.huntersVsSpeedrunners.teams;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World.Environment;

public class Speedrunner extends CommonPlayer {
	
	public Speedrunner(UUID playerUUID) {
		super(playerUUID);
	}

	/**
	 * Changes the dimension ID for the Speedrunner depending on the new envionment they entered.
	 * The dimension ID dictates where to record the location
	 * @param destinationEnvionment - The environment the Speedrunner just entered
	 */
	public void onPlayerChangeDimension(Environment destinationEnvionment) {

		if(Bukkit.getPlayer(playerUUID) == null) return;
		int dimensionID = 0;

		switch (destinationEnvionment) {
		case NORMAL:
			break;
		case NETHER:
			dimensionID = 1;
			break;
		case THE_END:
			dimensionID = 2;
			break;
		default:
			break;
		}
		
		this.locations.setDimensionID(dimensionID);
	}
}
