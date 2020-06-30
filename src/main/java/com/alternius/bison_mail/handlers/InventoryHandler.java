package com.alternius.bison_mail.handlers;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.alternius.bison_mail.BisonMail;
import com.alternius.bison_mail.Envelope;
import com.alternius.bison_mail.Mailbox;

public class InventoryHandler implements Listener {
	
	BisonMail plugin;
	
	public InventoryHandler(BisonMail plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void onInventoryClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equals("Envelope")){
        	Envelope env = plugin.envelopes.get(Integer.parseInt(p.getEquipment().getItemInMainHand().getItemMeta().getLore().get(0)));
        	ItemStack item = e.getCurrentItem();
        	if(item == null || item.getType() == Material.AIR || !item.getItemMeta().hasLore()) {
        		return;
        	}
        	
        	if(item.getItemMeta().getLore().get(0).equals("immovable") || (item.getType() == BisonMail.ENV_ITEM)) {
        		e.setCancelled(true);
        	}
        	
        	if(item.getType() == Material.STAINED_CLAY && item.getItemMeta().getDisplayName().equals(BisonMail.LABEL_SEAL)) {
        		p.closeInventory();
        		env.setSealed(true);
        	}
        }
        
        else if(e.getInventory().getTitle().equals("Mailbox")) {
        	ItemStack item = e.getCurrentItem();
        	e.setCancelled(true);
        	if(item == null || item.getType() == Material.AIR || !item.getItemMeta().hasLore() || item.getType() != BisonMail.SEALED_ENV_ITEM)
        		return;
        	        	
        	int spaceNeeded = Integer.parseInt(item.getItemMeta().getLore().get(1).split(" ")[0]);
        	int spaceAvailable = 0;
        	Inventory inv = p.getInventory();
        	
        	for(ItemStack i:inv.getContents()) {
        		if(i == null || i.getType() == Material.AIR)
        			spaceAvailable++;
        	}
        	
        	spaceAvailable -= 5; // account for armor slots and offhand
        	
        	if(spaceAvailable < spaceNeeded) {
        		p.sendMessage(BisonMail.MSG_NO_SPACE);
        		return;
        	}
        	else {
        		int id = Integer.parseInt(item.getItemMeta().getLore().get(2));
        		Mailbox mb = plugin.mailboxes.get(p.getUniqueId());
        		List<ItemStack> itemsToMove = mb.getEnvelope(id).getContents();
        		for(int i=0;i<itemsToMove.size();i++) {
        			if(itemsToMove.get(i) != null && !(itemsToMove.get(i).getItemMeta().hasLore() && itemsToMove.get(i).getItemMeta().getLore().get(0) == "immovable"))
        				p.getInventory().addItem(itemsToMove.get(i));
        		}
        		mb.removeItem(mb.getEnvelope(id));
        		p.getInventory().remove(item);
        		p.closeInventory();
        		p.openInventory(mb.getView().getInventory());
        	}
        }
    }
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		ItemStack item = p.getEquipment().getItemInMainHand();
		if(e.getInventory().getName() == "Envelope") {
			Envelope env = plugin.envelopes.get(Integer.parseInt(item.getItemMeta().getLore().get(0)));
			env.setItems(Arrays.asList(e.getInventory().getContents()));
		}
	}
}
