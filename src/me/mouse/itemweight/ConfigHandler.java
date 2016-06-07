package me.mouse.itemweight;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class ConfigHandler {
	private final ItemWeight plugin;

	private HashMap<Item, Float> itemWeights = new HashMap<>();
	private float defaultWeight;
	private float defaultSpeed;

	public ConfigHandler(ItemWeight plugin) {
		this.plugin = plugin;
	}

	@SuppressWarnings("deprecation")
	public void reload() {
		plugin.reloadConfig();
		FileConfiguration config = plugin.getConfig();
		defaultWeight = (float) config.getDouble("defaultWeight", 0.0002);
		defaultSpeed = (float) config.getDouble("defaultSpeed", 1.25);
		List<String> l = config.getStringList("itemWeights");
		for (String s : l) {
			String args[] = s.split(":");
			Material m = null;
			short d = -1;
			float w = defaultWeight;
			try {
				m = Material.getMaterial(Integer.parseInt(args[0]));
			} catch (Exception e) {
				m = Material.getMaterial(args[0]);
			}
			if(args.length==2){
				try {
					w = Float.parseFloat(args[1]);
				} catch (Exception e) {
					w = defaultWeight;
				}
			}else if(args.length==3){
				try {
					d = Short.parseShort(args[1]);
				} catch (Exception e) {
					d = -1;
				}
				try {
					w = Float.parseFloat(args[2]);
				} catch (Exception e) {
					w = defaultWeight;
				}
			}
			if (m != null) {
				itemWeights.put(new Item(m,d), w);
			}
		}
	}

	public float getItemWeight(ItemStack item) {
		for(Item i:itemWeights.keySet()){
			if(i.material==item.getType()&&(i.damage==-1||i.damage==item.getDurability())){
				return itemWeights.get(i);
			}
		}
		return defaultWeight;
	}

	public float getDefaultSpeed() {
		return defaultSpeed;
	}
	
	public class Item{
		Material material;
		short damage;
		public Item(Material m,short d) {
			this.material=m;
			this.damage=d;
		}
	}
}
