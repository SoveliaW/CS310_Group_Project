package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.SQLException;


public class TAS {

    public static void main (String[] args) throws SQLException {
        
        TASDatabase db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
        if (db.isConnected()){
            
            System.err.println("Connected Successfully!");
            Employee e1 = db.getEmployee(10);
            System.out.println(e1);
            Badge b1 = db.getBadge("1B2052DE");
            Employee q1 = db.getEmployee(b1);
            System.out.println(q1);
        }
    }
}