package com.madcreeper11.MCMiniGames;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class MiniGameInventorySelector implements Listener {

	private Inventory gameSelectorInventory;
	private Inventory playerSelectorInventory;
	private HashMap<UUID, Inventory> openInventories;

	public MiniGameInventorySelector() {
		Bukkit.getPluginManager().registerEvents(this, PluginMain.getInstance());
		openInventories = new HashMap<UUID, Inventory>();
		gameSelectorInventory = Bukkit.createInventory(null, 54, "Mini Games");
		// TODO: make this sizeable
		playerSelectorInventory = Bukkit.createInventory(null, 36, "Player Select");

		int i = 0;
		Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
		for(Player p : onlinePlayers) {

			ItemStack skull = new ItemStack(Material.SKELETON_SKULL, 1);

			SkullMeta meta = (SkullMeta) skull.getItemMeta();
			meta.setOwningPlayer(p);
			meta.setDisplayName(ChatColor.LIGHT_PURPLE + p.getName());
			skull.setItemMeta(meta);

			playerSelectorInventory.setItem(i, skull);
			i++;
		}
	}

	public void setOpenInventories(UUID uniqueID, Inventory inventory) {
		openInventories.put(uniqueID, inventory);
	}
	
	public HashMap<UUID, Inventory> getOpenInventories() {
		return openInventories;
	}
	
	public void addGameTypeToInventory(ItemStack item) {
		gameSelectorInventory.addItem(item);
	}

	// To open initial inventory
	@EventHandler
	public void onItemRightClickEvent(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		ItemStack item = event.getItem();
		
		// Open starter inventory
		if (item == null)
			return;
		if (item.equals(new ItemStack(Material.CLOCK)) && p.isOp()) {
			p.openInventory(gameSelectorInventory);
			openInventories.put(p.getUniqueId(), gameSelectorInventory);
		}
	}
	
	@EventHandler
    public void onInventoryInteractEvent(InventoryClickEvent event) {
        Player p = (Player) event.getWhoClicked();
        Inventory clickedInv = event.getClickedInventory();
		int slotNum = event.getSlot();
		
		// No special inventories open? No problem, ignore anything further
		if(openInventories.get(p.getUniqueId()) == null) return; 
		// Now we are in a special inventory but it can't be the player's
		event.setCancelled(true);
		
        // Let each game instance handle their own inventories
        if(openInventories.get(p.getUniqueId()).equals(gameSelectorInventory) 
        		&& event.getClickedInventory().getType() != InventoryType.PLAYER
        		&& clickedInv.getItem(slotNum) != null) {
        	
	      /*  Inventory tempInv = PluginMain.getGameRegistry().openInventory(slotNum);
	        openInventories.put(p.getUniqueId(), PluginMain.getGameRegistry().openInventory(slotNum));
	        p.openInventory(tempInv);*/
        }
    }

	// If player closes menu then they have no inventories in the hashmap
	@EventHandler
    public void InvClose(InventoryCloseEvent event){
		Player p = (Player) event.getPlayer();
		openInventories.remove(p.getUniqueId());
    }
	// Whatever inventory they are in it will show up in hashmap (way for directing user actions)
	@EventHandler
    public void InvOpen(InventoryOpenEvent event){
		Player p = (Player) event.getPlayer();
		// If location where the inventory originates from is part of the world then skip
		if (event.getInventory().getLocation() != null) return;
		openInventories.put(p.getUniqueId(), event.getInventory());
    }
}
