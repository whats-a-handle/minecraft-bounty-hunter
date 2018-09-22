package com.whatsahandle.bountyhunter;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;

public class SetBounty implements CommandExecutor {
	
	private TNEAPI tneAPI = TNE.instance().api();  
	
	public boolean checkIfPlayerExists(String targetPlayer) {
		for(OfflinePlayer player :  Bukkit.getOfflinePlayers()) {
    		if(player.getName() == targetPlayer) {
    			return true;
    		}
    	}
		return false;
	}
	
	public boolean checkIfSamePlayer(String sender, String targetPlayer) {
		
		if(sender.equalsIgnoreCase(targetPlayer)) {
			return true;
		}
		
		return false;
	}
	
	public boolean isBountyValid(String bountyString) {
		
		try {
			int bountyAmount = Integer.valueOf(bountyString);
			if(bountyAmount <= 0) {
				return false;
			}
			else {
				return true;
			}
		}
		catch(NumberFormatException error){
			System.out.println("ERROR amount not number\n" + error);
			return false;
		}
	
	}
		
	public boolean hasEnoughMoney(String senderName, int bountyAmount, DatabaseConnection dbConnection) {
		BigDecimal balance = BigDecimal.ZERO;
		balance = this.tneAPI.getHoldings(senderName);
		
		if(balance.compareTo(BigDecimal.valueOf(bountyAmount)) >= 0) {
			return true;
		}
		return false;
	}
	
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
        	
        	if(checkIfSamePlayer(senderName, targetPlayer)) {
        		sender.sendMessage("Sorry, you can\'t set a bounty on yourself.");
        		return false;
        	}
        	
        	if(!checkIfPlayerExists(targetPlayer)) {
        		sender.sendMessage("Sorry, but that player has never played on this server.");
        		return false;
        	}
  
        	if(args[1] != null && isBountyValid(args[1])) {
        		
        		bountyAmount = Integer.valueOf(args[1]);
        		
            	dbConnection.setPlayerBounty(targetPlayer, bountyAmount);
            	sender.sendMessage("You\'ve placed a bounty of $" + bountyAmount + " on " + targetPlayer);
            	
            	return true;
        		
        	}
        	else {
        		sender.sendMessage("Sorry, enter a bounty amount greater than 0");
        		return false;
        	}	
        
        }

    	return false;
    }
}
