package com.alternius.bison_mail;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class EnvelopeView {
	
	private Inventory inv;
	
	public EnvelopeView(Envelope env) {
		inv = Bukkit.createInventory(null, 18, "Envelope");
		for(int i=0;i<env.getItems().size();i++) {
			inv.setItem(i,env.getItems().get(i));
		}
	}
	
	public Inventory getInventory() {
		return inv;
	}
	
	public void update() {
		
	}
}
