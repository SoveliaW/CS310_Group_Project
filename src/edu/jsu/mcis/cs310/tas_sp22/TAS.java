package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        try{
            String query = "SELECT * FROM tas_sp22_v1.event WHERE timestamp =?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,id);
         
            boolean pstmtExe = pstmt.execute();

            if (pstmtExe) {

                ResultSet resultset = pstmt.getResultSet();

                while(resultset.next()) {
                    params.put("id", String.valueOf(id));
                    params.put("terminalid", String.valueOf(resultset.getInt("terminalid")));
                    params.put("eventtypeid",String.valueOf(resultset.getInt("eventtypeid")));
                    params.put("timestamp", resultset.getTimestamp("timestamp").toLocalDateTime().toString());
                    params.put("badgeid", resultset.getString("badgeid"));
                   
                }
            }
          
        }
        catch(Exception e) {
            e.printStackTrace(); 
        }
        
    }
}