package com.bridgelabz.model;

import com.bridgelabz.service.CensusAndStateAnalyser;

public class CensusDAO {
    public double populationDensity;
    public double totalArea;
    public String stateCode;
    public String state;
    public  int population;
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
    }

    public CensusDAO(String state, int population, int populationDensity, int totalArea,String stateCode) {
        this.state = state;
        this.population = population;
        this.populationDensity = populationDensity;
        this.totalArea = totalArea;
        this.stateCode = stateCode;
    }

    public Object getCensusDTO(CensusAndStateAnalyser.Country country) {
        if(country.equals(CensusAndStateAnalyser.Country.US))
            return new UsCensusCSV(state,stateCode,population,populationDensity,totalArea);
        return new IndianCensusCSV(state,population,(int)populationDensity,(int)totalArea);

    }
}