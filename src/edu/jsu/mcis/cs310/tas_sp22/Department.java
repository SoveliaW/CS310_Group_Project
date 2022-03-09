package edu.jsu.mcis.cs310.tas_sp22;
import java.util.HashMap;
import java.time.LocalDate;


public class Department {
    private String description;
    private int id, terminalid;
    
    public Department(HashMap <String, String> params) {
        this.description = params.get("description");
        this.id = Integer.parseInt(params.get("id"));
        this.terminalid = Integer.parseInt(params.get("terminalid"));
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getTerminalid() {
        return terminalid;
    }
    public String toString() {
        //("#1 (Assembly): terminalid: 103", d1.toString());
    
        StringBuilder s = new StringBuilder();
        s.append("#").append(id).append(" (").append(description).append("): ").append("terminalid: ");
        s.append(terminalid);
        
        return s.toString();
    }
    
}
