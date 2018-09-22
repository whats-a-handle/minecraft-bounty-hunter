package com.whatsahandle.bountyhunter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
public class DatabaseConnection {
	private Connection connection;
	private String host, database, username, password, tableName;
	private int port;
	
	//placeholders
	//Long-term connect to a MySql DB defined in config.yml
	public DatabaseConnection() {
		this.host = "localhost";
		this.port = 3306;
		this.database = "test_bounties";
		this.username = "root";
		this.password = "new-password";
		this.tableName = "bounty_table";
	}
	
	public Connection getConnection() {
		return this.connection;
	}
	public String getHost() {
		return this.host;
	}
	public int getPort() {
		return this.port;
	}
	public String getDatabase() {
		return this.database;
	}
	public String getUsername() {
		return this.username;
	}
	public String getPassword() {
		return this.password;
	}
	public String getTableName() {
		return this.tableName;
	}
	public boolean isConnected() {
		try {
			boolean connected = false;
			connected = this.connection.isValid(10000);
			
			System.out.println("BountyHunter Database connected? : " + connected);
			return connected;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public void openConnection() throws SQLException, ClassNotFoundException {
		
	    if (this.connection != null && !this.connection.isClosed()) {
	        return;
	    }

	    synchronized (this) {
	        if (this.connection != null && !this.connection.isClosed()) {
	            return;
	        }
	        Class.forName("com.mysql.jdbc.Driver");
	        this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.username, this.password);
	    }
	}
	
	public boolean authenticate() {
		List<Exception> test = new ArrayList<Exception>();
		try {
			this.openConnection();
			this.isConnected();
		} catch (ClassNotFoundException e) {
			
			test.add(e);
		} catch (SQLException e) {
			test.add(e);
		}
		
		if(!test.isEmpty()) {
			System.out.println("=======ERROR DURING BOUNTY HUNTER SETUP=======");
			for(Exception error : test) {
				System.out.println(error.getMessage());
			}
			System.out.println("=======END BOUNTY HUNTER LOG=======");
			return false;
		}
		
		return true;
		
	}
	
	/*
	 *  id int unsigned auto_increment not null,
	    playerName varchar(32) not null,
	    bountyAmount int,
	    primary key (id)
	 */
	public void createTable(){
	    String createTableString = "CREATE TABLE IF NOT EXISTS " + this.getTableName()
	            + "  (id int unsigned auto_increment not null,"
	            + "   playerName varchar(32) not null,"
	            + "   bountyAmount int,"
	            + "   primary key (id))";
	    try {
	    	Statement createTableStatement = this.connection.createStatement();
	    	createTableStatement.execute(createTableString);
	    }
	    catch(SQLException error) {
	    	System.out.println("ERROR ON TABLE CREATION: \n" + error.getMessage());
	    }
  
	}
	
	public int getIdFromPlayerName(String playerName) {
		int playerId = 0;
		try {
			String searchQuery = "SELECT id, playerName FROM bounty_table WHERE playerName=\'" + playerName +"\'";
			Statement statement = this.connection.createStatement();
			ResultSet result = statement.executeQuery(searchQuery);
			while(result.next()) {
				playerId = result.getInt("id");
			}
		}
		catch(SQLException error) {
			System.out.println("ERROR DURING PLAYER ID LOOKUP: \n" + error);
		}	
		return playerId;
	}
	public void setPlayerBounty(String playerName, int amount) {
		
		try {
			String queryString = "INSERT INTO " + this.getTableName() + "(id,playerName,bountyAmount) " +
					"VALUES (" + getIdFromPlayerName(playerName)+ ",\'" + playerName +"\'," + amount +") " +
							"ON DUPLICATE KEY UPDATE " + "bountyAmount=" + amount;
			Statement statement = this.connection.createStatement();
			statement.execute(queryString);
		}
		catch(SQLException error) {
			System.out.println("ERROR DURING BOUNTY SET: \n" + error);
		}
		
		//Statement statement = this.connection.createStatement()
	}
	
	public int getPlayerBounty(String playerName) {
		int bountyAmount = 0;
		try {
			String queryString = "SELECT id, playerName, bountyAmount FROM " + this.getTableName() +
					" WHERE playerName=\'" + playerName + "\'";
			
			Statement queryStatement = this.connection.createStatement();
			ResultSet result = queryStatement.executeQuery(queryString);
			
			while(result.next()) {
				bountyAmount = result.getInt("bountyAmount");
			}
			
		}
		catch(SQLException error) {
			System.out.println("ERROR DURING BOUNTY LOOKUP: \n " + error);
		}
		return bountyAmount;
	}
}
