package com.bridgelabz.model;
import com.opencsv.bean.CsvBindByName;

public class UsCensusCSV {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "State Id", required = true)
    public String stateId;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "Population Density", required = true)
    public Double populationDensity;

    @CsvBindByName(column = "Total area", required = true)
    public Double totalArea;

    @CsvBindByName(column = "Housing units", required = true)
    public int housingUnits;

    @CsvBindByName(column = "Water area", required = true)
    public Double waterArea;

    @CsvBindByName(column = "Land area", required = true)
    public Double landArea;

    @CsvBindByName(column = "Housing Density", required = true)
    public Double housingDensity;

//    public UsCensusCSV(String state,String statecode, int population, double populationDensity, double totalArea) {
//    }
    public UsCensusCSV(){

    }
    public UsCensusCSV(String state,String stateCode, int population, double populationDensity, double totalArea) {
        this.state = state;
        this.totalArea = totalArea;
        this.populationDensity = populationDensity;
        this.population = population;
        this.stateId = stateCode;
    }

    @Override
    public String toString() {
        return "usCensusCSV{" +
                "State Id='" + stateId + '\'' +
                ", State ='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", Housing units='" + housingUnits + '\'' +
                ", Total area='" + totalArea + '\'' +
                ", Water area='" + waterArea + '\'' +
                ", Land Area='" + landArea + '\'' +
                ", Population Density='" + populationDensity + '\'' +
                ", Housing Density='" + housingDensity + '\'' +
                '}';
    }
}

