package edu.jsu.mcis.cs310.tas_sp22;

public class Badge {
    
    private String id,description;
    
    public Badge(String id,String description){
        this.id = id;
        this.description = description;   
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        
        s.append("#").append(id).append(" ").append("(").append(description).append(")");
        
        return s.toString();
    }
}

