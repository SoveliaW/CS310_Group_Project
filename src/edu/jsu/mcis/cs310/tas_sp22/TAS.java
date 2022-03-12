package edu.jsu.mcis.cs310.tas_sp22;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.Assert.assertEquals;


public class TAS {

    public static void main (String[] args) throws SQLException {
        
        TASDatabase db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
        if (db.isConnected()){
            System.err.println("Connected Successfully!");
             DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
        /* Create New Punch Object */

        Punch p1 = new Punch(0, db.getBadge("0D87987C"), 1);
        
        /* Create Timestamp Objects */
        
        LocalDateTime ots, rts;
		
        /* Get Punch Properties */
        
        String badgeid = p1.getBadge().getId();
        
        ots = p1.getOriginalTimestamp();
        System.err.println(ots+ " :this is the time for test 3");
        int terminalid = p1.getTerminalid();
        System.err.println(terminalid+ " :this is the terminalid for test 3");
        PunchType punchtype = p1.getPunchtype();
		
        /* Insert Punch Into Database */
        
        int punchid = db.insertPunch(p1);
		
        /* Retrieve New Punch */
        System.err.println(punchid+ " :this is the punch id for test 3");
        Punch p2 = db.getPunch(punchid);
		
        /* Compare Punches */

        assertEquals(badgeid, p2.getBadge().getId());
        
        rts = p2.getOriginalTimestamp();
        
        assertEquals(terminalid, p2.getTerminalid());
        assertEquals(punchtype, p2.getPunchtype());
        System.err.println(rts.format(dtf)+" test 2 time");
        assertEquals(ots.format(dtf), rts.format(dtf));
        
    }
        }
}
}