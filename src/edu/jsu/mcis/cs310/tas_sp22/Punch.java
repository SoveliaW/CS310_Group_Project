package edu.jsu.mcis.cs310.tas_sp22;

import java.time.LocalDateTime;
import java.sql.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import static java.time.temporal.ChronoUnit.MINUTES;

public class Punch {

    private int terminalid, id, eventtypeid;
    private PunchType punchtypeid;
    private String badgeid, adjustmenttype;
    private LocalDateTime timestamp, adjustedtimestamp;
    private Badge badge;
    private ArrayList DailyPunchList;
    private LocalTime adjustedtime;


    public Punch(HashMap<String, String> params, Badge badge) {
        this.id = Integer.parseInt(params.get("id"));
        this.terminalid = Integer.parseInt(params.get("terminalid"));
        this.badgeid = params.get("badgeid");
        this.timestamp = LocalDateTime.parse(params.get("timestamp"));
        this.eventtypeid = Integer.parseInt(params.get("eventtypeid"));
        this.punchtypeid = PunchType.values()[Integer.parseInt(params.get("eventtypeid"))];
        this.badge = badge;

        String dayofweek = timestamp.getDayOfWeek().toString();

    }

    public Punch(int terminalid, Badge badge, int eventtypeid) {
        this.terminalid = terminalid;
        this.eventtypeid = eventtypeid;
        this.badge = badge;
        java.sql.Timestamp timestamp1 = new Timestamp(new java.util.Date().getTime());
        LocalDateTime local = timestamp1.toLocalDateTime();
        local = local.withSecond(0).withNano(0);
        this.timestamp = local;

    }

    public int getId() {
        return id;
    }

    public Badge getBadge() {
        return badge;
    }

