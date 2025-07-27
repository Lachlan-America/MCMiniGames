package MCMiniGames.huntersVsSpeedrunners.teams;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World.Environment;

import MCMiniGames.huntersVsSpeedrunners.util.LocationSet;

public abstract class CommonPlayer {

	protected LocationSet locations;
	protected UUID playerUUID;
	
	public CommonPlayer(UUID playerUUID) {
		this.playerUUID = playerUUID;
		this.locations = new LocationSet();
		initializePlayerLocation();
	}
	
	public UUID getPlayerUUID() {
		return playerUUID;
	}
	
	public void setLocation(Location location) {
		if(location == null) return;
		locations.setLocation(location);	
	}
	
	public LocationSet getLocationSet() {
		return locations;
	}

	private void initializePlayerLocation() {
		
		if(Bukkit.getPlayer(playerUUID) == null) {
			return;
		}
		Environment dimensionEnvironment = Bukkit.getPlayer(playerUUID).getWorld().getEnvironment();
		int dimensionID = 0;
		
		switch (dimensionEnvironment) {
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
		this.locations.setLocation(Bukkit.getPlayer(playerUUID).getLocation());
	}
}
