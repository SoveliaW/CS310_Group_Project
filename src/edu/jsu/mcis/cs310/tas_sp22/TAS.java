package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;


public class TAS {

    public static void main (String[] args) throws SQLException {
        
        TASDatabase db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
        if (db.isConnected()){
            System.err.println("Connected Successfully!");
        }
    }
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift)
            
    {
        LocalDateTime time;
        ArrayList <Punch>p1 = dailypunchlist;
        for (Punch p: p1 ){
            
            time = p.getAdjustedTimestamp();
        }
           
        int min = 0;
        min = Math.abs((int)MINUTES.between(, ));
        System.err.println("This is the Punches for that day" +dailypunchlist);
        
        return min;
    }
}
