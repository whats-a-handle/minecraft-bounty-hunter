package com.whatsahandle.bountyhunter;

import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.tnemc.core.TNE;
import net.tnemc.core.common.api.TNEAPI;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;



public class KillListener  implements Listener
{
	TNEAPI tneAPI = TNE.instance().api();  
	
	@EventHandler
	public void onKill(PlayerDeathEvent event){
		
		DatabaseConnection dbConnection = new DatabaseConnection();
		dbConnection.authenticate();
    	dbConnection.createTable();
    	
		Player killedPlayer = event.getEntity();
		Player killer = event.getEntity().getKiller();
		
		
		int bountyAmount = 0;

		bountyAmount = dbConnection.getPlayerBounty(killedPlayer.getName());
		Bukkit.broadcastMessage("Murderer!");
		if(bountyAmount > 0) {
			
			
			dbConnection.setPlayerBounty(killedPlayer.getName(), 0);
			this.tneAPI.addHoldings(String.valueOf(killer.getUniqueId()),BigDecimal.valueOf(bountyAmount));
			
			killer.sendMessage("You\'ve claimed a bounty of $" + bountyAmount + " !");
			
			Bukkit.broadcastMessage(killer.getName() + " has claimed the bounty on " + killedPlayer.getName() + 
					" for $" + bountyAmount + "!");
			
		}
		
		
		
		
		
	}
}