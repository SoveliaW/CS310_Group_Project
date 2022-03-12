package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.Timestamp.*;
import java.time.LocalDateTime;
import java.sql.*;

import java.time.format.DateTimeFormatter;
import static java.time.temporal.TemporalQueries.zone;
import java.util.HashMap;

public class Punch {
    
   private int terminalid, id, eventtypeid;
   private PunchType punchtypeid;
   private String badgeid, adjustmenttype;
   private LocalDateTime timestamp;
   private Badge badge;
   
   public Punch(HashMap <String, String> params,Badge badge){
        this.id = Integer.parseInt(params.get("id"));
        this.terminalid = Integer.parseInt(params.get("terminalid"));
        this.badgeid = params.get("badgeid");
        this.timestamp = LocalDateTime.parse(params.get("timestamp"));
        this.punchtypeid = PunchType.values()[Integer.parseInt(params.get("eventtypeid"))];
        this.badge = badge;
        
        
        String dayofweek = timestamp.getDayOfWeek().toString();
        
    }

    public Punch(int terminalid, Badge badge, int eventtypeid) {
        this.terminalid = terminalid;
        this.eventtypeid = eventtypeid;
        this.badge = badge;
        java.sql.Timestamp timestamp1 = new Timestamp(new java.util.Date().getTime());
        LocalDateTime local = timestamp1.toLocalDateTime();
        local = local.withSecond(0).withNano(0);   
        this.timestamp = local;
        
    }

    public Badge getBadge() {
        //System.err.println(badge+"this is what it returns");
        return badge;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public int getEventtypeid(){
        return eventtypeid;
    }
    
   
    public LocalDateTime getOriginalTimestamp(){
        return timestamp;
    }
            
    public int getTerminalid(){
        return terminalid;
    }
    
    public PunchType getPunchtype(){
        punchtypeid = null;
        return punchtypeid;
    }
    
    public String printOriginal() {
        
        // "#D2C39273 CLOCK IN: WED 09/05/2018 07:00:07"
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        
        StringBuilder s = new StringBuilder();
        s.append("#").append(badgeid).append(' ').append(punchtypeid);
        s.append(": ").append(timestamp.format(dtf));
        s.append("badge is: "+badge);

        return s.toString().toUpperCase();
    }
  
}
