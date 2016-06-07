package me.mouse.itemweight;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHandler implements CommandExecutor {

	public boolean onCommand(CommandSender p1, Command p2, String p3, String[] p4) {
		if (p4.length == 0 || p4[0].equalsIgnoreCase("help")) {
			if (p1.hasPermission("itemweight.help")) {
				p1.sendMessage(ChatColor.YELLOW + "物品重量指令帮助");
				p1.sendMessage(ChatColor.YELLOW + "/itemweight reload - 重载插件配置文件");
				p1.sendMessage(ChatColor.YELLOW + "/itemweight look [玩家名] - 查看某玩家的当前速度");
			} else {
				p1.sendMessage(ChatColor.RED + "[物品重量]你没有足够的权限!");
			}
		} else if (p4[0].equalsIgnoreCase("reload")) {
			if (p1.hasPermission("itemweight.reload")) {
				ItemWeight.config.reload();
				p1.sendMessage(ChatColor.GREEN + "[物品重量]重载插件成功!");
			} else {
				p1.sendMessage(ChatColor.RED + "[物品重量]你没有足够的权限!");
			}
		} else if (p4[0].equalsIgnoreCase("look")){
			if (p1.hasPermission("itemweight.look")){
				if(p4.length==1){
					p1.sendMessage(ChatColor.YELLOW + "[物品重量]您现在的速度为: "+((Player) p1).getWalkSpeed()*5);
				}else{
					p1.sendMessage(ChatColor.YELLOW + "[物品重量] "+p4[1]+" 现在的速度为: "+Bukkit.getPlayer(p4[1]).getWalkSpeed()*5);
				}
			} else {
				p1.sendMessage(ChatColor.RED + "[物品重量]你没有足够的权限!");
			}
		}
		return true;
	}
}
