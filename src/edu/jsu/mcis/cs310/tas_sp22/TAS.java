package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.SQLException;


public class TAS {

    public static void main (String[] args) throws SQLException {
        
        Database db = new Database();
        
        
        
        if (db.isConnected()){
            
            System.err.println("Connected Successfully!");

            
        }
}
}