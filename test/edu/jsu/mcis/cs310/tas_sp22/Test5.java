package edu.jsu.mcis.cs310.tas_sp22;

import java.util.ArrayList;
import org.junit.*;
import static org.junit.Assert.*;


public class Test5 {
    
    private TASDatabase db;
    
    @Before
    public void setup() {
        
       db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
    }
    
        @Test
        public void testMinutesAccruedShift1Weekday() {
		
        /* Get Punch */
        
        Punch p = db.getPunch(759);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
		
        /* Compute Pay Period Total */
        
        int m = TAS.calculateTotalMinutes(dailypunchlist, s);
		
        /* Compare to Expected Value */
        
        assertEquals(480, m);
        
    }
    
    
    @Test
    public void testMinutesAccruedShift1WeekdayWithTimeout() {
		
        /* Get Punch */
        
        Punch p = db.getPunch(1012);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
		
        /* Compute Pay Period Total */
        
        int m = TAS.calculateTotalMinutes(dailypunchlist, s);
		
        /* Compare to Expected Value */
        
        assertEquals(0, m);
        
    }
    
     @Test
    public void testMinutesAccruedShift1Weekend() {
		
        /* Get Punch */
        
        Punch p = db.getPunch(5216);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
		
        /* Compute Pay Period Total */
        
        int m = TAS.calculateTotalMinutes(dailypunchlist, s);
		
        /* Compare to Expected Value */
        
        assertEquals(600, m);
        
    }
    
        @Test
    public void testMinutesAccruedShift2Weekday() {
		
        /* Get Punch */
        
        Punch p = db.getPunch(5020);
        Badge b = p.getBadge();
        Shift s = db.getShift(b);
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* Compute Pay Period Total */
        
        int m = TAS.calculateTotalMinutes(dailypunchlist, s);
		
        /* Compare to Expected Value */
        
        assertEquals(600, m);
        
    }

}
