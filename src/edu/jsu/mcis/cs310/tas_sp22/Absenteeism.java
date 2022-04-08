package edu.jsu.mcis.cs310.tas_sp22;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Absenteeism {
    private Badge badge;
    private LocalDate payperiod;
    private Double percentage;
    
    public Absenteeism (Badge badge, LocalDate payperiod, Double percentage){
        this.badge = this.badge;
        this.payperiod = payperiod;
        this.percentage = percentage;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badgeid) {
        this.badge = badgeid;
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
        
        s.append("#").append(badge).append("(Pay Period Starting ").append(payperiod.format(dtf)).append("): percentage"); 
        
        return s.toString();
    }
}
