package edu.jsu.mcis.cs310.tas_sp22;
import java.util.Date.*;
import java.sql.Timestamp.*;
import java.time.LocalDateTime;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class Punch {
    
   private int terminalid, id, eventtypeid;
   private PunchType punchtypeid;
   private String badgeid, adjustmenttype;
   private LocalDateTime timestamp;
   private Badge badge;
   public Punch(HashMap <String, String> params){

        this.terminalid = Integer.parseInt(params.get("terminalid"));
        this.id = Integer.parseInt(params.get("id"));
        this.punchtypeid = PunchType.values()[Integer.parseInt(params.get("eventtypeid"))];
        this.badgeid = params.get("badgeid");
        this.adjustmenttype = null;
        this.timestamp = LocalDateTime.parse(params.get("timestamp"));
        
        String dayofweek = timestamp.getDayOfWeek().toString();
        
    }

    public Punch(int terminalid, Badge badge, int eventtypeid) {
        this.terminalid = terminalid;
        this.eventtypeid = eventtypeid;
        this.badge = badge;
    }
    
    public Badge getBadge(){
        return badge;
    }
    
    public LocalDateTime getOriginalTimestamp(){
        return badge;
    }
            
    public int getTerminalid(){
        return terminalid;
    }
    
    public PunchType getPunchtype(){
        return badge;
    }
    
    public String printOriginal() {
        
        // "#D2C39273 CLOCK IN: WED 09/05/2018 07:00:07"
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        
        StringBuilder s = new StringBuilder();
        //?????? missing how to get the clock in or out variable
        s.append("#").append(badgeid).append(' ').append(punchtypeid);
        s.append(": ").append(timestamp.format(dtf));

        return s.toString().toUpperCase();
    }
  
}
