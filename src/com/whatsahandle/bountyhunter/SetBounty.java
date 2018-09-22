package com.whatsahandle.bountyhunter;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SetBounty implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
        if(sender instanceof  Player ) {
        	DatabaseConnection dbConnection = new DatabaseConnection();
            dbConnection.authenticate();
            dbConnection.createTable();	

        	String targetPlayer = "";
        	int bountyAmount = 0;
        	
        	if(args[0] == null) {
        		sender.sendMessage("Sorry, you must enter a target player name.");
        		return false;
        	}
        	else {
        		targetPlayer = args[0];
        	}

        	if(args[1] != null && BountyValidator.isAdminBountyValid(args[1])) {
        		
        		bountyAmount = Integer.valueOf(args[1]);
        		bountyAmount = Integer.valueOf(args[1]);
	        		
	            dbConnection.setPlayerBounty(targetPlayer, bountyAmount);
	            sender.sendMessage("You\'ve set a bounty of $" + bountyAmount + " on " + targetPlayer);

	            return true;	
        	}
        	else {
        		sender.sendMessage("Sorry, enter a bounty amount greater than or equal to 0");
        		return false;
        	}	
        
        }

    	return false;
    }
}
