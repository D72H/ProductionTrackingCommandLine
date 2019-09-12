package com.company;

import java.time.LocalDate;

public class ShippingData {
    LocalDate date;
    int unitsShipped;

    public ShippingData(LocalDate date, int unitsShipped) {
        this.date = date;
        this.unitsShipped = unitsShipped;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getUnitsShipped() {
        return unitsShipped;
    }

    public void setUnitsShipped(int unitsShipped) {
        this.unitsShipped = unitsShipped;
    }
    public String toString(){
        return date + "\t" + unitsShipped;
    }
}
