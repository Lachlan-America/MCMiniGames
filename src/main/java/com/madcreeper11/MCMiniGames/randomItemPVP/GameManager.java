package MCMiniGames.randomItemPVP;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import MCMiniGames.PluginMain;

public class GameManager implements Listener {

	private HashMap<UUID, Material> currentPickup;
	private int minutes;
	
	public GameManager() {
		Bukkit.getPluginManager().registerEvents(this, PluginMain.getInstance());
		currentPickup = new HashMap<UUID, Material>();
		this.minutes = 5;
		
		Update();
	}

	public void Update() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(PluginMain.getInstance(), new Runnable() {

			@Override
			public void run() {
				if (!RandomItemPVPGame.getGameSettings().getGameState())
					return;
				
		        World world = Bukkit.getServer().getWorld("MCManHunt");
		        List<Entity> entList = world.getEntities();
		 
		        for(Entity current : entList){
		            if (current.getType() == EntityType.DROPPED_ITEM){
		            	Item currentItem = (Item) current;
		            	ItemStack itemS = currentItem.getItemStack();
		            	currentItem.remove();
		            	/*Material currentMat = itemS.getType();
		            	
		            	for(Material mat : currentPickup.values()) {
		            		if(mat.equals(currentMat)) {
		    		            current.remove();
		            		}
		            	}*/
		            }
		        }
		        
				Bukkit.broadcastMessage(ChatColor.GOLD + "Random item drops commencing!");
				for (Player player : Bukkit.getOnlinePlayers()) {

					Material[] materials = Material.values();
					Random randomizer = new Random();
					Material randomMat = Material.AIR;
					while (randomMat == Material.AIR || randomMat == null) {
						randomMat = materials[randomizer.nextInt(materials.length)];
					}

					//int droppedAmount = getFullSlots(player.getInventory());
					int droppedAmount = 1;
					int freeSlots = 1 - droppedAmount;

					int multiplier = 64;

					if (randomMat.toString().endsWith("PICKAXE") || randomMat.toString().endsWith("SWORD")
							|| randomMat.toString().endsWith("HOE") || randomMat.toString().endsWith("AXE")
							|| randomMat.toString().endsWith("SHOVEL") || randomMat.toString().endsWith("BOW")
							|| randomMat.toString().endsWith("HELMET") || randomMat.toString().endsWith("CHESTPLATE")
							|| randomMat.toString().endsWith("LEGGINGS") || randomMat.toString().endsWith("BOOTS")
							|| randomMat.toString().endsWith("SHIELD") || randomMat.toString().endsWith("TRIDANT")) {
						multiplier = 1;
					}

					ItemStack itemStack = new ItemStack(randomMat, multiplier * freeSlots);
					
					if (freeSlots > 0) {
						player.getInventory().addItem(itemStack);
					}
					if (droppedAmount > 0) {
						for (int i = 0; i <= droppedAmount; i++) {
							player.getWorld().dropItemNaturally(player.getLocation(), new ItemStack(randomMat, multiplier));
						}
					}

					currentPickup.put(player.getUniqueId(), randomMat);
				}
			}

		}, 0, 20L * 2 * minutes);
	}

	public int getFullSlots(Inventory inventory) {
		int i = 0;
		for (ItemStack is : inventory.getStorageContents()) {
			if (is == null)
				continue;
			i++;
		}
		return i;
	}

	@EventHandler
	public void onCollect(EntityPickupItemEvent e) {
		if (!RandomItemPVPGame.getGameSettings().getGameState())
			return;

		if (currentPickup != null) {
			if (e.getEntity() instanceof Player) {
				Player player = (Player) e.getEntity();
				UUID playerUUID = player.getUniqueId();

				for (Material material : currentPickup.values()) {

					// If picked up item doesn't equal what you currently are able to and the
					// material is someone elses, don't pickup
					if (e.getItem().getItemStack().getType() != currentPickup.get(playerUUID)
							&& e.getItem().getItemStack().getType() == material) {
						e.setCancelled(true);
					}

				}
			}
		}
	}
}
