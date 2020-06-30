package com.alternius.bison_mail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Envelope {

	private int id;
	private String title;
	private boolean sealed;
	private UUID recipient;
	private UUID sender;
	private List<ItemStack> items;
	private EnvelopeView view;
	private final int ENV_SIZE = 18;

	public Envelope(int id, UUID sender) {
		this.id = id;
		this.sender = sender;
		sealed = false;
		items = new ArrayList<ItemStack>(ENV_SIZE);
		title = "Envelope"; // default name for envelope
		view = new EnvelopeView(this);

		ItemStack pane = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
		ItemStack confirmButton = new ItemStack(Material.STAINED_CLAY, 1, (short) 13);
		ItemStack air = new ItemStack(Material.AIR);
		
		ItemMeta paneMeta = pane.getItemMeta();
		ItemMeta confirmMeta = confirmButton.getItemMeta();
		
		paneMeta.setDisplayName(BisonMail.LABEL_FILLER);
		confirmMeta.setDisplayName(BisonMail.LABEL_SEAL);
		paneMeta.setLore(Arrays.asList("immovable"));
		confirmMeta.setLore(Arrays.asList("immovable"));
		pane.setItemMeta(paneMeta);
		confirmButton.setItemMeta(confirmMeta);

		for (int i = 0; i < ENV_SIZE; i++) { // starts on last row of inventory (rows 9 each)
			if (i >= ENV_SIZE - 9) {
				if (i == ENV_SIZE - 5) // middle of row for confirm button
					items.add(confirmButton);
				else
					items.add(pane);
			} else {
				items.add(air);
			}
		}
	}
	
	public void update() {
		items = Arrays.asList(view.getInventory().getContents());
	}

	public boolean isSealed() {
		return sealed;
	}

	public void setSealed(boolean sealed) {
		this.sealed = sealed;
	}

	public UUID getRecipient() {
		return recipient;
	}

	public void setRecipient(UUID recipient) {
		this.recipient = recipient;
	}

	public UUID getSender() {
		return sender;
	}

	public void setSender(UUID sender) {
		this.sender = sender;
	}

	public void setItems(List<ItemStack> items) {
		this.items = items;
	}

	public List<ItemStack> getItems() {
		return items;
	}

	public void removeItem(ItemStack item) {
		items.remove(item);
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
