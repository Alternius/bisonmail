package com.alternius.bison_mail.handlers;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.alternius.bison_mail.BisonMail;
import com.alternius.bison_mail.Envelope;
import com.alternius.bison_mail.EnvelopeView;
import com.alternius.bison_mail.Mailbox;
import com.alternius.bison_mail.MailboxItem;
import com.alternius.bison_mail.MailboxView;

public class PlayerInteractHandler implements Listener {

	private BisonMail plugin;

	public PlayerInteractHandler(BisonMail plugin) {
		this.plugin = plugin;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerInteract(PlayerInteractEvent event) {
		if (event.getHand() == EquipmentSlot.OFF_HAND)
			return;

		Player p = event.getPlayer();
		ItemStack item = p.getInventory().getItemInMainHand();
		if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.SKULL && event.getClickedBlock().hasMetadata("mailbox")) {
			if (item != null && item.getType() == BisonMail.ENV_ITEM && item.getItemMeta().hasLore()) {
				Envelope e = plugin.envelopes.get(Integer.parseInt(item.getItemMeta().getLore().get(0)));
				if (e.isSealed()) {
					Bukkit.getLogger().info("Send envelope");
					if(e.getRecipient() != null) {
						// send envelope
						MailboxItem delivery = new MailboxItem(e.getID(), p.getUniqueId(), e.getItems(), e.getTitle());
						plugin.mailboxes.get(e.getRecipient()).addItem(delivery);
						Player rec = Bukkit.getPlayer(e.getRecipient());
						if(rec != null) {
							rec.sendMessage(BisonMail.MSG_REC_MAIL);
						}
						p.sendMessage(BisonMail.MSG_SEND_SUCCESS);
						p.getInventory().removeItem(item);
					}
					else {
						p.sendMessage(BisonMail.MSG_NO_RECIPIENT);
					}
				}
				else {
					p.sendMessage(BisonMail.MSG_NOT_SEALED);
				}
			}
			else {
				// open mailbox
				Mailbox box = plugin.mailboxes.get(p.getUniqueId());
				p.openInventory(new MailboxView(box).getInventory());
			}
		}
		
		else if((event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) && 
				item.getType() == BisonMail.ENV_ITEM && item.getItemMeta().hasLore()) {
			Envelope env = plugin.envelopes.get(Integer.parseInt(item.getItemMeta().getLore().get(0)));
			if(!env.isSealed()) {
				p.openInventory(new EnvelopeView(env).getInventory());
			}
			else {
				p.sendMessage(BisonMail.MSG_SEALED);
			}
		}
	}
}