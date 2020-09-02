package dev.aglerr.krakendonations.configs;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import dev.aglerr.krakendonations.KrakenDonations;
import dev.aglerr.krakendonations.Utils;

public class ConfigManager {
	
	public FileConfiguration data;
	public File cfg;
	
	public void setup() {
		if(!KrakenDonations.getInstance().getDataFolder().exists()) {
			KrakenDonations.getInstance().getDataFolder().mkdir();
		}
		
		cfg = new File(KrakenDonations.getInstance().getDataFolder(), "config.yml");
		
		if(!cfg.exists()) {
			
			KrakenDonations.getInstance().saveResource("config.yml", false);
			Utils.sendConsoleLog("Creating the config.yml...");
			
		}
		
		data = YamlConfiguration.loadConfiguration(cfg);
		
	}
	
	public FileConfiguration getConfiguration() {
		return data;
	}
	
	public void saveData() {
		try {
			data.save(cfg);
		} catch(IOException e) {
			Utils.sendConsoleLog("Could not save the config.yml.");
		}
	}
	
	public void reloadData() {
		data = YamlConfiguration.loadConfiguration(cfg);
	}

}
