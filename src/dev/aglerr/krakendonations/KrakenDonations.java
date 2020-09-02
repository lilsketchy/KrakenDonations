package dev.aglerr.krakendonations;

import org.bukkit.plugin.java.JavaPlugin;

import dev.aglerr.krakendonations.commands.BaseCommand;
import dev.aglerr.krakendonations.configs.ConfigManager;
import dev.aglerr.krakendonations.configs.PackagesManager;

public class KrakenDonations extends JavaPlugin{
	
	private static KrakenDonations instance;
	private ConfigManager config = new ConfigManager();
	private PackagesManager packages = new PackagesManager();
	
	@Override
	public void onEnable() {
		
		instance = this;
		loadAllConfiguration();
		this.getCommand("donation").setExecutor(new BaseCommand());
		
	}
	
	@Override
	public void onDisable() {
		
	}
	
	private void loadAllConfiguration() {
		config.setup();
		packages.setup();
	}
	
	public static KrakenDonations getInstance() { return instance; }
	public ConfigManager getConfigManager() { return config; }
	public PackagesManager getPackagesManager() { return packages; }

}
