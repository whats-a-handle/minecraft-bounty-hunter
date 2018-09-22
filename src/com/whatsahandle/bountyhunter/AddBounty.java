package com.whatsahandle.bountyhunter;

import java.math.BigDecimal;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;

public class AddBounty implements CommandExecutor {
	
	private TNEAPI tneAPI = TNE.instance().api();  
	

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
        if(sender instanceof  Player ) {
        	DatabaseConnection dbConnection = new DatabaseConnection();
            dbConnection.authenticate();
            dbConnection.createTable();	
        	
        	String senderName = sender.getName();
        	String targetPlayer = "";
        	int bountyAmount = 0;
        	
        	if(args[0] == null) {
        		sender.sendMessage("Sorry, you must enter a target player name.");
        		return false;
        	}
        	else {
        		targetPlayer = args[0];
        	}
        	
        	if(BountyValidator.checkIfSamePlayer(senderName, targetPlayer)) {
        		sender.sendMessage("Sorry, you can\'t set a bounty on yourself.");
        		return false;
        	}
        	
        	if(!BountyValidator.checkIfPlayerExists(targetPlayer)) {
        		sender.sendMessage("Sorry, but that player has never played on this server.");
        		return false;
        	}
  
        	if(args[1] != null && BountyValidator.isPlayerBountyValid(args[1])) {
        		
        		bountyAmount = Integer.valueOf(args[1]);
        		
        		if(!BountyValidator.hasEnoughMoney(senderName, bountyAmount, dbConnection, this.tneAPI)) {
        			sender.sendMessage("Sorry, you don\'t have enough money to set a bounty of $" + bountyAmount);
        			return false;
        		}
        		else {
	        		bountyAmount = Integer.valueOf(args[1]);
	        		this.tneAPI.removeHoldings(senderName, BigDecimal.valueOf(bountyAmount));
	            	dbConnection.addPlayerBounty(targetPlayer, bountyAmount);
	            	sender.sendMessage("You\'ve placed a bounty of $" + bountyAmount + " on " + targetPlayer);
	            	sender.sendMessage("$" + bountyAmount + " has been removed from your holdings");
	            	return true;
        		}
        		
        	}
        	else {
        		sender.sendMessage("Sorry, enter a bounty amount greater than 0");
        		return false;
        	}	
        
        }

    	return false;
    }
}
