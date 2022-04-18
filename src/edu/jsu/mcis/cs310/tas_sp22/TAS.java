package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.Date;
import java.util.HashMap;
import org.json.simple.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;



public class TAS {

     private TASDatabase db;
     
    public static void main (String[] args) throws SQLException {
        
        TASDatabase db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
        if (db.isConnected()){
            System.err.println("Connected Successfully!");
        }
    }
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        int lunch = 0;
        int min = 0;
        int lunch_added = Math.abs((int)MINUTES.between(shift.getLunchstart(), shift.getLunchstop()));
        
        ArrayList <Punch> p1 = dailypunchlist; 
        
        if (dailypunchlist.size() >= 2){
            LocalTime time_in = p1.get(0).getAdjustedTimestamp();
            
            LocalTime time_out = p1.get(1).getAdjustedTimestamp();
            
            min = Math.abs((int)MINUTES.between(time_in,time_out));
            if (min > shift.getLunchthreshold() ){ 
                min = min - lunch_added;
            }
                
            if (dailypunchlist.size() > 2){
                LocalTime time_in_lunch = p1.get(2).getAdjustedTimestamp();
                
                LocalTime time_outforday = p1.get(3).getAdjustedTimestamp();
               
                lunch = Math.abs((int)MINUTES.between(time_in_lunch,time_outforday));
            }
        }
        else{
            min = 0;
        }
        
        min = min + lunch;
        return min;
    }
    
    public  static int calculatePayPeriodTotalMinutes(ArrayList<Punch> punchlist, Shift shift) {
        int lunch = 0;
        int total = 0;
        int days_mins = 0;
        int min = 0;
        
        
        int lunch_added = Math.abs((int)MINUTES.between(shift.getLunchstart(), shift.getLunchstop()));
        
       // so for every date I need to get a new punchlist 
     
       
            
            ArrayList <Punch> p1 =punchlist;
            
            if (punchlist.size() >= 2){
                LocalTime time_in = p1.get(0).getAdjustedTimestamp();

                LocalTime time_out = p1.get(1).getAdjustedTimestamp();

                min = Math.abs((int)MINUTES.between(time_in,time_out));
                if (min > shift.getLunchthreshold() ){ 
                    min = min - lunch_added;
                }

                if (punchlist.size() > 2){
                    LocalTime time_in_lunch = p1.get(2).getAdjustedTimestamp();

                    LocalTime time_outforday = p1.get(3).getAdjustedTimestamp();

                    lunch = Math.abs((int)MINUTES.between(time_in_lunch,time_outforday));
                }
            
                 else{
                 min = 0;
                }
            total = days_mins;
            days_mins = min + lunch;
            days_mins = total + days_mins;
        
        }
        
        return min;
    }
    
    public static String getPunchListAsJSON(ArrayList<Punch> dailypunchlist){
         
        String results = "";
        
        ArrayList <HashMap<String, String>> jsonData = new ArrayList<>();
 
        for(Punch punch : dailypunchlist){
         
            HashMap<String, String> punchData = new HashMap<>();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
            
            punchData.put("originaltimestamp", punch.getTimestamp().toUpperCase());
            punchData.put("badgeid", String.valueOf(punch.getBadge().getId()));
            punchData.put("adjustedtimestamp", punch.getAdjustedLocalDateTime().format(dtf).toUpperCase());        
            punchData.put("adjustmenttype", String.valueOf(punch.getAdjustmenttype()));
            punchData.put("terminalid", String.valueOf(punch.getTerminalid()));
            punchData.put("punchtype", String.valueOf(punch.getPunchtypeid()));
            punchData.put("id", String.valueOf(punch.getId()));
            
            jsonData.add(punchData);
        }
        String json = JSONValue.toJSONString(jsonData);
        
        return json;
    } 
    
    public static int TotalMinutesForPayperiod (ArrayList<Punch> dailypunchlist, Shift shift)
    {
        int totalMinutesWorked = 0;
        int totalWithLunch = 0;
        int startHours = 0;
        int startMinute = 0;
        int stopMinute = 0;
        int stopHours = 0;
        boolean pair = false;
        LocalTime punches;
        int lunchDuration = (int) shift.getLunchDuration();
        int calculations = 0;
        
        for (Punch punch: dailypunchlist){
            if (punch.getPunchtype() == PunchType.CLOCK_IN || punch.getPunchtype() == PunchType.CLOCK_OUT){
                
                if (punch.getPunchtype() == PunchType.CLOCK_IN){
                    pair = false;
                }
                if (punch.getPunchtype() == PunchType.CLOCK_OUT){
                    pair = true;
                }
            }
            if (pair == false){
                punches = punch.getAdjustedTimestamp();
                startHours = punches.getHour();
                startMinute = punches.getMinute();
            }
            else if (pair){
               punches = punch.getAdjustedTimestamp();
                stopHours = punches.getHour();
                stopMinute = punches.getMinute();
                totalWithLunch = (( stopHours - startHours) *  + (stopMinute - startMinute));
                
                if(totalWithLunch > shift.getLunchthreshold())
                {
                    calculations = totalWithLunch - lunchDuration;
                    totalMinutesWorked = totalMinutesWorked + calculations;
                }
                if(totalWithLunch <= shift.getLunchthreshold()){
                    calculations = ((stopHours - startHours) * 60) + (stopMinute - startMinute);
                    totalMinutesWorked = totalMinutesWorked + calculations;
                }
            }
        }
        return totalMinutesWorked;
    }
    public static Double calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s){
       int minutesScheduled = s.getTotalScheduledHours();
       int minutesWorked = TotalMinutesForPayperiod(punchlist, s);
       
       double absenteeism = (100.00 - ((double)(minutesWorked / (double)minutesScheduled)) *100.00);       
            
        return absenteeism;
    }
    
    public static String getPunchListPlusTotalsAsJson(ArrayList<Punch> punchlist, Shift s){
        HashMap<String, String> output = new HashMap<>();
        String punchListAsJson = getPunchListAsJSON(punchlist);
        
        output.put("totalminutes", String.valueOf(calculateAbsenteeism(punchlist, s)));
        output.put("absenteeism", String.format("%.02f", calculateAbsenteeism(punchlist, s)) + "%");
        JSONObject obj = (JSONObject) JSONValue.parse(JSONValue.toJSONString(output));
        obj.put("punchlist", JSONValue.parse(punchListAsJson));
        
        return JSONValue.toJSONString(obj);
    }
    
}
