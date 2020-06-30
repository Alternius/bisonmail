package com.alternius.bison_mail;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class Mailbox {
	
	private UUID owner;
	private List<MailboxItem> items;
	
	public Mailbox(UUID owner) {
		this.owner = owner;
		items = new ArrayList<MailboxItem>();
	}
	
	public UUID getOwner() {
		return owner;
	}
	
	public void setOwner(UUID owner) {
		this.owner = owner;
	}
	
	public void addItem(MailboxItem item) {
		items.add(item);
	}
	
	public void removeItem(MailboxItem item) {
		items.remove(item);
	}
	
	public List<MailboxItem> getItems() {
		return items;
	}
	
	public MailboxItem getEnvelope(int id) {
		for(MailboxItem item:items) {
			if(item.getID() == id)
				return item;
		}
		return null;
	}
	
	public MailboxView getView() {
		return new MailboxView(this);
	}
}
