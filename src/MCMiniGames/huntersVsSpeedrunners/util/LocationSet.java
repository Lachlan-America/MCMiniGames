package MCMiniGames.huntersVsSpeedrunners.util;

import org.bukkit.Location;

public class LocationSet {

	private Location locations[];
	private int dimensionID;
	
	public LocationSet() {
		this.dimensionID = 0;
		this.locations = new Location[3];
	}
	
	// Sets the current location for the current dimensionID
	public void setLocation(Location location) {
		locations[dimensionID] = location;
	}
	// Gets the current location for the current dimensionID
	public Location getLocation(int dimensionID) {
		return locations[dimensionID];
	}
	// Sets the current dimensionID
	public void setDimensionID(int dimensionID) {
		this.dimensionID = dimensionID;
	}
	// Gets the current dimensionID
	public int getDimensionID() {
		return dimensionID;
	}
}
