package edu.jsu.mcis.cs310.tas_sp22;
import java.sql.*;
import java.sql.Connection;
import java.util.HashMap;



public class Shift {
    private String id,description,shiftstart,shiftstop,roundinterval,graceperiod
            ,dockpenalty,lunchstart,lunchstop,inactive;
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
        this.inactive = shif.get("inactive");
        
        this.Shift_copy = shif;
        
    }
    @Override
       public String toString() {
         StringBuilder s = new StringBuilder();
    
         s.append(description).append(":").append(" ").append(shiftstart).append(" - ");
         s.append(shiftstop).append(" (").append("????????????").append(")").append("; ");
         s.append(lunchstart).append(" - ").append(lunchstop).append(" (").append("????????????").append(")");
        return s.toString();
        }
}
