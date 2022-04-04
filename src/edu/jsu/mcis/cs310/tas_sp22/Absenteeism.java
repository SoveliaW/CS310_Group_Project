package edu.jsu.mcis.cs310.tas_sp22;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Absenteeism {
    private String badgeid;
    private LocalDate payperiod;
    private Double percentage;
    
    public Absenteeism (String badgeid, LocalDate payperiod, Double percentage){
        this.badgeid = badgeid;
        this.payperiod = payperiod;
        this.percentage = percentage;
    }

    public String getBadgeid() {
        return badgeid;
    }

    public void setBadgeid(String badgeid) {
        this.badgeid = badgeid;
    }

    public LocalDate getPayperiod() {
        return payperiod;
    }

    public void setPayperiod(LocalDate payperiod) {
        this.payperiod = payperiod;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    
    @Override
    public String toString(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy");
        // #28DC3FB8 (Pay Period Starting 09-02-2018): 2.50%
        StringBuilder s = new StringBuilder();
        
        s.append("#").append(badgeid).append("(Pay Period Starting ").append(payperiod.format(dtf)).append("): percentage");
        
        return s.toString();
    }
}
