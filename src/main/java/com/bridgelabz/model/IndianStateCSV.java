package com.bridgelabz.model;
import com.opencsv.bean.CsvBindByName;

public class IndianStateCSV {

    @CsvBindByName(column = "State Name", required = true)
    public String state;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    @Override
    public String toString() {
        return "IndianStateCSV{" +
                ", State Name='" + state + '\'' +
                ", StateCode='" + stateCode + '\'' +
                '}';
    }
}
