package com.whatsahandle.bountyhunter;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import net.tnemc.core.common.api.TNEAPI;

public class BountyValidator {

	public static boolean checkIfPlayerExists(String targetPlayer) {
		for(OfflinePlayer player :  Bukkit.getOfflinePlayers()) {
    		if(player.getName().equalsIgnoreCase(targetPlayer)) {
    			
    			return true;
    		}
    	}
		return false;
	}
	
	public static boolean checkIfSamePlayer(String sender, String targetPlayer) {
		
		if(sender.equalsIgnoreCase(targetPlayer)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isAdminBountyValid(String bountyString) {
		try {
			int bountyAmount = Integer.valueOf(bountyString);
			if(bountyAmount < 0) {
				return false;
			}
			else {
				return true;
			}
		}
		catch(NumberFormatException error){
			System.out.println("ERROR amount not number or less than 0 \n" + error);
			return false;
		}
	}
	public static boolean isPlayerBountyValid(String bountyString) {
		
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
			System.out.println("ERROR amount not number or greater to or less than 0 \n" + error);
			return false;
		}
	
	}
	
	public static boolean hasEnoughMoney(String senderName, int bountyAmount, DatabaseConnection dbConnection, 
										TNEAPI tneAPI) {
		BigDecimal balance = BigDecimal.ZERO;
		balance = tneAPI.getHoldings(senderName);
		
		if(balance.compareTo(BigDecimal.valueOf(bountyAmount)) >= 0) {
			
			return true;
		}
		
		return false;
	}
}
