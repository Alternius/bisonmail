package com.alternius.bison_mail;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import com.alternius.bison_mail.commands.EnvelopeCommand;
import com.alternius.bison_mail.commands.MailboxCommand;
import com.alternius.bison_mail.handlers.InventoryHandler;
import com.alternius.bison_mail.handlers.PlayerInteractHandler;
import com.alternius.bison_mail.handlers.PlayerJoinHandler;

public class BisonMail extends JavaPlugin {
	
	public static final Material ENV_ITEM = Material.IRON_NUGGET;
	public static final Material SEALED_ENV_ITEM = Material.GOLD_NUGGET;
	
	// string constants
	public static final String LABEL_SEAL = ChatColor.GREEN + "Seal";
	public static final String LABEL_FILLER = ChatColor.RED + "---";
	public static final String LABEL_ENV = ChatColor.RESET + "Envelope";
	
	public static final String MSG_SET_NAME = ChatColor.RED + "Set a name using /envelope name [name]";
	public static final String MSG_NOT_SEALED = ChatColor.RED + "Envelope must be sealed before being sent";
	public static final String MSG_NO_RECIPIENT = ChatColor.RED + "Envelope must have a recipient before being sent (/envelope recipient [UUID])";
	public static final String MSG_SEND_SUCCESS = ChatColor.GREEN + "Envelope sent successfully!";
	public static final String MSG_REC_MAIL = ChatColor.GREEN + "You have just received a package! Check your mailbox to open it.";
	public static final String MSG_HAS_MAIL = ChatColor.GREEN + "You have unopened mail. Check your mailbox to open it.";
	public static final String MSG_NO_SPACE = ChatColor.RED + "Not enough space in inventory to open envelope.";
	public static final String MSG_SEALED = ChatColor.RED + "Envelope is already sealed. Right click on a mailbox to send it.";
	public static final String MSG_INVALID_ITEM = ChatColor.RED + "Invalid item. Ensure you're holding a sealed envelope.";
	public static final String MSG_NAME_SET = ChatColor.GREEN + "Successfully set name.";
	public static final String MSG_RECIPIENT_SET = ChatColor.GREEN + "Successfully set recipient.";
	
	public HashMap<UUID, Mailbox> mailboxes = new HashMap<UUID, Mailbox>();
	public HashMap<Integer, Envelope> envelopes = new HashMap<Integer, Envelope>();
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(new PlayerInteractHandler(this), this);
		getServer().getPluginManager().registerEvents(new PlayerJoinHandler(this), this);
		getServer().getPluginManager().registerEvents(new InventoryHandler(this), this);
		getCommand("envelope").setExecutor(new EnvelopeCommand(this));
		getCommand("mailbox").setExecutor(new MailboxCommand(this));
	}
	
	@Override
	public void onDisable() {
		
	}
}
