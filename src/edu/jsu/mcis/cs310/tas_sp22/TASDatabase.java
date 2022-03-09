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
                ResultSet resultset = pstmt.getResultSet();
                
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
            String query= "SELECT * FROM employee WHERE id = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1, id);

            boolean pstmtExe = pstmt.execute();

            if (pstmtExe) {
                
                ResultSet resultset = pstmt.getResultSet();

                if(resultset.next()) {
                    params.put("id", String.valueOf(id));
                    params.put("badgeid", resultset.getString("badgeid"));
                    params.put("firstname",resultset.getString("firstname"));
                    params.put("lastname",resultset.getString("lastname"));
                    params.put("middlename",resultset.getString("middlename"));
                    params.put("employeetypeid", String.valueOf(resultset.getInt("employeetypeid")));
                    params.put("departmentid", String.valueOf(resultset.getInt("departmentid")));
                    params.put("shiftid", String.valueOf(resultset.getInt("shiftid")));
                    params.put("active",resultset.getDate("active").toString());
                    params.put("inactive", resultset.getString("inactive"));
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace(); 
        }
        
        Employee employee = new Employee(params);
        return employee;
    }
    
    public Employee getEmployee(Badge badge) {       
        String badgeid = badge.getId();
        int id_int = 0;

        try {
            String query = "SELECT * FROM employee WHERE badgeid = ?";
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
    
    public Punch getPunch(int id) {
        HashMap<String, String> params = new HashMap<>();
        
        try{
            String query= "SELECT * FROM tas_sp22_v1.event WHERE id =?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,id);
         
            boolean pstmtExe = pstmt.execute();

            if (pstmtExe) {

                ResultSet resultset = pstmt.getResultSet();

                while(resultset.next()) {
                    params.put("id", String.valueOf(id));
                    params.put("terminalid", String.valueOf(resultset.getInt("terminalid")));
                    params.put("eventtypeid",String.valueOf(resultset.getInt("eventtypeid")));
                    params.put("timestamp", resultset.getTimestamp("timestamp").toLocalDateTime().toString());
                    params.put("badgeid", resultset.getString("badgeid"));
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace(); 
        }
        
        Punch Results = new Punch(params);
        return Results;
    } 
    
    public int insertPunch(Punch p){
        int result = 0;
        /* It should first attempt to authorize the new punch, checking to make 
        sure that the terminal ID that the punch originated from matches the ID
        of the designated clock terminal of the employee's department. 
        (You will find the newly-added "getDepartment()" method
        described above, and the "getEmployee()" method 
        added in Feature 1, to be helpful for this.) 
        */
        //This is what our method is taking 
        //Punch p1 = new Punch(103, db.getBadge("021890C0"), 1);
        
        
        
       if(getDepartment(id)==getEmployee(id)){
            
            // need to bring in hash map and retrive data ?So we can place inside the query
            try{
            String query = "INSERT INTO registration (studentid, termid, crn) VALUES (?,?,?,?);";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,terminalid);
            pstmt.setInt(2,eventtypeid);
            pstmt.setInt(3,badge);
            pstmt.setInt(3,timestamp);
            result = pstmt.executeUpdate();
            }  
            catch (Exception e){
            e.printStackTrace(); 
            }
       }
       /*
       After the punch has been authorized and inserted into the database 
       successfully, the method should retrieve the numeric ID of the newly-inserted
       punch—which will be automatically assigned by the database—and return 
       this ID as an integer.  (See the "Java Database Programming" lecture notes 
       for an example of how to retrieve auto-generated keys.)  If the punch failed
       the authorization check, or if an error occurred during the insertion 
       process, a default ID of zero should be returned.
       */
     return result;
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
    
    public Shift getShift(Badge badge) {       //getShift that takes a Badge id as a parameter
        String badgeid = badge.getId();
        int id_int = 0;
        HashMap<String, String> params = new HashMap<>();
        try {
            String query = "SELECT * FROM employee WHERE badgeid = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, badgeid);
            
            boolean ptExe = pstmt.execute();
            
            if (ptExe) {
                ResultSet resultset = pstmt.executeQuery();
                
                while(resultset.next()){
                    params.put("shiftid", String.valueOf(resultset.getInt("shiftid")));
                }
            }
        }
        
        catch (Exception e) { 
            e.printStackTrace();
        }
        int shiftid = Integer.parseInt(params.get("shiftid"));
        return getShift(shiftid);
    }
    
        /*Department*/
    
    public Department getDepartment(int id) {
        HashMap<String, String> params = new HashMap<>();
        
        try{
            String query= "SELECT * FROM department WHERE id =?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setInt(1,id);

            boolean pstmtExe = pstmt.execute();

            if (pstmtExe) {

                ResultSet resultset = pstmt.getResultSet();

                while(resultset.next()) {
                    params.put("id", String.valueOf(id));
                    params.put("description", resultset.getString("description"));
                    params.put("terminalid", String.valueOf(resultset.getInt("terminalid")));

                }
            }
        }
        catch(Exception e) {
            e.printStackTrace(); 
        }
        
        Department Results = new Department(params);
        return Results;
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
 