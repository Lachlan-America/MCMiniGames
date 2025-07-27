package com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.handlers;

import java.util.UUID;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World.Environment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.madcreeper11.MCMiniGames.PluginMain;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.teams.Hunter;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.teams.Speedrunner;

public class TeamHandler implements Listener {

	private HashMap<UUID, Hunter> currentHunters;
	private HashMap<UUID, Speedrunner> currentSpeedrunners;
	private boolean gameFlag;

	public TeamHandler() {
		Bukkit.getPluginManager().registerEvents(this, PluginMain.getInstance());
		this.currentHunters = new HashMap<UUID, Hunter>();
		this.currentSpeedrunners = new HashMap<UUID, Speedrunner>();
		this.gameFlag = false;
	}

	/**
	 * The crux of the whole game: set each Hunter's closest target
	 */
	public void updateHunterTargets() {

		// For each hunter that exists in the hunters team get their current dimension ID
		for (Hunter hunter : currentHunters.values()) {
			int hunterDimensionID = hunter.getLocationSet().getDimensionID();

			Speedrunner closestToHunter = null;
			double shortestDistance = 0;
			// Many-to-one mapping means we find the closest speedrunner to an individual
			// hunter by obtaining the shortest distance between the
			// hunter and the speedrunners that are currently located in the same dimension
			// or have previously been there.
			for (Speedrunner speedrunner : currentSpeedrunners.values()) {
				// Ensure we do not get NullPointerException
				if (hunter.getLocationSet().getLocation(hunterDimensionID) != null) {
					if (speedrunner.getLocationSet().getLocation(hunterDimensionID) != null) {
						/*Bukkit.broadcastMessage(hunter.getLocationSet().getLocation(hunterDimensionID).getWorld().getName() + "\n" +
						speedrunner.getLocationSet().getLocation(hunterDimensionID).getWorld().getName());*/
						
						double distance = hunter.getLocationSet().getLocation(hunterDimensionID)
								.distance(speedrunner.getLocationSet().getLocation(hunterDimensionID));
						// set the current shortest distance and the speedrunner that had that distance
						// as the Hunter's target
						if (closestToHunter == null || distance < shortestDistance) {
							closestToHunter = speedrunner;
							shortestDistance = distance;
						}
					}
				}
			}
			// If the speedrunner isnt online, DONT update values
			hunter.setTarget(closestToHunter);
			hunter.updateCompass();
		}
	}

	/**
	 * Updates the player's location set - dimension dependent
	 * 
	 * @param event - The event parameter used to dictate who recently moved
	 *              locations
	 */
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		// Ensure the game has started before assigning a new compass
		if (!HuntersVsSpeedrunnersGameType.getGameSettings().getGameState())
			return;
		UUID playerUUID = event.getPlayer().getUniqueId();