    public String getTimestamp() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");
        return timestamp.format(dtf).toUpperCase();
    }

    public int getEventtypeid() {
        return eventtypeid;
    }

    public LocalDateTime getOriginalTimestamp() {
        return timestamp;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public PunchType getPunchtype() {
        punchtypeid = null;
        return punchtypeid;
    }

    public PunchType getPunchtypeid() {
        return punchtypeid;
    }
    
    public LocalTime roundInterval(int roundinterval, LocalTime time) {
        int minutes = time.getMinute();
        int remainder = minutes % roundinterval;

        if (remainder < (roundinterval / 2)) {
            return adjustedtime = time.minusMinutes(remainder).withSecond(0).withNano(0);
        } 
        else {
            return adjustedtime = time.plusMinutes(roundinterval - remainder).withSecond(0).withNano(0);
        }
    }

    public void adjust (Shift s) {
        LocalTime shiftstart = s.getShiftstart();
        LocalTime shiftstop = s.getShiftstop();
        LocalTime lunchstart = s.getLunchstart();
        LocalTime lunchstop = s.getLunchstop();
        int roundinterval = s.getRoundinterval();
        int graceperiod = s.getGraceperiod();
        int dockpenalty = s.getDockpenalty();
        
        LocalTime time = getOriginalTimestamp().toLocalTime();
        adjustmenttype = "None";
        int timediff = 0;
        
        DayOfWeek day = timestamp.getDayOfWeek();
        
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            adjustedtime = roundInterval(roundinterval,time);
            adjustmenttype = "Interval Round";
        }
        else {
            
            if (punchtypeid == PunchType.CLOCK_IN) { //Clock in

                timediff = Math.abs((int)MINUTES.between(time, shiftstart)); //Mintues between clock in and start of shift
                

                if (time.isBefore(shiftstart)) { //Before shift start
                    if (timediff <= graceperiod || timediff <= roundinterval) { //Grace Period
                        adjustedtime = shiftstart;
                        adjustmenttype = "Shift Start";
                    }
                    else { //Round interval
                        adjustedtime = roundInterval(roundinterval,time);
                        adjustmenttype = "Interval Round";
                    }
                }

                else if (time.isAfter(shiftstart) && time.isBefore(lunchstart)) { //After shift start, but before lunch start
                    if (timediff < 1 || timediff == 60) {
                        adjustedtime = time.withSecond(0);
                    }
                    else if (timediff <= graceperiod) { //Grace Period
                        adjustedtime = shiftstart;
                        adjustmenttype = "Shift Start";
                    }
                    else if (timediff >= graceperiod && timediff <= dockpenalty) { //Dock Penalty
                        adjustedtime = shiftstart.plusMinutes(dockpenalty);
                        adjustmenttype = "Shift Dock";
                    }
                    else if (timediff >= dockpenalty) {
                        adjustedtime = roundInterval(roundinterval,time);
                        adjustmenttype = "Interval Round";
                    }
                }

                if (time.isAfter(lunchstart) && time.isBefore(lunchstop)) {
                    adjustedtime = lunchstop;
                    adjustmenttype = "Lunch Stop";
                }
            }

            if (punchtypeid == PunchType.CLOCK_OUT) { //Clock out

                timediff = Math.abs((int)MINUTES.between(time, shiftstop)); //Mintues between clock out and end of shift
                

                if (time.isAfter(shiftstop)) { //After shift stop
                    if (timediff < 1 || timediff == 60) { //Are the seconds between 0:00 and 0:59
                        adjustedtime = time.withSecond(0);
                    }
                    else if (timediff <= graceperiod || timediff <= roundinterval) { //Grace Period
                        adjustedtime = shiftstop;
                        adjustmenttype = "Shift Stop";
                    }
                    else /*if (timediff > roundinterval)*/ { //Round interval
                        adjustedtime = roundInterval(roundinterval,time);
                        adjustmenttype = "Interval Round";
                    }
                }

                else if (time.isAfter(lunchstop) && time.isBefore(shiftstop)) { //After lunch start, but before shift stop
                    if (timediff <= graceperiod) { //Grace Period
                        adjustedtime = shiftstop;
                        adjustmenttype = "Shift Stop";
                    }
                    if (timediff >= graceperiod && timediff <= dockpenalty) { //Dock Penalty
                        adjustedtime = shiftstop.minusMinutes(dockpenalty);
                        adjustmenttype = "Shift Dock";
                    }
                    else if (timediff >= dockpenalty) {
                        adjustedtime = roundInterval(roundinterval,time);
                        adjustmenttype = "Interval Round";
                    }
                }

                else if (time.isAfter(lunchstart) && time.isBefore(lunchstop)) {
                    adjustedtime = lunchstart;
                    adjustmenttype = "Lunch Start";
                }
            }
        }
        
        adjustedtimestamp = getAdjustedLocalDateTime();

    }
    
    public LocalTime getAdjustedTimestamp(){
        LocalTime adjusted = adjustedtime;
        return adjusted;
    }
    
    public LocalDateTime getAdjustedLocalDateTime(){
        adjustedtimestamp = LocalDateTime.of(timestamp.toLocalDate(), adjustedtime); 
        return adjustedtimestamp;
    }
    
    public String getAdjustmenttype(){
        return adjustmenttype;
    }

    public String printOriginal() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

        StringBuilder s = new StringBuilder();
        s.append("#").append(badgeid).append(' ').append(punchtypeid);
        s.append(": ").append(timestamp.format(dtf));
        

        return s.toString().toUpperCase();
    }
    
    public String printAdjusted() {

        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEE MM/dd/yyyy HH:mm:ss");

        StringBuilder s = new StringBuilder();
        s.append("#").append(badgeid).append(' ').append(punchtypeid);
        s.append(": ").append(adjustedtimestamp.format(dtf).toUpperCase());
        s.append(' ').append("(").append(adjustmenttype).append(")");     
        
        return s.toString();
    }
    
    @Override
    public String toString(){
        return printAdjusted();
    }
}
