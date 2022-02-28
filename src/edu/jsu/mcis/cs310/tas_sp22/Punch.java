package edu.jsu.mcis.cs310.tas_sp22;
import java.util.Date.*;
import java.sql.Timestamp.*;
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
