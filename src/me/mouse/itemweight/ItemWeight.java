package me.mouse.itemweight;

import org.bukkit.plugin.java.JavaPlugin;

public class ItemWeight extends JavaPlugin {

	public static ConfigHandler config;

	@Override
	public void onEnable() {
		saveDefaultConfig();
		config = new ConfigHandler(this);
		config.reload();
		getServer().getPluginManager().registerEvents(new EventListener(), this);
		getCommand("itemweight").setExecutor(new CommandHandler());
	}

	@Override
	public void onDisable() {

	}

}