		// Check to see if the player who moved is a hunter or speedrunner and react
		// accordingly
		for (UUID hunterUUID : currentHunters.keySet()) {
			if (hunterUUID.equals(playerUUID)) {
				Hunter hunter = currentHunters.get(playerUUID);
				hunter.setLocation(event.getPlayer().getLocation());
				if (HuntersVsSpeedrunnersGameType.getGameSettings().getCountdownTime() > 0) {
					event.setCancelled(true);
				}
				return;
			}
		}
		for (UUID speedrunnerUUID : currentSpeedrunners.keySet()) {
			if (speedrunnerUUID.equals(playerUUID)) {
				Speedrunner speedrunner = currentSpeedrunners.get(playerUUID);
				speedrunner.setLocation(event.getPlayer().getLocation());
				return;
			}
		}
	}

	/**
	 * Gives the player a new compass when the player respawns as a Hunter
	 * 
	 * @param event - The event parameter used to dictate who recently respawned
	 */
	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		// Ensure the game has started before assigning a new compass
		if (!HuntersVsSpeedrunnersGameType.getGameSettings().getGameState())
			return;
		currentHunters.get(event.getPlayer().getUniqueId()).setStartingCompass();
	}

	/**
	 * Changes the dimension ID (the current dimension's location to record) for
	 * either team member
	 * 
	 * @param event - The event parameter used to dictate who recently teleported
	 */
	@EventHandler
	public void onPlayerChangeDimension(PlayerTeleportEvent event) {
		// Ensure the game has started before doing anything
		if (!HuntersVsSpeedrunnersGameType.getGameSettings().getGameState())
			return;
		UUID playerUUID = event.getPlayer().getUniqueId();

		// Get if the player who triggered the event is a hunter or speedrunner and
		// react accordingly
		for (UUID hunterUUID : currentHunters.keySet()) {
			if (hunterUUID.equals(playerUUID)) {
				Hunter hunter = currentHunters.get(playerUUID);
				hunter.onPlayerChangeDimension(event.getTo().getWorld().getEnvironment());
				return;
			}
		}
		for (UUID speedrunnerUUID : currentSpeedrunners.keySet()) {
			if (speedrunnerUUID.equals(playerUUID)) {
				Speedrunner speedrunner = currentSpeedrunners.get(playerUUID);
				speedrunner.onPlayerChangeDimension(event.getTo().getWorld().getEnvironment());
				return;
			}
		}
	}

	/**
	 * Create a new Hunter object and assign it to the HashMap if the player was
	 * previously a Speedrunner
	 * 
	 * @param event - The event parameter used to dictate who was killed
	 */
	@EventHandler
	public void onPlayerKilled(PlayerDeathEvent event) {
		// Ensure the game has started before assigning a new compass
		if (!HuntersVsSpeedrunnersGameType.getGameSettings().getGameState())
			return;

		// If the latest death is a speedrunner then swap their team
		UUID playerUUID = event.getEntity().getPlayer().getUniqueId();
		for (UUID speedrunnerUUID : currentSpeedrunners.keySet()) {
			if (speedrunnerUUID.equals(playerUUID)) {
				createHunter(playerUUID);
			}
		}

		for (UUID hunterUUID : currentHunters.keySet()) {
			if (hunterUUID.equals(playerUUID)) {
				Hunter hunter = currentHunters.get(playerUUID);
				hunter.onPlayerChangeDimension(Environment.NORMAL);
			}
		}
		// TODO: make gameflag reset on new game
		if (currentSpeedrunners.isEmpty() && !gameFlag) {
			Bukkit.broadcastMessage(ChatColor.RED + "Hunters win!");
			
			gameFlag = true;
		}
	}

	// USE UUIDS TO CHECK NULLS!
	public void createHunter(UUID playerUUID) {
		if (playerUUID == null)
			return;
		if (currentSpeedrunners.keySet().contains(playerUUID))
			currentSpeedrunners.remove(playerUUID);

		Hunter hunter = new Hunter(playerUUID);

		currentHunters.putIfAbsent(playerUUID, hunter);
		hunter.setStartingCompass();
	}

	public void createSpeedrunner(UUID playerUUID) {
		if (playerUUID == null)
			return;
		if (currentHunters.keySet().contains(playerUUID))
			currentHunters.remove(playerUUID);

		currentSpeedrunners.putIfAbsent(playerUUID, new Speedrunner(playerUUID));
	}

	public void removeHunter(UUID playerUUID) {
		if (playerUUID == null)
			return;
		if (currentHunters.keySet().contains(playerUUID))
			currentHunters.remove(playerUUID);
	}

	public void removeSpeedrunner(UUID playerUUID) {
		if (playerUUID == null)
			return;
		if (currentSpeedrunners.keySet().contains(playerUUID))
			currentSpeedrunners.remove(playerUUID);
	}

	public HashMap<UUID, Hunter> getHunters() {
		return currentHunters;
	}

	public HashMap<UUID, Speedrunner> getSpeedrunners() {
		return currentSpeedrunners;
	}

	/**
	 * Gives Hunters their compasses when the game initiates
	 */
	public void setHunterCompasses() {

		for (Hunter hunter : currentHunters.values()) {
			hunter.setStartingCompass();
		}
	}

	/**
	 * Creates new Hunter and Speedrunner objects for each existing team member
	 */
	public void resetTeams() {

		HuntersVsSpeedrunnersGameType.getGameSettings().setGameState(false);

		HashMap<UUID, Hunter> huntersTemp = new HashMap<UUID, Hunter>();
		HashMap<UUID, Speedrunner> speedrunnersTemp = new HashMap<UUID, Speedrunner>();

		for (UUID playerUUID : currentHunters.keySet()) {

			Hunter hunterTemp = new Hunter(playerUUID);
			huntersTemp.putIfAbsent(playerUUID, hunterTemp);
		}

		for (UUID playerUUID : currentSpeedrunners.keySet()) {

			Speedrunner speedRunnerTemp = new Speedrunner(playerUUID);
			speedrunnersTemp.putIfAbsent(playerUUID, speedRunnerTemp);
		}

		// Assign temporary new HashMaps to the global ones
		currentHunters = huntersTemp;
		currentSpeedrunners = speedrunnersTemp;
	}
}
