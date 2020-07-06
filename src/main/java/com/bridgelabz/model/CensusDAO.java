package com.bridgelabz.model;

public class CensusDAO {
    public double populationDensity;
    public double totalArea;
    public String stateCode;
    public String state;
    public  int population;
    public double housingDensity;
    public double landArea;
    public double waterArea;
    public CensusDAO(IndianCensusCSV indianCensusCSV){
        state = indianCensusCSV.state;
        population = indianCensusCSV.population;
        populationDensity = indianCensusCSV.densityPerSqKm;
        totalArea = indianCensusCSV.areaInSqKm;
    }
    public CensusDAO(UsCensusCSV usCensusCSV){
        state = usCensusCSV.state;
        stateCode = usCensusCSV.stateId;
        population = usCensusCSV.population;
        populationDensity = usCensusCSV.populationDensity;
        totalArea = usCensusCSV.totalArea;
        housingDensity = usCensusCSV.housingDensity;
        landArea = usCensusCSV.landArea;
        waterArea = usCensusCSV.waterArea;
    }
}
