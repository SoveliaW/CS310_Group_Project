package edu.jsu.mcis.cs310.tas_sp22;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.HashMap;

public class Employee {
    private String badgeid,firstname,middlename,lastname,inactive;
    private int employeetypeid,deptid,shiftid,id;
    private LocalDate active;
    HashMap <String, String> empl_copy;
    
    public Employee(HashMap <String, String> Results){
     this.id = Integer.parseInt(Results.get("id"));
     this.badgeid = Results.get("badgeid");
     this.firstname = Results.get("firstname");
     this.middlename = Results.get("middlename");
     this.lastname = Results.get("lastname");
     this.employeetypeid = Integer.parseInt(Results.get("employeetypeid"));
     this.deptid = Integer.parseInt(Results.get("departmentid"));
     this.shiftid = Integer.parseInt(Results.get("shiftid"));
     this.active = LocalDate.parse(Results.get("active"));
     this.inactive = "none";
        
     this.empl_copy = Results;
        
        //active = active.substring(0, 10);
        
     if (inactive == "null") {
         inactive = "none";
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("#").append(badgeid).append(" (").append(lastname).append(", ");
        s.append(firstname).append(" ").append(middlename).append("): ").append("employeetypeid: ");
        s.append(employeetypeid).append(", departmentid: ").append(deptid).append(", shiftid: ");
        s.append(shiftid).append(", active: ").append(active).append(", inactive: ").append(inactive);
        return s.toString();
    }
    
    
      
}
