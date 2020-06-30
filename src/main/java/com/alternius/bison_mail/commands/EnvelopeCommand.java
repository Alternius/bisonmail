package com.alternius.bison_mail.commands;

import java.util.Arrays;
import java.util.UUID;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.alternius.bison_mail.BisonMail;
import com.alternius.bison_mail.Envelope;

public class EnvelopeCommand implements CommandExecutor {
	
	private BisonMail plugin;
	
	public EnvelopeCommand(BisonMail plugin) {
		this.plugin = plugin;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) { // commands to modify/spawn envelopes
		if (cmd.getName().equalsIgnoreCase("envelope") && sender instanceof Player) {
			Player p = (Player) sender;
			if(args.length == 0) {
				return false;
			}
			
			if(args[0].equals("spawn")) {
				int id = plugin.envelopes.size();
				plugin.envelopes.put(id, new Envelope(id, p.getUniqueId()));
				ItemStack en = new ItemStack(BisonMail.ENV_ITEM);
				ItemMeta meta = en.getItemMeta();
				meta.setDisplayName(BisonMail.LABEL_ENV);
				meta.setLore(Arrays.asList(Integer.toString(plugin.envelopes.size() - 1)));
				en.setItemMeta(meta);
				p.getInventory().addItem(en);
				plugin.getLogger().info("Given envelope with ID " + (plugin.envelopes.size() - 1));
				return true;
			}
			
			else if(args[0].equals("recipient")) {
				if(args.length != 2)
					return false;
				
				if(p.getEquipment().getItemInMainHand().getType() == BisonMail.ENV_ITEM && p.getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
					Envelope e = plugin.envelopes.get(Integer.parseInt(p.getEquipment().getItemInMainHand().getItemMeta().getLore().get(0)));
					if(e.isSealed()) {
						e.setRecipient(UUID.fromString(args[1])); // sets recipient by UUID, no check to ensure UUID is valid to maintain simplicity
						p.sendMessage(BisonMail.MSG_RECIPIENT_SET);
						return true;
					}
				}
				p.sendMessage(BisonMail.MSG_INVALID_ITEM);
				return true;
			}
			
			else if(args[0].equals("name")) {
				if(args.length < 2)
					return false;
				
				String name = "";
				for(int i=1;i<args.length;i++) {
					name += args[i];
					if(!(i == args.length - 1))
						name += " ";
				}
				
				if(p.getEquipment().getItemInMainHand().getType() == BisonMail.ENV_ITEM && p.getEquipment().getItemInMainHand().getItemMeta().hasLore()) {
					Envelope e = plugin.envelopes.get(Integer.parseInt(p.getEquipment().getItemInMainHand().getItemMeta().getLore().get(0)));
					if(e.isSealed()) {
						e.setTitle(name);
						p.sendMessage(BisonMail.MSG_NAME_SET);
						return true;
					}
				}
				p.sendMessage(BisonMail.MSG_INVALID_ITEM);
				return true;
			}
		}
		return false;
	}
}
