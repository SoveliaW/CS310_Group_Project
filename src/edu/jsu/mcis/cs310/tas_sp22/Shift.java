package edu.jsu.mcis.cs310.tas_sp22;
import java.sql.*;
import java.sql.Connection;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;



public class Shift {
    
    private String description;
    private int roundinterval, graceperiod, dockpenalty, lunchthreshold, id;
    private LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    
        
   public Shift(HashMap <String, String> params){
       
       
        this.id = params.get("id");
        this.description = params.get("description");
        this.shiftstart = params.get("shiftstart");
        this.shiftstop = params.get("shiftstop");
        this.roundinterval = params.get("roundinterval");
        this.graceperiod = params.get("graceperiod");
        this.dockpenalty = params.get("dockpenalty");
        this.lunchstart = params.get("lunchstart");
        this.lunchstop = params.get("lunchstop");
        this.lunchthreshold = params.get("lunchthreshold");
        
        this.Shift_copy = params;
        
        // Formatting the time for shift and lunch
        
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
        
        LocalTime sstart = LocalTime.parse(shiftstart);
        this.shiftstart = sstart.toString();
        LocalTime sstop = LocalTime.parse(shiftstop);
        this.shiftstop = sstop.toString();
        LocalTime lstart = LocalTime.parse(lunchstart);
        this.lunchstart = lstart.toString();
        LocalTime lstop = LocalTime.parse(lunchstop);
        this.lunchstop = lstop.toString();
        
        // The minutes between the shift start and stop
        this.shiftmin = java.time.Duration.between(sstart, sstop).toMinutes();
        // The minutes between the lunch start and stop
        this.lunchmin = java.time.Duration.between(lstart, lstop).toMinutes();
        

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
