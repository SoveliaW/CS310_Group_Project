package edu.jsu.mcis.cs310.tas_sp22;
import java.util.Date.*;
import java.sql.Timestamp.*;
import java.time.LocalDateTime;
import java.sql.*;
import java.sql.Connection;
import java.util.HashMap;

public class Punch { 
   private String TerminalId;
   private String PunchTypeId;
   private String eventtypeid;
   private String timestamp;
   private String badgeid;
   HashMap <String, String> Pun_copy;
   
   public Punch(HashMap <String, String> Pun){
        this.TerminalId = Pun.get("TerminalId");
        this.PunchTypeId = Pun.get("PunchTypeId;");
        this.eventtypeid = Pun.get("eventtypeid");
        this.timestamp = Pun.get("timestamp");
        this.badgeid = Pun.get("badgeid");
         
        this.Pun_copy = Pun;
    }
   
   //Timestamp timestamp2 = new Timestamp(System.currentTimeMillis());
   Timestamp timestamp2 = new Timestamp(new java.util.Date().getTime());
   
   //Timestamp timestamp2 = rs.getTimestamp(columnIndex);
   //System.out.println("timestamp: " + timestamp.toString());
   
   //LocalDateTime local = timestamp2.toLocalDateTime();
   //local = local.withSecond(0).withNano(0);
   //java.sql.Timestamp ts3 = java.sql.Timestamp.valueOf(local);
   

   public enum PunchType {
    CLOCK_OUT("CLOCK OUT"),
    CLOCK_IN("CLOCK IN"),
    TIME_OUT("TIME OUT");
    private final String description;
    private PunchType(String d) { description = d; }
    @Override
    public String toString() { return description; }
}

   

    public String getEventtypeid() {
        return eventtypeid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getBadgeid() {
        return badgeid;
    }

    

    public String getTerminalId() {
        return TerminalId;
    }

    public String getPunchTypeId() {
        return PunchTypeId;
    }
  
}
