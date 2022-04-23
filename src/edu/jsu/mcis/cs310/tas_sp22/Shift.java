package edu.jsu.mcis.cs310.tas_sp22;

import java.time.LocalTime;
import java.util.HashMap;

public class Shift {
    
    private String description;
    private int roundinterval, graceperiod, dockpenalty, lunchthreshold, id, 
            TotalScheduledHours, LunchDuration, scheduledDays;
    private LocalTime shiftstart, shiftstop, lunchstart, lunchstop;
    private long shiftmin, lunchmin;
    

   public Shift(HashMap <String, String> params){

      
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
       
        
        this.shiftmin = java.time.Duration.between(shiftstart,shiftstop).toMinutes();
        
        this.lunchmin = java.time.Duration.between(lunchstart,lunchstop).toMinutes();
        TotalScheduledHours = 40;
        
        LunchDuration = 30;
        
        this.scheduledDays = 5;
        
    }

    public int getLunchDuration() {
        return LunchDuration;
    }

    public int getTotalScheduledHours() {
        int TotalScheduledHours = 0;
        
        for (int i = 0; i < scheduledDays; i++){
            TotalScheduledHours += (shiftmin - lunchmin);
        }
        
        return TotalScheduledHours;
    }

    public String getDescription() {
        return description;
    }

    public int getRoundinterval() {
        return roundinterval;
    }

    public int getGraceperiod() {
        return graceperiod;
    }

    public int getDockpenalty() {
        return dockpenalty;
    }

    public int getLunchthreshold() {
        return lunchthreshold;
    }

    public int getId() {
        return id;
    }

    public LocalTime getShiftstart() {
        return shiftstart;
    }

    public LocalTime getShiftstop() {
        return shiftstop;
    }

    public LocalTime getLunchstart() {
        return lunchstart;
    }

    public LocalTime getLunchstop() {
        return lunchstop;
    }

    public long getShiftmin() {
        return shiftmin;
    }

    public long getLunchmin() {
        return lunchmin;
    }

    public int getScheduledDays() {
        return scheduledDays;
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
