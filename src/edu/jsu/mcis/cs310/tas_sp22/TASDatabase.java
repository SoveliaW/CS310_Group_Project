package edu.jsu.mcis.cs310.tas_sp22;
import java.sql.*;
import java.sql.Connection;
import java.util.HashMap;
import java.util.*;

public class TASDatabase {
    
    private final Connection connection;
    
    public TASDatabase(String username, String password, String address) {
        this.connection = openConnection(username, password, address);
    }
    String results =null;
    
        /*Badge*/
    
    public Badge getBadge(String badgeid) {
        String id = null, des = null;
     
        try {
            String query = "Select *FROM Badge WHERE id = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, badgeid);
            
            boolean ptExe = pstmt.execute();
            
            if (ptExe) {
                ResultSet resultset = pstmt.executeQuery();
                
                while(resultset.next()){
                    id = resultset.getString(1);
                    des = resultset.getString(2);
                }
            }
        }
        catch (Exception e) { 
            e.printStackTrace();
        }
        
        Badge result = new Badge(id, des);
        return result;
    } 
    
        /*Employee*/       
    
    public Employee getEmployee(int id) {       
        HashMap<String, String> params = new HashMap<>();

        try{
         String query= "SELECT * FROM employee WHERE id = ?;";
         PreparedStatement pstmt = connection.prepareStatement(query);
         pstmt.setInt(1,id);
         
         boolean pstmtExe = pstmt.execute();
         

            if (pstmtExe) {
                
                ResultSet resultset = pstmt.getResultSet();

                if(resultset.next()) {
                 params.put("id", String.valueOf(resultset.getInt("id")));
                 params.put("badgeid", resultset.getString("badgeid"));
                 params.put("description", resultset.getString("description"));
                 params.put("firstname",resultset.getString("firstname"));
                 params.put("lastname",resultset.getString("lasttname"));
                 params.put("middlename",resultset.getString("middlename"));
                 params.put("employeetypeid", String.valueOf(resultset.getInt("employeetypeid")));
                 params.put("departmentid", String.valueOf(resultset.getInt("departmentid")));
                 params.put("shiftid", String.valueOf(resultset.getInt("shiftid")));
                 params.put("active",resultset.getTime("active").toString());
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace(); 
        }
        
        Employee Results = new Employee(params);
        return Results;
    }
    
    public Employee getEmployee(Badge id) {       
        String badgeid = id.getId();
        int id_int = 0;

        try {
            String query = "SELECT * FROM employee WHERE badgeid = ?;";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, badgeid);
            
            boolean ptExe = pstmt.execute();
            
            if (ptExe) {
                ResultSet resultset = pstmt.executeQuery();
                
                while(resultset.next()){
                    badgeid = resultset.getString(2);
                    id_int = resultset.getInt(1);
                }
            }
        }
        catch (Exception e) { 
            e.printStackTrace();
        }
        
        return getEmployee(id_int);
    } 
    
            /*Punch*/       
    
    public Punch  getPunch(int id) {
        HashMap<String, String> params = new HashMap<>();
        
        try{
         String query= "SELECT * FROM tas_sp22_v1.event WHERE id =?;";
         PreparedStatement pstmt = connection.prepareStatement(query);
         pstmt.setInt(1,id);
         
         boolean pstmtExe = pstmt.execute();
         
         if (pstmtExe) {
                
                ResultSet resultset = pstmt.getResultSet();

                if(resultset.next()) {
                    
                    params.put("terminalId", resultset.getString("terminalId"));
                    params.put("eventtypeid",String.valueOf(resultset.getInt("eventtypeid")));
                    params.put("timestamp",resultset.getTime("timestamp").toString());
                    params.put("badgeid", resultset.getString("badgeid"));
                 }
            }
        }
        catch(Exception e) {
         e.printStackTrace(); 
        }
        
     //Punch Results = new Punch(params,);// Needs another parma for punch constuctor 
     Punch Results=null;
     return Results;
    } 
    
            /*Shift*/       
   
    public Shift getShift(int id) {       //getShift that takes an int id as a parameter
        HashMap<String, String> params = new HashMap<>();
        
        try {
            
            String query= "Select * FROM shift WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);
           
            boolean pstmtExe = pstmt.execute();

            if (pstmtExe) {
               ResultSet resultset = pstmt.getResultSet();

                if(resultset.next()) {

                    params.put("description", resultset.getString("description"));
                    params.put("id", String.valueOf(resultset.getInt("id")));
                    params.put("shiftstart",resultset.getTime("shiftstart").toLocalTime().toString());
                    params.put("shiftstop",resultset.getTime("shiftstop").toLocalTime().toString());
                    params.put("roundinterval", String.valueOf(resultset.getInt("roundinterval")));
                    params.put("graceperiod", String.valueOf(resultset.getInt("graceperiod")));
                    params.put("dockpenalty", String.valueOf(resultset.getInt("dockpenalty")));
                    params.put("lunchstart",resultset.getTime("lunchstart").toLocalTime().toString());
                    params.put("lunchstop",resultset.getTime("lunchstop").toLocalTime().toString());
                    params.put("lunchthreshold", String.valueOf(resultset.getInt("lunchthreshold")));
                }
            }
        }
       
        catch(Exception e) {
            e.printStackTrace(); 
        }
       Shift Results = new Shift(params);
        return Results;
        }
    
    public Shift getShift(Badge id) {       //getShift that takes a Badge id as a parameter
        String badgeid = id.getId();
        int id_int = 0;

        try {
            String query = "SELECT * FROM employee WHERE badgeid = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, badgeid);
            
            boolean ptExe = pstmt.execute();
            
            if (ptExe) {
                ResultSet resultset = pstmt.getResultSet();
                
                if (resultset.next()){
                    id_int = resultset.getInt("id");
                }
            }
        }
        catch (Exception e) { 
            e.printStackTrace();
        }
        
        return getShift(id_int);
        }
    
        /*Connection*/
    
    public boolean isConnected() {
        boolean result = false;
        
        try {
            
            if ( !(connection == null) )
                result = !(connection.isClosed());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;   
    }
    
    private Connection openConnection(String u, String p, String a) {
        Connection c = null;
        
        if (a.equals("") || u.equals("") || p.equals("")){
            System.err.println("*** ERROR: MUST SPECIFY ADDRESS/USERNAME/PASSWORD BEFORE OPENING DATABASE CONNECTION ***");
        }
        
        else {
        
            try {
                String url = "jdbc:mysql://" + a + "/tas_sp22_v1?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=EXCEPTION&serverTimezone=America/Chicago";
                // System.err.println("Connecting to " + url + " ...");

                c = DriverManager.getConnection(url, u, p);
            }
            catch (Exception e) {
                e.printStackTrace();
            }  
        }
        
        return c;    
    }
}
 