package com.alternius.bison_mail;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class MailboxItem {
	
	private UUID sender;
	private List<ItemStack> contents;
	private String title;
	private int id;
	
	public MailboxItem(int id, UUID sender, List<ItemStack> contents, String title) {
		this.id = id;
		this.sender = sender;
		this.contents = contents;
		this.title = title;
	}
	
	public UUID getSender() {
		return sender;
	}
	
	public List<ItemStack> getContents() {
		return contents;
	}
	
	public void setContents(List<ItemStack> contents) {
		this.contents = contents;
	}
	
	public ItemStack createItem() {
		int size = 0;
		for(int i=0;i<contents.size();i++) {
			if(contents.get(i) != null && !contents.get(i).getItemMeta().hasLore()) {
				size++;
			}
		}
		ItemStack item = new ItemStack(BisonMail.SEALED_ENV_ITEM);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(title);
		meta.setLore(Arrays.asList(("From: " + sender.toString()), Integer.toString(size) + " items", Integer.toString(id)));
		item.setItemMeta(meta);
		return item;
	}

	public int getID() {
		return id;
	}
}
