package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.*;
import java.sql.Connection;
import java.util.HashMap;

public class Employee {
    private int id,empltype,deptid;
    private String badgeid,fname,mname,lname;
    HashMap <String, String> param_copy;
    
    public Employee(HashMap <String, String> param){
        param.put("Id", Integer.toString(id));
        param.put("Badge Id", badgeid);
        param.put("First Name", fname);
        param.put("Middle Name", mname);
        param.put("Last Name", lname);
        param.put("Employee Type Id", Integer.toString(empltype));
        param.put("Department Id", Integer.toString(deptid));
        this.param_copy = param;
    }

    public int getId() {
        return id;
    }

    public int getEmpltype() {
        return empltype;
    }

    public int getDeptid() {
        return deptid;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public String getFname() {
        return fname;
    }

    public String getMname() {
        return mname;
    }

    public String getLname() {
        return lname;
    }

    @Override
    public String toString() {
        return param_copy.toString();   //HashMap to String
    }
    
    
      
}
