package com.bridgelabz.model;

public class CensusDAO {
    public Double housingDensity;
    public int housingUnits;
    public Double landArea;
    public Double populationDensity;
    public Double totalArea;
    public Double waterArea;
    public String stateId;
    public String srNo;
    public String stateName;
    public int TIN;
    public String stateCode;
    public String state;
    public  int densityPerSqKm;
    public  int population;
    public  int areaInSqKm;
    public CensusDAO(IndianCensusCSV indianCensusCSV){
        state = indianCensusCSV.state;
        population = indianCensusCSV.population;
        densityPerSqKm = indianCensusCSV.densityPerSqKm;
        areaInSqKm = indianCensusCSV.areaInSqKm;

    }
    public CensusDAO(IndianStateCSV indianStateCSV){
        srNo = indianStateCSV.srNo;
        stateName = indianStateCSV.stateName;
        TIN = indianStateCSV.TIN;
        stateCode = indianStateCSV.stateCode;
    }
    public CensusDAO(UsCensusCSV usCensusCSV){
        housingDensity = usCensusCSV.housingDensity;
        housingUnits = usCensusCSV.housingUnits;
        landArea = usCensusCSV.landArea;
        population = usCensusCSV.population;
        populationDensity = usCensusCSV.populationDensity;
        state = usCensusCSV.state;
        stateId = usCensusCSV.stateId;
        waterArea = usCensusCSV.waterArea;
        totalArea = usCensusCSV.totalArea;
    }

}
