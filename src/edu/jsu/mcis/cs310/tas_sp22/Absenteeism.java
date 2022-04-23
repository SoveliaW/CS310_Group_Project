package edu.jsu.mcis.cs310.tas_sp22;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.Locale;


public class Absenteeism {
    
    private Badge badge;
    private LocalDate payperiod, pay_period;
    private double percentage;
    
    public Absenteeism (Badge badge, LocalDate payperiod, double percentage){
        this.badge = badge;
        TemporalField fieldUS = WeekFields.of(Locale.US).dayOfWeek();
        this.payperiod = payperiod;
        this.percentage = percentage;
        java.sql.Date payweek = java.sql.Date.valueOf(payperiod.with(fieldUS, Calendar.SUNDAY));
        pay_period = payweek.toLocalDate();
    }

    public Badge getBadge() {
        return badge;
    }

    public LocalDate getPayperiod() {
        return payperiod;
    }

    public double getPercentage() {
        return percentage;
    }

    
    @Override
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        String fs = String.format("%.02f", percentage);
        // #28DC3FB8 (Pay Period Starting 09-02-2018): 2.50%
        StringBuilder s = new StringBuilder();
        
        s.append("#").append(badge.getId()).append(" (Pay Period Starting ");
        s.append(pay_period.format(dtf)).append("): ").append(fs).append("%"); 
        
        return s.toString();
    }


}
