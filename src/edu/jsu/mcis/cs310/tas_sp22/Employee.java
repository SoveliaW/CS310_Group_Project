package edu.jsu.mcis.cs310.tas_sp22;

import java.time.LocalDate;
import java.util.HashMap;

public class Employee {
    
    private String badgeid, firstname, middlename, lastname, inactive;
    private int employeetypeid, deptid, shiftid, id;
    private LocalDate active;
    
    public Employee(HashMap <String, String> params){
       
        this.id = Integer.parseInt(params.get("id"));
        this.badgeid = params.get("badgeid");
        this.firstname = params.get("firstname");
        this.middlename = params.get("middlename");
        this.lastname = params.get("lastname");
        this.employeetypeid = Integer.parseInt(params.get("employeetypeid"));
        this.deptid = Integer.parseInt(params.get("departmentid"));
        this.shiftid = Integer.parseInt(params.get("shiftid"));
        this.active = LocalDate.parse(params.get("active"));
        this.inactive = "none";
    }

    public String getBadgeid() {
        return badgeid;
    }

    public int getEmployeetypeid() {
        return employeetypeid;
    }

    public int getDeptid() {
        return deptid;
    }

    public int getShiftid() {
        return shiftid;
    }

    public int getId() {
        return id;
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
