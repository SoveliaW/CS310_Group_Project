package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.SQLException;


public class TAS {

    public static void main (String[] args) throws SQLException {
        
        TASDatabase db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
        if (db.isConnected()){
            
            System.err.println("Connected Successfully!");
            
        Badge b1 = db.getBadge("B6902696");
        Badge b2 = db.getBadge("76E920D9");
        Badge b3 = db.getBadge("4382D92D");
        
		
        /* Retrieve Shift Rulesets from Database */

        Shift s1 = db.getShift(b1);
        Shift s2 = db.getShift(b2);
        Shift s3 = db.getShift(b3);
		
        /* Compare to Expected Values */

        System.out.println("Em 1: " + s1.toString());
        System.out.println("Em 2: " + s2.toString());
        System.out.println("Em 3: " + s3.toString());
            
            }

        }
}