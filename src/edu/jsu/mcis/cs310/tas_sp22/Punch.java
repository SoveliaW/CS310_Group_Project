package edu.jsu.mcis.cs310.tas_sp22;
import java.util.Date.*;
import java.sql.Timestamp.*;
import java.time.LocalDateTime;
import java.sql.*;
import java.util.HashMap;

public class Punch { 
   private int terminalid,id;
   private PunchType PunchTypeid;
   private String badgeid,adjustmenttype;
   private LocalDateTime timestamp;
   private Badge badge;
   HashMap <String, String> Pun_copy;
   
   public Punch(HashMap <String, String> Results,Badge emptybadge){
        this.terminalid = Integer.parseInt(Results.get("terminalid"));
        this.PunchTypeid = PunchType.values()[Integer.parseInt(Results.get("eventtypeid"))];
        this.id=0;
        this.badgeid = null;
        this.adjustmenttype=null;
        this.badge=emptybadge;
        if (Results.get("timestamp")!=null){ // if no timestamp it is a new punch
            //so collect current date&time and use for punch
         this.timestamp=LocalDateTime.now();
        }
   
   
      //Timestamp timestamp2 = new Timestamp(new java.util.Date().getTime());
    }
public String printOriginal() {
         StringBuilder s = new StringBuilder();
        
        return s.toString();
    }
  
}
