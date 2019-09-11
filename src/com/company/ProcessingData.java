package com.company;

import java.time.LocalDate;
import java.util.Date;

public class ProcessingData {
    private String initials;
    private LocalDate date;
    private int unitsProcessed;

    ProcessingData(String initials, LocalDate date, int unitsProcessed){
        this.initials = initials;
        this.date = date;
        this.unitsProcessed = unitsProcessed;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUnitsProcessed() {
        return unitsProcessed;
    }

    public void setUnitsProcessed(int unitsProcessed) {
        this.unitsProcessed = unitsProcessed;
    }
    public String toString(){
        return initials + " " + date + " " + unitsProcessed;
    }
}
