package com.madcreeper11.MCMiniGames.huntersVsSpeedrunners;

import java.util.HashSet;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitTask;

import com.madcreeper11.MCMiniGames.PluginMain;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.handlers.TeamHandler;
import com.madcreeper11.MCMiniGames.huntersVsSpeedrunners.tasks.TimerTask;

public class GameManager implements Listener {

	private TeamHandler teamHandler;
	private static BukkitTask timerTask;

	// List of all the unsafe blocks
	public static HashSet<Material> unsafeBlocks = new HashSet<>();
	static {
		unsafeBlocks.add(Material.LAVA);
		unsafeBlocks.add(Material.FIRE);
		unsafeBlocks.add(Material.CACTUS);
		unsafeBlocks.add(Material.WATER);
	}

	public GameManager() {
		Bukkit.getPluginManager().registerEvents(this, PluginMain.getInstance());
		this.teamHandler = new TeamHandler();

		Update();
	}

	public TeamHandler getTeamHandler() {
		return teamHandler;
	}
	
	// The main game loop for the specific gametype
	public void Update() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMain.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (!HuntersVsSpeedrunnersGameType.getGameSettings().getGameState())
					return;
				teamHandler.updateHunterTargets();
			}

		}, 0, 1L);
	}
	@EventHandler
	public void playerDamagePlayerEvent(EntityDamageByEntityEvent event) {
		if (HuntersVsSpeedrunnersGameType.getGameSettings().getCountdownTime() > 0
				&& HuntersVsSpeedrunnersGameType.getGameSettings().getGameState()) {
			if (!(event.getEntity() instanceof Player))
				return;
			event.setCancelled(true);
		}
	}

	public void startingSequence(int countdownTime) {
		// TODO: improve this
		double areaRadius = 5;
		double minRadius = 1;
		double t, radius, x, z, y;
		Location loc = null;

		World world = Bukkit.getWorlds().get(0);
		Location worldSpawnLocation = world.getSpawnLocation();
		for (Player player : Bukkit.getOnlinePlayers()) {

			t = Math.random() * Math.PI;
			radius = Math.random() * (areaRadius - minRadius) + minRadius;
			x = worldSpawnLocation.getX() + Math.cos(t) * radius;
			z = worldSpawnLocation.getZ() + Math.sin(t) * radius;

			loc = new Location(world, x, 0, z);
			y = world.getHighestBlockYAt(loc);
			loc = new Location(world, x, y + 1, z);

			while (!isLocationSafe(loc)) {
				// Keep looking for a safe location
				t = Math.random() * Math.PI;
				radius = Math.random() * (areaRadius - minRadius) + minRadius;
				x = worldSpawnLocation.getX() + Math.cos(t) * radius;
				z = worldSpawnLocation.getZ() + Math.sin(t) * radius;
				loc = new Location(world, x, 0, z);
				y = world.getHighestBlockYAt(loc);
				loc = new Location(world, x, y + 1, z);
			}
			
			player.teleport(loc);
		}
		
		if(timerTask != null) timerTask.cancel();
		timerTask = new TimerTask(countdownTime).runTaskTimer(PluginMain.getInstance(), 0, 20);
	}
	public static boolean isLocationSafe(Location location) {

		int x = location.getBlockX();
		int y = location.getBlockY();
		int z = location.getBlockZ();
		// Get instances of the blocks around where the player would spawn
		Block block = location.getWorld().getBlockAt(x, y, z);
		Block below = location.getWorld().getBlockAt(x, y - 1, z);
		Block above = location.getWorld().getBlockAt(x, y + 1, z);

		// Check to see if the surroundings are safe or not
		return !(unsafeBlocks.contains(below.getType())) || (block.getType().isSolid()) || (above.getType().isSolid());
	}
}
