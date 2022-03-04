package edu.jsu.mcis.cs310.tas_sp22;
import java.sql.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;


public class Shift {
    
    private String description;
    private int roundinterval, graceperiod, dockpenalty, lunchthreshold, id;
    private LocalTime shiftstart, shiftstop,lunchstart, lunchstop;
    private long shiftmin,lunchmin;
    
        
   public Shift(HashMap <String, String> params){
       System.err.println("Shift ID: " + params.get("id"));
        this.id = Integer.parseInt(params.get("id"));
        this.description = params.get("description");
        this.shiftstart = LocalTime.parse(params.get("shiftstart"));
        this.shiftstop = LocalTime.parse(params.get("shiftstop"));
        this.roundinterval = Integer.parseInt(params.get("roundinterval"));
        this.graceperiod = Integer.parseInt(params.get("graceperiod"));
        this.dockpenalty = Integer.parseInt(params.get("dockpenalty"));
        this.lunchstart = LocalTime.parse(params.get("lunchstart"));
        this.lunchstop = LocalTime.parse(params.get("lunchstop"));
        this.lunchthreshold = Integer.parseInt(params.get("lunchthreshold"));
       
        // The minutes between the shift start and stop
        this.shiftmin = java.time.Duration.between(shiftstart,shiftstop).toMinutes();
        // The minutes between the lunch start and stop
        this.lunchmin = java.time.Duration.between(lunchstart,lunchstop).toMinutes();
        

    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
         
        s.append(description).append(": ").append(shiftstart).append(" - ");
        s.append(shiftstop).append(" (").append(shiftmin).append(" minutes)").append("; Lunch: ");
        s.append(lunchstart).append(" - ").append(lunchstop).append(" (").append(lunchmin).append(" minutes)");

        return s.toString();
    }
        
}
