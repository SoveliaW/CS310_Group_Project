package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.SQLException;


public class TAS {

    public static void main (String[] args) throws SQLException {
        
        TASDatabase db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
        if (db.isConnected()){
            
            System.err.println("Connected Successfully!");
            
        Badge b1 = db.getBadge("1B2052DE");
        Badge b2 = db.getBadge("0886BF12");
        Badge b3 = db.getBadge("29C03912");
		
        Employee e1 = db.getEmployee(b1);
        Employee e2 = db.getEmployee(b2);
        Employee e3 = db.getEmployee(b3);
       
        System.out.println("Em 1: " + e1.toString());
        System.out.println("Em 2: " + e2.toString());
        System.out.println("Em 3: " + e3.toString());
            
            }

        }
}