package com.bridgelabz.model;

public class IndianCensusDAO {
    public String srNo;
    public String stateName;
    public int TIN;
    public String stateCode;
    public String state;
    public  int densityPerSqKm;
    public  int population;
    public  int areaInSqKm;
    public IndianCensusDAO(IndianCensusCSV indianCensusCSV){
        state = indianCensusCSV.state;
        areaInSqKm = indianCensusCSV.areaInSqKm;
        population = indianCensusCSV.population;
        densityPerSqKm = indianCensusCSV.densityPerSqKm;

    }
    public IndianCensusDAO(IndianStateCSV indianStateCSV){
        srNo = indianStateCSV.srNo;
        stateName = indianStateCSV.stateName;
        TIN = indianStateCSV.TIN;
        stateCode = indianStateCSV.stateCode;
    }
}
