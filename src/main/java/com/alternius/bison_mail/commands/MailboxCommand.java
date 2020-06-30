package com.alternius.bison_mail.commands;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import com.alternius.bison_mail.BisonMail;

import net.md_5.bungee.api.ChatColor;

public class MailboxCommand implements CommandExecutor {
	// creates mailbox when aiming at player skull
	
	private BisonMail plugin;
	
	public MailboxCommand(BisonMail plugin) {
		this.plugin = plugin;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("mailbox") && sender instanceof Player) {
			Player p = (Player) sender;
			Block b = p.getTargetBlock(null, 5);
			if (b.getType() == Material.SKULL) {
				if (b.hasMetadata("mailbox")) {
					p.sendMessage(ChatColor.RED + "Block is already a mailbox.");
					return true;
				}
				b.setMetadata("mailbox", new FixedMetadataValue(plugin, "1"));
				p.sendMessage(ChatColor.GREEN + "Successfully created mailbox!");
			} else {
				p.sendMessage(ChatColor.RED + "Invalid block. Ensure you're aiming at a player head.");
				return true;
			}
			return true;
		}
		return false;
	}

}
