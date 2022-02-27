package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.SQLException;


public class TAS {

    public static void main (String[] args) throws SQLException {
        
        TASDatabase db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
        if (db.isConnected()){
            
            System.err.println("Connected Successfully!");
            
            Badge b1 = db.getBadge("1B2052DE");

            Employee results = db.getEmployee(b1);
            System.out.println(results);
            
            
            }

        }
}