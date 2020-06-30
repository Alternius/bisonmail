package com.alternius.bison_mail;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class MailboxView {
	
	private Mailbox box;
	private Inventory inv;
	
	public MailboxView(Mailbox box) {
		this.box = box;
		inv = Bukkit.createInventory(null, 54, "Mailbox");
		for(int i=0;i<box.getItems().size();i++) {
			inv.setItem(i,box.getItems().get(i).createItem());
		}
	}
	
	public Inventory getInventory() {
		return inv;
	}
	
	public void update() {
		for(int i=0;i<box.getItems().size();i++) {
			inv.setItem(i,box.getItems().get(i).createItem());
		}
	}
}
