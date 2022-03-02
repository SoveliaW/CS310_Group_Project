package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Employee {
    private String id,badgeid,fname,mname,lname,empltype,deptid,shiftid,active,inactive;
    HashMap <String, String> empl_copy;
    
    public Employee(HashMap <String, String> empl){
        this.id = empl.get("id");
        this.badgeid = empl.get("badgeid");
        this.fname = empl.get("firstname");
        this.mname = empl.get("middlename");
        this.lname = empl.get("lastname");
        this.empltype = empl.get("employeetypeid");
        this.deptid = empl.get("departmentid");
        this.shiftid = empl.get("shiftid");
        this.active = empl.get("active");
        this.inactive = empl.get("inactive");
        
        this.empl_copy = empl;
        
        active = active.substring(0, 10);
        
        if (inactive == "null") {
            inactive = "none";
        }
        
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("#").append(badgeid).append(" (").append(lname).append(", ");
        s.append(fname).append(" ").append(mname).append("): ").append("employeetypeid: ");
        s.append(empltype).append(", departmentid: ").append(deptid).append(", shiftid: ");
        s.append(shiftid).append(", active: ").append(active).append(", inactive: ").append(inactive);
        return s.toString();
    }
    
    
      
}
