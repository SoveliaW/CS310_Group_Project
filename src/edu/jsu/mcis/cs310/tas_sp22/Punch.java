package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.Timestamp.*;
import java.time.LocalDateTime;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Punch {
    
   private int terminalid, id, eventtypeid;
   private PunchType punchtypeid;
   private String badgeid, adjustmenttype;
   private LocalDateTime timestamp;
   private Badge badge;
   private ArrayList DailyPunchList;
   private LocalTime localtime;
   LocalTime NewClockinTime;
   
   public Punch(HashMap <String, String> params,Badge badge){
        this.id = Integer.parseInt(params.get("id"));
        this.terminalid = Integer.parseInt(params.get("terminalid"));
        this.badgeid = params.get("badgeid");
        this.timestamp = LocalDateTime.parse(params.get("timestamp"));
        this.punchtypeid = PunchType.values()[Integer.parseInt(params.get("eventtypeid"))];
        this.badge = badge;
        System.err.println("Error accured hereon next line");
        this.localtime = LocalTime.parse(params.get("timestamp"));
        System.err.println("Error did not accur");
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

    public LocalTime getLocaltime() {
        return localtime;
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
    public PunchType getPunchtypeid(){
        return punchtypeid;
    }
    
     public void adjust(Shift s){
      LocalTime start =s.getShiftstart();
      LocalTime stop = s.getShiftstop();
      LocalTime lunch =s.getLunchstart();
      LocalTime lunchend =s.getLunchstop();
      int roundinterval = s.getRoundinterval();
      int graceperoid =s.getGraceperiod();
      int dockpenalty =s.getDockpenalty();
      int lunchtheshold =s.getLunchthreshold();
      LocalTime Currentpunchtime = getLocaltime();
      System.err.println("Checking to see if logic gate passed ");
      
      if(getEventtypeid() ==1){
          //Check for punch in 
          if (Currentpunchtime.isBefore(start)||Currentpunchtime.isAfter(start)){
              System.err.println("Checking to see if logic gate passed ");
              LocalTime withgraceperoid =start.minus(Duration.ofMinutes(graceperoid));
              LocalTime Plusgraceperoid = start.plus(Duration.ofMinutes(graceperoid));
              if(Currentpunchtime.isBefore(withgraceperoid)||Currentpunchtime.isAfter(Plusgraceperoid)){
                  
                  //outside of bounds for grace peroid
                  
                }
              else{
                    Long Diffrence= MINUTES.between(Currentpunchtime,start);
                  if(Currentpunchtime.isBefore(start)){
                       NewClockinTime = Currentpunchtime.plus(Duration.ofMinutes(Diffrence));
                  }
                  if(Currentpunchtime.isAfter(start)){
                      NewClockinTime = Currentpunchtime.minus(Duration.ofMinutes(Diffrence));
                  }
                 
              }
            }
           
        }  
          
      if(getEventtypeid() == 0){
          //check for punch out
            if (getLocaltime().isBefore(stop)){
              
              
            }
            if(getLocaltime().isAfter(stop)){
              
            }
        }
      
    
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
    public String printAdjusted() {
        
        // "#D2C39273 CLOCK IN: WED 09/05/2018 07:00:07"
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        
        StringBuilder s = new StringBuilder();
        s.append("#").append(badgeid).append(' ').append(punchtypeid);
        s.append(": ").append(NewClockinTime.format(dtf));
        s.append("badge is: "+badge);

        return s.toString().toUpperCase();
  
}
}
