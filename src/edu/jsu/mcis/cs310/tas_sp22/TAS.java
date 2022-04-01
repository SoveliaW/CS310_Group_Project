package edu.jsu.mcis.cs310.tas_sp22;


import java.util.ArrayList;
import java.util.HashMap;
import org.json.simple.*;
import java.sql.SQLException;
import java.time.LocalTime;
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
        int lunch_added = 30;
        
        ArrayList <Punch> p1 = dailypunchlist; 
        
        if (dailypunchlist.size() >= 2){
            LocalTime time_in = p1.get(0).getAdjustedTimestamp();
            
            LocalTime time_out = p1.get(1).getAdjustedTimestamp();
            
            min = Math.abs((int)MINUTES.between(time_in,time_out));
            if (min >360 ){
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
         ArrayList <String>jsonData  = new ArrayList<>();
         HashMap<String, String> PunchData = new HashMap<>();

         
         for(Punch punch :dailypunchlist){
       
           PunchData.put("badgeid:", String.valueOf(punch.getBadge()));
           PunchData.put("terminalid:", String.valueOf(punch.getTerminalid()));
           PunchData.put("punchtypeid:", String.valueOf(punch.getPunchtypeid()));
           PunchData.put("id:", String.valueOf(punch.getId()));
           PunchData.put("adjustmenttype:", String.valueOf(punch.getAdjustmenttype()));
           PunchData.put("originaltimestamp:", String.valueOf(punch.getOriginalTimestamp()));
           
            //have some error when getting the original timestamp
           //System.err.println(punch.getOriginalTimestamp());
           
           
           PunchData.put("adjustedtimestamp:", String.valueOf(punch.getAdjustedTimestamp()));
           //System.err.println(punch.getAdjustedTimestamp());
           //System.err.println("This is the original timestamp: "+ punch.getOriginalTimestamp());
           
           jsonData.add("originaltimestamp"+":"+PunchData.get("originaltimestamp:"));
           jsonData.add("badgeid"+":"+PunchData.get("badgeid:"));
           jsonData.add("adjustedtimestamp"+":"+PunchData.get("adjustedtimestamp:"));
           jsonData.add("adjustmenttype"+":"+PunchData.get("adjustmenttype:"));
           jsonData.add("terminalid"+":"+PunchData.get("terminalid:"));
           jsonData.add("id"+":"+PunchData.get("id: "));

           
         }
         
         //System.err.println("This is the original timestamp: "+ punch.getOriginalTimestamp());
         String json = JSONValue.toJSONString(jsonData);
         System.err.println("This is the JasonData: "+json);
         return json;
    } 
}
