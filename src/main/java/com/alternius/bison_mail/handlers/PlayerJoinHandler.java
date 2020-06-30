package com.alternius.bison_mail.handlers;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.alternius.bison_mail.BisonMail;
import com.alternius.bison_mail.Mailbox;

public class PlayerJoinHandler implements Listener {
	
	BisonMail plugin;
	
	public PlayerJoinHandler(BisonMail plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		
		if(plugin.mailboxes.get(p.getUniqueId()) == null)
			plugin.mailboxes.put(p.getUniqueId(), new Mailbox(p.getUniqueId()));
		
		if(plugin.mailboxes.get(p.getUniqueId()).getItems().size() > 0)
			p.sendMessage(BisonMail.MSG_HAS_MAIL);
	}
}
