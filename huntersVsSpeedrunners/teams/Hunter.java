package com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.teams;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World.Environment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CompassMeta;

public class Hunter extends CommonPlayer {

	private Speedrunner target;
	private ItemStack compass;
	private int itemSlotNumber;
	private boolean usingLodestone;

	public Hunter(UUID playerUUID) {
		super(playerUUID);

		this.itemSlotNumber = 8;
		this.target = null;
		this.compass = null;
		
		// If player starts in a dimension other than the overworld
		if (locations.getDimensionID() > 0) {
			this.usingLodestone = true;
		} else {
			this.usingLodestone = false;
		}
	}

	/**
	 * Set this Hunter's target to the closest Speedrunner
	 * @param closestSpeedrunner
	 */
	public void setTarget(Speedrunner closestSpeedrunner) {
		if(closestSpeedrunner == null) return;
		target = closestSpeedrunner;
	}

	/**
	 * First run as soon as the game is initiated; gives the Hunters their compasses
	 */
	public void setStartingCompass() {
		if(Bukkit.getPlayer(playerUUID) == null) return;		
		Player player = Bukkit.getPlayer(this.playerUUID);

		compass = new ItemStack(Material.COMPASS, 1);
		player.getInventory().setItem(itemSlotNumber, compass);
	}

	/**
	 * Manages compass update for the Hunter. If in the Nether, lodestone compass is used
	 */
	public void updateCompass() {
		if(target == null || compass == null || Bukkit.getPlayer(playerUUID) == null) return;
		// Ensure the target has a location and that dimension isn't Overworld to assign lodestone
		/*if (target.getLocationSet().getLocation(this.locations.getDimensionID()) == null && this.locations.getDimensionID() > 0) {
			setLodestone();
		}*/

		if (usingLodestone) {
			
			setLodestone();
			Bukkit.getPlayer(playerUUID).getInventory().setItem(itemSlotNumber, compass);

		} else {
			Bukkit.getPlayer(playerUUID).setCompassTarget(target.getLocationSet().getLocation(this.locations.getDimensionID()));
		}
	}
	public void setLodestone() {
		
		CompassMeta compassMeta = (CompassMeta) compass.getItemMeta();
		compassMeta.setLodestoneTracked(true);
		compassMeta.setLodestone(target.getLocationSet().getLocation(this.locations.getDimensionID()));
		compass.setItemMeta(compassMeta);
	}

	/**
	 * Changes the dimension ID for the Hunter depending on the new envionment they entered.
	 * The dimension ID dictates where to record the location
	 * @param destinationEnvionment - The environment the Hunter just entered
	 */
	public void onPlayerChangeDimension(Environment destinationEnvionment) {

		if(Bukkit.getPlayer(playerUUID) == null) return;
		int dimensionID = 0;
		usingLodestone = false;

		switch (destinationEnvionment) {
		case NORMAL:
			compass = new ItemStack(Material.COMPASS, 1);
			Bukkit.getPlayer(playerUUID).getInventory().setItem(itemSlotNumber, compass);
			break;
		case NETHER:
			dimensionID = 1;
			usingLodestone = true;
			break;
		case THE_END:
			dimensionID = 2;
			usingLodestone = true;
			break;
		default:
			break;
		}
		this.locations.setDimensionID(dimensionID);
	}
}
