package com.bridgelabz.model;
import com.opencsv.bean.CsvBindByName;

public class IndianCensusCSV {

    @CsvBindByName(column = "State", required = true)
    public String state;

    @CsvBindByName(column = "Population", required = true)
    public int population;

    @CsvBindByName(column = "AreaInSqKm", required = true)
    public int areaInSqKm;

    @CsvBindByName(column = "DensityPerSqKm", required = true)
    public int densityPerSqKm;

    public IndianCensusCSV(IndianCensusDAO indianCensusDAO){
        state = indianCensusDAO.state;
        population = indianCensusDAO.population;
        areaInSqKm = indianCensusDAO.areaInSqKm;
        densityPerSqKm = indianCensusDAO.densityPerSqKm;

    }

    @Override
    public String toString() {
        return "IndianCensusCSV{" +
                "State='" + state + '\'' +
                ", Population='" + population + '\'' +
                ", AreaInSqKm='" + areaInSqKm + '\'' +
                ", DensityPerSqKm='" + densityPerSqKm + '\'' +
                '}';
    }
}

