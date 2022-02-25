package edu.jsu.mcis.cs310.tas_sp22;
import java.sql.*;
import java.sql.Connection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class TASDatabase {
    
    private final Connection connection;
    
    public TASDatabase(String username, String password, String address) {
        
        this.connection = openConnection(username, password, address);
        
        
    }
    String results =null;
    public String getEmployee(int id) {
        try{
         String query= "SELECT * FROM tas_sp22_v1.employee WHERE id =?;";
         PreparedStatement pstmt = connection.prepareStatement(query);
         pstmt.setInt(1,id);
         boolean pstmtExe = pstmt.execute();
         if(pstmtExe){
            ResultSet resultset = pstmt.getResultSet(); 
             results = getResultSetAsJSON((ResultSet)resultset);
             
             
         }
        }
        catch(Exception e) {
         e.printStackTrace(); 
        }
        return results.toString();
        }
    public String getShift(int id) {
        try{
         String query= "Select *FROM shift WHERE id = ?;";
         PreparedStatement pstmt = connection.prepareStatement(query);
         pstmt.setInt(1,id);
        boolean pstmtExe = pstmt.execute();
         if(pstmtExe){
             ResultSet resultset = pstmt.getResultSet();
             results = getResultSetAsJSON((ResultSet)resultset);
             
         }
        }
        catch(Exception e) {
         e.printStackTrace(); 
        }
        return results.toString();
        }
    public String getBadge(String id) {
        try{
         String query= "SELECT * FROM tas_sp22_v1.badge WHERE id =?;";
         PreparedStatement pstmt = connection.prepareStatement(query);
         pstmt.setString(1,id);
         boolean pstmtExe = pstmt.execute();
         if(pstmtExe){
             ResultSet resultset = pstmt.getResultSet();
             results = getResultSetAsJSON((ResultSet)resultset);
         }
        }
        catch(Exception e) {
         e.printStackTrace(); 
        }
        return results.toString();
        }
    
    public boolean isConnected() {

        boolean result = false;
        
        try {
            
            if ( !(connection == null) )
                
                result = !(connection.isClosed());
            
        }
        catch (Exception e) { e.printStackTrace(); }
        
        return result;
        
    }
    private Connection openConnection(String u, String p, String a) {
        
        Connection c = null;
        
        if (a.equals("") || u.equals("") || p.equals(""))
            
            System.err.println("*** ERROR: MUST SPECIFY ADDRESS/USERNAME/PASSWORD BEFORE OPENING DATABASE CONNECTION ***");
        
        else {
        
            try {

                String url = "jdbc:mysql://" + a + "/tas_sp22_v1?autoReconnect=true&useSSL=false&zeroDateTimeBehavior=EXCEPTION&serverTimezone=America/Chicago";
                // System.err.println("Connecting to " + url + " ...");

                c = DriverManager.getConnection(url, u, p);

            }
            catch (Exception e) { e.printStackTrace(); }
           
        }
        
        return c;
        
    }
    private String getResultSetAsJSON(ResultSet resultset) {
        
        String result;
        
        /* Create JSON Containers */
        
        JSONArray json = new JSONArray();
        JSONArray keys = new JSONArray();
        
        try {
            
            /* Get Metadata */
        
            ResultSetMetaData metadata = resultset.getMetaData();
            int columnCount = metadata.getColumnCount();
            
            /* Get Keys */
            
            for (int i = 1; i <= columnCount; ++i) {

                keys.add(metadata.getColumnLabel(i));

            }
            
            /* Get ResultSet Data */
            
            while(resultset.next()) {
                
                /* Create JSON Container for New Row */
                
                JSONObject row = new JSONObject();
                
                /* Get Row Data */

                for (int i = 1; i <= columnCount; ++i) {
                    
                    /* Get Value; Pair with Key */

                    Object value = resultset.getObject(i);
                    row.put(keys.get(i - 1), String.valueOf(value));

                }
                
                /* Add Row Data to Collection */
                
                json.add(row);

            }
        
        }
        catch (Exception e) { e.printStackTrace(); }
        
        /* Encode JSON Data and Return */
        
        result = JSONValue.toJSONString(json);
        return result;
        
    }
}
