package edu.jsu.mcis.cs310.tas_sp22;
import java.sql.*;
import java.sql.Connection;




public class Badge {
    private String id,description;
    
    public Badge(String id,String description){
        this.id =id;
        this.description= description;
        
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    
    public String ToString(){
        return "0";
    }
}
