package edu.jsu.mcis.cs310.tas_sp22;

import java.util.HashMap;
import org.json.simple.*;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import static java.time.temporal.ChronoUnit.MINUTES;
import java.util.ArrayList;



public class TAS {

     private TASDatabase db;
     
    public static void main (String[] args) throws SQLException {
        
        TASDatabase db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
        if (db.isConnected()){
            System.err.println("Connected Successfully!");
        }
    }
    
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift) {
        
        int min = 0;
        int total_min = 0;
        int lunch = Math.abs((int)MINUTES.between(shift.getLunchstart(), shift.getLunchstop()));;
        LocalTime time_in = null;
        LocalTime time_out = null;
        boolean pair = false;
        
        for (Punch dp : dailypunchlist){

            if (dp.getPunchtype() == PunchType.CLOCK_IN || dp.getPunchtype() == PunchType.CLOCK_OUT){

                if (dp.getPunchtype() == PunchType.CLOCK_IN){
                    pair = false;
                }
                
                if (dp.getPunchtype() == PunchType.CLOCK_OUT){
                    pair = true;
                }
            }

            if (pair == false){
                time_in = dp.getAdjustedLocalDateTime().toLocalTime();
            }
            
            else if (pair == true){
                time_out = dp.getAdjustedLocalDateTime().toLocalTime(); 
                min = Math.abs((int)MINUTES.between(time_in, time_out));
                
                if (min > shift.getLunchthreshold()){
                    min = min - lunch;
                    total_min = total_min + min;     
                }
                
                else if (min <= shift.getLunchthreshold()){
                    total_min = total_min + min;     
                }
            }
        }
        
        return total_min;
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
    
    public static double calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s){
        int minScheduled = s.getTotalScheduledHours();
        int minWorked = calculateTotalMinutes(punchlist, s);
       
        double absenteeism = (100.00 - ((double)minWorked / (double)minScheduled) * 100.00);
        
        return absenteeism;
    }
    
    public static String getPunchListPlusTotalsAsJSON(ArrayList<Punch> punchlist, Shift s){
        HashMap<String, String> output = new HashMap<>();
        String punchListAsJson = getPunchListAsJSON(punchlist);
        
        output.put("totalminutes", String.valueOf(calculateAbsenteeism(punchlist, s)));
        output.put("absenteeism", String.format("%.02f", calculateAbsenteeism(punchlist, s)) + "%");
        JSONObject obj = (JSONObject) JSONValue.parse(JSONValue.toJSONString(output));
        obj.put("punchlist", JSONValue.parse(punchListAsJson));
        
        return JSONValue.toJSONString(obj);
    }
    
}
