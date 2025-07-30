package com.madcreeper11.MCMiniGames.deathSwap;

import com.madcreeper11.MCMiniGames.PluginMain;
import com.madcreeper11.MCMiniGames.util.GameConfig;
import com.madcreeper11.MCMiniGames.util.Minigame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathSwap extends GameConfig implements Minigame, Listener {
	private BukkitTask gameTask;
	
	// Time in seconds
	private int minSwapTime;
	private int maxSwapTime;
	private int warningTime;
	private int countdownTime;
	private Random random;
	private List<UUID> participants = new ArrayList<UUID>();
	private List<UUID> aliveUUIDs = new ArrayList<UUID>();

	public DeathSwap() {
		// Generates config file if it doesn't exist
		// This will be used to load game settings
        super("DeathSwap.yml");
    }

	@Override
	public void setDefaults() {
		config.set("minSwapTime", 180);
		config.set("maxSwapTime", 300);
		config.set("warningTime", 15);
	}

	@Override
	public void loadValues() {
		minSwapTime = config.getInt("minSwapTime");
		maxSwapTime = config.getInt("maxSwapTime");
		warningTime = config.getInt("warningTime");
	}

	public void init() {
		loadValues();
		random = new Random();
		
		participants.clear();
		aliveUUIDs.clear();

		for (Player player : Bukkit.getOnlinePlayers()) {
			UUID id = player.getUniqueId();
			participants.add(id);
			aliveUUIDs.add(id);
			Bukkit.broadcastMessage("§c[DeathSwap] §7" + player.getName() + " has joined the game!");
		}

		Bukkit.broadcastMessage("§c[DeathSwap] §7Game is starting with swap time between " + (minSwapTime / 60) + " and " + (maxSwapTime / 60) + " minutes.");
		countdownTime = minSwapTime + random.nextInt(maxSwapTime - minSwapTime + 1);
		Bukkit.broadcastMessage("§cDEBUG: [DeathSwap] §7Countdown chosen to be: " + Math.floor(countdownTime / 60) + " minutes and " + (countdownTime % 60) + " seconds.");
	}

    @Override
    public void start() {
		init();
		stop();

		Bukkit.getLogger().info("Starting task");
        gameTask = new BukkitRunnable() {
            @Override
            public void run() {
				if (countdownTime <= warningTime) {
					Bukkit.broadcastMessage("§c[DeathSwap] §7Warning: Swapping in " + countdownTime + " seconds!");
				}

                if (countdownTime <= 0) {
                    // Perform the swap
                    Bukkit.broadcastMessage("§c[DeathSwap] §7Swapping players now!");
                    swapPlayers();
                    countdownTime = minSwapTime + random.nextInt(maxSwapTime - minSwapTime + 1);
                }
				
				countdownTime--;
            }
        }.runTaskTimer(PluginMain.getInstance(), 0L, 20L);
    }

	private void swapPlayers() {
		// Logic to swap players goes here
		List<Location> locations = aliveUUIDs.stream()
			.map(uuid -> Bukkit.getPlayer(uuid))
			.filter(Objects::nonNull)
			.map(Player::getLocation)
			.collect(Collectors.toList());
			
		Collections.rotate(locations, 1);

		int i = 0;
		for (UUID uuid : aliveUUIDs) {
			Player player = Bukkit.getPlayer(uuid);
			if (player != null && player.isOnline()) {
				player.teleport(locations.get(i++));
			}
		}
	}

	@Override
    public void stop() {
        if (gameTask != null) {
		    Bukkit.getLogger().info("Cancelling task");
			gameTask.cancel();
		} else {
			Bukkit.getLogger().info("No task to cancel");
		}
    }

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		UUID id = event.getEntity().getUniqueId();
		aliveUUIDs.remove(id);
		Bukkit.broadcastMessage("§c[DeathSwap] §7" + event.getEntity().getName() + " has died!");
		if (aliveUUIDs.size() <= 1) {
			// Moves to the next person in the sequence (last alive player)
			UUID winner = aliveUUIDs.isEmpty() ? null : aliveUUIDs.iterator().next();
			if (winner != null) {
				Bukkit.broadcastMessage("§c[DeathSwap] §7" + Bukkit.getPlayer(winner).getName() + " is the winner!");
			} else {
				Bukkit.broadcastMessage("§c[DeathSwap] §7No players left, game over!");
			}
			stop();
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (!aliveUUIDs.contains(event.getPlayer().getUniqueId())) {
			event.getPlayer().setGameMode(GameMode.SPECTATOR);
		}
	}
}
