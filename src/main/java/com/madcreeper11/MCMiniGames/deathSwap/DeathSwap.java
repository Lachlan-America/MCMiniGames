package com.madcreeper11.MCMiniGames.deathSwap;

import com.madcreeper11.MCMiniGames.Minigame;
import com.madcreeper11.MCMiniGames.PluginMain;

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
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class DeathSwap implements Minigame, Listener {
	private BukkitRunnable gameTask;
	
	// Time in seconds
	private int minSwapTime;
	private int maxSwapTime;
	private int warningTime;
	private int countdownTime;
	private Random random;
	private List<UUID> participants = new ArrayList<UUID>();
	private List<UUID> aliveUUIDs = new ArrayList<UUID>();

	public void init(FileConfiguration config) {
		// Load game settings from config
		minSwapTime = config.getInt("minSwapTime", 180); // Default to 3 minutes
		maxSwapTime = config.getInt("maxSwapTime", 300); // Default to 5 minutes
		warningTime = config.getInt("warningTime", 15); // Default to 15 seconds
		random = new Random();
		
		participants.clear();
		aliveUUIDs.clear();

		for (Player player : Bukkit.getOnlinePlayers()) {
			UUID id = player.getUniqueId();
			participants.add(id);
			aliveUUIDs.add(id);
		}

		Bukkit.broadcastMessage("§c[DeathSwap] §7Game is starting with swap time between " + (minSwapTime / 60) + " and " + (maxSwapTime / 60) + " minutes.");
		countdownTime = minSwapTime + random.nextInt(maxSwapTime - minSwapTime + 1);
	}

    @Override
    public void start(FileConfiguration config) {
		this.init(config);

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
        };
        gameTask.runTaskTimer(PluginMain.getInstance(), 0L, 20L);
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

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		UUID id = event.getEntity().getUniqueId();
		aliveUUIDs.remove(id);
		if (aliveUUIDs.size() <= 1) {
			// Moves to the next person in the sequence (last alive player)
			UUID winner = aliveUUIDs.isEmpty() ? null : aliveUUIDs.iterator().next();
			if (winner != null) {
				Bukkit.broadcastMessage("§c[DeathSwap] §7" + Bukkit.getPlayer(winner).getName() + " is the winner!");
			} else {
				Bukkit.broadcastMessage("§c[DeathSwap] §7No players left, game over!");
			}
			this.stop();
		}
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		if (!aliveUUIDs.contains(event.getPlayer().getUniqueId())) {
			event.getPlayer().setGameMode(GameMode.SPECTATOR);
		}
	}

    @Override
    public void stop() {
        if (gameTask != null) gameTask.cancel();
    }

    @Override
    public String getName() {
        return "DeathSwap";
    }
}
