package dev.aglerr.krakendonations;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Utils {
	
	public static String color(String s) {
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	public static void sendConsoleLog(String s) {
		Bukkit.getConsoleSender().sendMessage("[KrakenDonations] " + s);
	}

}
