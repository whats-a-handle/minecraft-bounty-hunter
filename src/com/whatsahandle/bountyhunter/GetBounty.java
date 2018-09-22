package com.whatsahandle.bountyhunter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetBounty implements CommandExecutor {

	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	
		if(sender instanceof Player) {
			String targetPlayerName = args[0]; //check if not null in future
			int bountyAmount = 0;
			DatabaseConnection dbConnection = new DatabaseConnection();
        	dbConnection.authenticate();
        	dbConnection.createTable();
        	
			sender.sendMessage("Getting bounty for " + targetPlayerName);
			
			bountyAmount = dbConnection.getPlayerBounty(targetPlayerName);
			
			sender.sendMessage(targetPlayerName + " has a bounty of $" + bountyAmount);
			
			return true;
		}
		
		
		return false;
	}
		
}