package nl.thedutchmc.multiplayerevents;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import nl.thedutchmc.multiplayerevents.annotations.Nullable;
import nl.thedutchmc.multiplayerevents.utils.Utils;

public class ConfigurationHandler {

	private MultiplayerEvents plugin;
	private File file;
	private FileConfiguration config;
	
	private static Map<String, Object> configOptions = new HashMap<>();
	
	public ConfigurationHandler(MultiplayerEvents plugin) {
		this.plugin = plugin;
	}
	
	public void loadConfig() {
		file = new File(plugin.getDataFolder(), "config.yml");
		
		if(!file.exists()) {
			file.getParentFile().mkdirs();
			plugin.saveResource("config.yml", false);
		}
		
		config = new YamlConfiguration();
		
		try {
			config.load(file);
			readConfig();
		} catch (InvalidConfigurationException e) {
			MultiplayerEvents.logWarn("Invalid config.yml!");
			MultiplayerEvents.logDebug(Utils.getStackTrace(e));
		} catch (IOException e) {
			MultiplayerEvents.logWarn("Unable to read config.yml due to an IOException!");
			MultiplayerEvents.logDebug(Utils.getStackTrace(e));
		}
	}
	
	private void readConfig() {
		//System.out.println(config.getConfigurationSection("config").getKeys(false));
		//configOptions = config.getConfigurationSection("config").getKeys(false).stream().collect(Collectors.toMap(x -> x, config::get));
		
		for(String key : config.getKeys(false)) {
			Object value = config.get(key);
			configOptions.put(key, value);
		}
	}
	
	@Nullable
	public Object getConfigOption(String optionName) {
		return configOptions.get(optionName);
	}
}
