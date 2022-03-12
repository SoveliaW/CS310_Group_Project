package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static junit.framework.Assert.assertEquals;


public class TAS {

    public static void main (String[] args) throws SQLException {
        
        TASDatabase db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
        if (db.isConnected()){
            System.err.println("Connected Successfully!");
          DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
        /* Create New Punch Object */

        Punch p1 = new Punch(103, db.getBadge("021890C0"), 1);
        
        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;
		
        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        ots = p1.getOriginalTimestamp();
        int terminalid = p1.getTerminalid();
        PunchType punchtype = p1.getPunchtype();
	//System.err.println("badgeid = "+badgeid+ " p1.getbadge= "+ p1.getBadge().getId());
        /* Insert Punch Into Database */
        
        int punchid = db.insertPunch(p1);
		
        /* Retrieve New Punch */
        System.err.println(punchid);
        Punch p2 = db.getPunch(punchid);
        
        /* Compare Punches */
        System.err.println(" p2.getbadge= "+ p2.printOriginal());
        assertEquals(badgeid, p2.getBadge().getId());
        
        
        rts = p2.getOriginalTimestamp();
        
        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(punchtype, p2.getPunchtype());
        assertEquals(ots.format(dtf), rts.format(dtf));
        
    
    }
}
}