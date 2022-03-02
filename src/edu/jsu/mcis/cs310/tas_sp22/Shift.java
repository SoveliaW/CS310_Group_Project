package edu.jsu.mcis.cs310.tas_sp22;
import java.sql.*;
import java.sql.Connection;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;



public class Shift {
    private String id, description, shiftstart, shiftstop, roundinterval, 
            graceperiod, dockpenalty, lunchstart, lunchstop, lunchthreshold;
    private long shiftmin, lunchmin;
    HashMap <String, String> Shift_copy;
    
        
   public Shift(HashMap <String, String> shif){
       
       
        this.id = shif.get("id");
        this.description = shif.get("description");
        this.shiftstart = shif.get("shiftstart");
        this.shiftstop = shif.get("shiftstop");
        this.roundinterval = shif.get("roundinterval");
        this.graceperiod = shif.get("graceperiod");
        this.dockpenalty = shif.get("dockpenalty");
        this.lunchstart = shif.get("lunchstart");
        this.lunchstop = shif.get("lunchstop");
        this.lunchthreshold = shif.get("lunchthreshold");
        
        this.Shift_copy = shif;
        
        // Formatting the time for shift and lunch
        
        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH.mm");
        
        LocalTime sstart = LocalTime.parse(shiftstart);
        shiftstart = sstart.toString();
        LocalTime sstop = LocalTime.parse(shiftstop);
        shiftstop = sstop.toString();
        LocalTime lstart = LocalTime.parse(lunchstart);
        lunchstart = lstart.toString();
        LocalTime lstop = LocalTime.parse(lunchstop);
        lunchstop = lstop.toString();
        
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
