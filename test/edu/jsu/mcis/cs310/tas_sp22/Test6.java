package edu.jsu.mcis.cs310.tas_sp22;

import org.junit.*;
import static org.junit.Assert.*;
import java.util.*;
import org.json.simple.*;


public class Test6 {
    
    private TASDatabase db;
    
    @Before
    public void setup() {
        
        db = new TASDatabase("tasuser", "PASSWORD", "localhost");
        
    }
    
    @Test
    public void testJSONShift1Weekday() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"originaltimestamp\":\"WED 08\\/01\\/2018 07:00:35\",\"badgeid\":\"922370AA\",\"adjustedtimestamp\":\"WED 08\\/01\\/2018 07:00:00\",\"adjustmenttype\":\"None\",\"terminalid\":\"104\",\"id\":\"198\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"WED 08\\/01\\/2018 12:02:47\",\"badgeid\":\"922370AA\",\"adjustedtimestamp\":\"WED 08\\/01\\/2018 12:00:00\",\"adjustmenttype\":\"Lunch Start\",\"terminalid\":\"104\",\"id\":\"204\",\"punchtype\":\"CLOCK OUT\"},{\"originaltimestamp\":\"WED 08\\/01\\/2018 12:27:17\",\"badgeid\":\"922370AA\",\"adjustedtimestamp\":\"WED 08\\/01\\/2018 12:30:00\",\"adjustmenttype\":\"Lunch Stop\",\"terminalid\":\"104\",\"id\":\"207\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"WED 08\\/01\\/2018 15:31:46\",\"badgeid\":\"922370AA\",\"adjustedtimestamp\":\"WED 08\\/01\\/2018 15:30:00\",\"adjustmenttype\":\"Shift Stop\",\"terminalid\":\"104\",\"id\":\"211\",\"punchtype\":\"CLOCK OUT\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(198);
        Badge b = db.getBadge(p.getBadge().getId());
        Shift s = db.getShift(b);
		
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TAS.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
    @Test
    public void testJSONShift1Weekend() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"originaltimestamp\":\"SAT 08\\/11\\/2018 05:51:44\",\"badgeid\":\"1B2052DE\",\"adjustedtimestamp\":\"SAT 08\\/11\\/2018 05:45:00\",\"adjustmenttype\":\"Interval Round\",\"terminalid\":\"101\",\"id\":\"1084\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"SAT 08\\/11\\/2018 11:01:16\",\"badgeid\":\"1B2052DE\",\"adjustedtimestamp\":\"SAT 08\\/11\\/2018 11:00:00\",\"adjustmenttype\":\"Interval Round\",\"terminalid\":\"101\",\"id\":\"1129\",\"punchtype\":\"CLOCK OUT\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(1084);
        Badge b = db.getBadge(p.getBadge().getId());
        Shift s = db.getShift(b);
        
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TAS.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
    @Test
    public void testJSONShift2Weekday() {
        
        /* Expected JSON Data */
        
        String expectedJSON = "[{\"originaltimestamp\":\"TUE 09\\/04\\/2018 06:48:31\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"TUE 09\\/04\\/2018 07:00:00\",\"adjustmenttype\":\"Shift Start\",\"terminalid\":\"104\",\"id\":\"3279\",\"punchtype\":\"CLOCK IN\"},{\"originaltimestamp\":\"TUE 09\\/04\\/2018 12:02:42\",\"badgeid\":\"28DC3FB8\",\"adjustedtimestamp\":\"TUE 09\\/04\\/2018 12:00:00\",\"adjustmenttype\":\"Lunch Start\",\"terminalid\":\"104\",\"id\":\"3333\",\"punchtype\":\"CLOCK OUT\"}]";
        
        ArrayList<HashMap<String, String>> expected = (ArrayList)JSONValue.parse(expectedJSON);
		
        /* Get Punch */
        
        Punch p = db.getPunch(3279);
        Badge b = db.getBadge(p.getBadge().getId());
        Shift s = db.getShift(b);
        
        /* Get Daily Punch List */
        
        ArrayList<Punch> dailypunchlist = db.getDailyPunchList(b, p.getOriginalTimestamp().toLocalDate());
        
        /* Adjust Punches */
        
        for (Punch punch : dailypunchlist) {
            punch.adjust(s);
        }
        
        /* JSON Conversion */
        
        String actualJSON = TAS.getPunchListAsJSON(dailypunchlist);
        
        ArrayList<HashMap<String, String>> actual = (ArrayList)JSONValue.parse(actualJSON);
		
        /* Compare to Expected JSON */
        
        assertEquals(expected, actual);
        
    }
    
}
