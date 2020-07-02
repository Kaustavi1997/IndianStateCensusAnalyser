package com.bridgelabz.model;
import com.opencsv.bean.CsvBindByName;

public class IndianStateCSV {
    @CsvBindByName(column = "SrNo", required = true)
    public String srNo;

    @CsvBindByName(column = "State Name", required = true)
    public String stateName;

    @CsvBindByName(column = "TIN", required = true)
    public int TIN;

    @CsvBindByName(column = "StateCode", required = true)
    public String stateCode;

    public IndianStateCSV(IndianCensusDAO indianCensusDAO){
        srNo = indianCensusDAO.srNo;
        stateName = indianCensusDAO.stateName;
        TIN = indianCensusDAO.TIN;
        stateCode = indianCensusDAO.stateCode;

    }

    @Override
    public String toString() {
        return "IndianStateCSV{" +
                "SrNo='" + srNo + '\'' +
                ", State Name='" + stateName + '\'' +
                ", TIN='" + TIN + '\'' +
                ", StateCode='" + stateCode + '\'' +
                '}';
    }
}
