package com.whatsahandle.bountyhunter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GetBounty implements CommandExecutor {

	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	
		if(sender instanceof Player) {
			//default the target to the player in case they dont provide an argument
			String targetPlayer = sender.getName();
			
			if(args.length > 0) {
				targetPlayer = args[0]; 
			}
			if(BountyValidator.checkIfPlayerExists(targetPlayer)) {
				int bountyAmount = 0;
				DatabaseConnection dbConnection = new DatabaseConnection();
		        dbConnection.authenticate();
		        dbConnection.createTable();

				bountyAmount = dbConnection.getPlayerBounty(targetPlayer);
					
				sender.sendMessage(targetPlayer + " has a bounty of $" + bountyAmount);
					
				return true;
			}
			else {
				sender.sendMessage("Player " + targetPlayer + " doesn\'t exist");
			}
			
		}
		
		return false;
	}
		
}