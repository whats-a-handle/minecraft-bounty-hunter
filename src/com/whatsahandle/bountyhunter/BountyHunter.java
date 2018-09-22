package com.whatsahandle.bountyhunter;

import org.bukkit.Bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class BountyHunter extends JavaPlugin  {
		DatabaseConnection dbConnection = new DatabaseConnection();
		
	    @Override
	    public void onEnable() {
	    	
	    	getServer().getPluginManager().registerEvents(new KillListener(), this);
	    	
	    	dbConnection.authenticate();
	    	dbConnection.createTable();
	    	
	    	this.getCommand("addBounty").setExecutor(new AddBounty());
	        this.getCommand("setBounty").setExecutor(new SetBounty());
			this.getCommand("getBounty").setExecutor(new GetBounty());
			
			Bukkit.broadcastMessage("Bounty Hunter Plugin enabled");
	    }
	    
	    @Override
	    public void onDisable() {
	    }
	   
}
