package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.SQLException;


public class TAS {

    public static void main (String[] args) throws SQLException {
        
        TASDatabase db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
        if (db.isConnected()){
            
            System.err.println("Connected Successfully!");

            String results = db.getShift(1);
            System.out.println(results);

        }
}
}