package edu.jsu.mcis.cs310.tas_sp22;
import java.util.Date.*;
import java.sql.Timestamp.*;
import java.sql.*;
import java.sql.Connection;

public class Punch {
   private String AdjustmentType; 
   private int TerminalId;
   private int PunchTypeId;
   private Badge badge;

  public Punch(int TerminalId, Badge badge, int PunchTypeId){
      this.PunchTypeId = PunchTypeId;
      this.TerminalId = TerminalId;
      this.badge = badge;
    }

    public Badge getBadge() {
        return badge;
    }

    public int getTerminalId() {
        return TerminalId;
    }

    public int getPunchTypeId() {
        return PunchTypeId;
    }
  
}
