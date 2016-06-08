package me.mouse.itemweight;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EventListener implements Listener {

	@EventHandler()
	public void onPlayerJoin(PlayerJoinEvent event) {
		updatePlayerSpeed(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		event.getPlayer().setWalkSpeed(0.2f);
	}

	@EventHandler(ignoreCancelled=true)
	public void onPlayerPickupItem(final PlayerPickupItemEvent event) {
		updatePlayerSpeed(event.getPlayer());
		if(!event.getPlayer().hasPermission("itemweight.overlook"))event.getPlayer().setWalkSpeed(event.getPlayer().getWalkSpeed()-ItemWeight.config.getItemWeight(event.getItem().getItemStack()) * event.getItem().getItemStack().getAmount() * 0.2f);
	}

	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		updatePlayerSpeed(event.getPlayer());
	}

	@EventHandler
	public void onPlayerChangedWorld(PlayerChangedWorldEvent event) {
		updatePlayerSpeed(event.getPlayer());
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		updatePlayerSpeed(event.getPlayer());
	}

	@EventHandler
	public void onPlayerItemConsume(PlayerItemConsumeEvent event) {
		updatePlayerSpeed(event.getPlayer());
	}

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		updatePlayerSpeed((Player) event.getPlayer());
	}

	private void updatePlayerSpeed(Player player) {
		if(player.hasPermission("itemweight.overlook")){
			player.setWalkSpeed(0.2f);
			return;
		}
		float speed = 0.2F * ItemWeight.config.getDefaultSpeed();
		PlayerInventory inv = player.getInventory();
		ItemStack armors[] = inv.getArmorContents();
		for (int i = 0; i < armors.length; i++) {
			if (armors[i] != null && armors[i].getType() != Material.AIR) {
				speed -= ItemWeight.config.getItemWeight(armors[i]) * armors[i].getAmount() * 0.2f;
			}
		}
		ItemStack items[] = inv.getContents();
		for (int i = 0; i < items.length; i++) {
			if (items[i] != null && items[i].getType() != Material.AIR) {
				speed -= ItemWeight.config.getItemWeight(items[i]) * items[i].getAmount() * 0.2f;
			}
		}
		player.setWalkSpeed(speed);
	}
}
