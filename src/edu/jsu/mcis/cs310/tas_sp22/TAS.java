package edu.jsu.mcis.cs310.tas_sp22;


import java.util.ArrayList;
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
    public static int calculateTotalMinutes(ArrayList<Punch> dailypunchlist, Shift shift)
            
    {
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
    
    public static Double calculateAbsenteeism(ArrayList<Punch> punchlist, Shift s){
        double i = 0;
        return i;
        //Use the getPayPeriodPunchList() 
        /* This method should get the toal minutes accured by the empolyee,
        get the total number of mins they were scheduled to work (use Punch arraylist and shift object.
        We will use the above to calculate the absenteeism percentage
        */
    }
}
