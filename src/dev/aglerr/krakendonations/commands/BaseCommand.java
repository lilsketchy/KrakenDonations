package dev.aglerr.krakendonations.commands;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import dev.aglerr.krakendonations.KrakenDonations;
import dev.aglerr.krakendonations.Utils;
import dev.aglerr.krakendonations.XSeries.XSound;

public class BaseCommand implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("donation")) {
			
			FileConfiguration config = KrakenDonations.getInstance().getConfigManager().getConfiguration();
			FileConfiguration packages = KrakenDonations.getInstance().getPackagesManager().getConfiguration();
			
			if(sender instanceof Player) {
				Player player = (Player) sender;
				if(!(player.hasPermission("krakendonations.admin"))) {
					player.sendMessage(Utils.color(config.getString("messages.no-permission")));
					return true;
				}
				
				if(args.length == 0) {
					for(String keys : config.getStringList("messages.help")) {
						player.sendMessage(Utils.color(keys));
					}
					
				} else if(args[0].equalsIgnoreCase("reload")) {
					
					KrakenDonations.getInstance().getConfigManager().reloadData();
					KrakenDonations.getInstance().getPackagesManager().reloadData();
					player.sendMessage(Utils.color(config.getString("messages.reload")));
					
				} else if(args[0].equalsIgnoreCase("send")) {
					if(args.length < 3) {
						player.sendMessage(Utils.color("&cUsage: /donation send <packages> <player>"));
						return true;
					}
						
					String playerpackage = args[1];
					Player target = Bukkit.getServer().getPlayer(args[2]);
					if(packages.isConfigurationSection("packages." + playerpackage)) {
						if(target != null) {
							String packageConvert = packages.getString("packages." + playerpackage + ".product-name");
							for(String key : config.getStringList("announcement-message")) {
								for(Player all : Bukkit.getServer().getOnlinePlayers()) {
									all.sendMessage(Utils.color(key)
											.replace("%player_name%", target.getName())
											.replace("%product%", Utils.color(packageConvert)));
								}
							}
							
							// Format: EFFECTS;MULTIPLIER;DURATION (in seconds)
							for(String effects : config.getStringList("effects")) {
								
								String[] split = effects.split(";");
								String effect = split[0];
								int multiplier = Integer.valueOf(split[1]) - 1;
								int duration = Integer.valueOf(split[2]) * 20;
								
								for(Player all : Bukkit.getServer().getOnlinePlayers()) {
									all.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), duration, multiplier));
								}
								
							}
							
							for(String sounds : config.getStringList("sounds")) {
								
								String[] split = sounds.split(";");
								String name = split[0];
								float volume = Integer.valueOf(split[1]);
								float pitch = Integer.valueOf(split[2]);
								
								for(Player all : Bukkit.getServer().getOnlinePlayers()) {
									Optional<XSound> sound = XSound.matchXSound(name);
									sound.ifPresent(sound1 -> sound1.playSound(all.getLocation(), volume, pitch));
								}
								
							}
								
						} else {
							player.sendMessage(Utils.color(config.getString("messages.target-not-found")));
							return true;
						}
					} else {
						player.sendMessage(Utils.color(config.getString("messages.package-invalid")));
						return true;
					}
				
				}
					
			} else if(!(sender instanceof Player)) {
				if(args.length == 0) {
					for(String keys : config.getStringList("messages.help")) {
						sender.sendMessage(Utils.color(keys));
					}
					
				} else if(args[0].equalsIgnoreCase("reload")) {
					
					KrakenDonations.getInstance().getConfigManager().reloadData();
					KrakenDonations.getInstance().getPackagesManager().reloadData();
					sender.sendMessage(Utils.color(config.getString("messages.reload")));
					
				} else if(args[0].equalsIgnoreCase("send")) {
					if(args.length < 3) {
						sender.sendMessage(Utils.color("&cUsage: /donation send <packages> <player>"));
						return true;
					}
						
					String playerpackage = args[1];
					Player target = Bukkit.getServer().getPlayer(args[2]);
					if(packages.isConfigurationSection("packages." + playerpackage)) {
						if(target != null) {
							String packageConvert = packages.getString("packages." + playerpackage + ".product-name");
							for(String key : config.getStringList("announcement-message")) {
								for(Player all : Bukkit.getServer().getOnlinePlayers()) {
									all.sendMessage(Utils.color(key)
											.replace("%player_name%", target.getName())
											.replace("%product%", Utils.color(packageConvert)));
								}
							}
							
							// Format: EFFECTS;MULTIPLIER;DURATION (in seconds)
							for(String effects : config.getStringList("effects")) {
								
								String[] split = effects.split(";");
								String effect = split[0];
								int multiplier = Integer.valueOf(split[1]) - 1;
								int duration = Integer.valueOf(split[2]) * 20;
								
								for(Player all : Bukkit.getServer().getOnlinePlayers()) {
									all.addPotionEffect(new PotionEffect(PotionEffectType.getByName(effect), duration, multiplier));
								}
								
							}
							
							for(String sounds : config.getStringList("sounds")) {
								
								String[] split = sounds.split(";");
								String name = split[0];
								float volume = Integer.valueOf(split[1]);
								float pitch = Integer.valueOf(split[2]);
								
								for(Player all : Bukkit.getServer().getOnlinePlayers()) {
									Optional<XSound> sound = XSound.matchXSound(name);
									sound.ifPresent(sound1 -> sound1.playSound(all.getLocation(), volume, pitch));
								}
								
							}
								
						} else {
							sender.sendMessage(Utils.color(config.getString("messages.target-not-found")));
							return true;
						}
					} else {
						sender.sendMessage(Utils.color(config.getString("messages.package-invalid")));
						return true;
					}
				
				}
				
				
			}
				
		}
		return false;
	}

}
