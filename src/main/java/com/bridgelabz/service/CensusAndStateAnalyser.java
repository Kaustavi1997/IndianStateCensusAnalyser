package com.bridgelabz.service;
import com.bridgelabz.adapter.CensusAdapterFactory;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.CensusDAO;
import com.bridgelabz.model.IndianCensusCSV;
import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.lang.reflect.Field;

import static java.util.stream.Collectors.toCollection;

public class CensusAndStateAnalyser {
    /**
     * Used for passing particular country using enum.
     */
    public enum Country { INDIA, US };
    private Country country;
    public CensusAndStateAnalyser(Map<String,CensusDAO> censusMap,Country country){
        this.censusMap = censusMap;
        this.country = country;
    }
    public CensusAndStateAnalyser(Country country){
        this.country = country;
    }
    Map<String, CensusDAO> censusMap = null;
    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap = CensusAdapterFactory.getCensusData(country,csvFilePath);
        return censusMap.size();
    }
    /**
     * Used for getting count of the entries present in csv.
     * @param iterator
     * @param <E>
     * @return
     */
    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int namOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false)
                .count();
        return namOfEntries;
    }
    public void checkNull() throws CensusAnalyserException {
        if (censusMap == null || censusMap.size() == 0)
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
    }
    public enum Fields { STATE, POPULATION,TOTAL_AREA,POPULATION_DENSITY,STATE_CODE };
    public String sortForAll(Fields field) throws CensusAnalyserException {
        checkNull();
        Comparator<CensusDAO> censusComparator = null;
        switch(field){
            case STATE:
                censusComparator = Comparator.comparing(census -> census.state);
                break;
            case POPULATION:
                censusComparator = Comparator.comparing(census -> census.population);
                censusComparator = censusComparator.reversed();
                break;
            case STATE_CODE:
                censusComparator = Comparator.comparing(census -> census.stateCode);
                break;
            case TOTAL_AREA:
                censusComparator = Comparator.comparing(census -> census.totalArea);
                censusComparator = censusComparator.reversed();
                break;
            case POPULATION_DENSITY:
                censusComparator = Comparator.comparing(census -> census.populationDensity);
                censusComparator = censusComparator.reversed();
                break;
        }
        ArrayList censusDTOS = (ArrayList) censusMap.values().stream().sorted(censusComparator).map(censusDAO -> censusDAO.getCensusDTO(country)).collect(Collectors.toList());
        String sortedStateIndiaCensus = new Gson().toJson(censusDTOS);
        return sortedStateIndiaCensus;
    }
    /**
     * This function used for getting most popular state in India and US.
     * @return
     * @throws CensusAnalyserException
     */
    public String mostPopularStateInIndiaAndUs() throws CensusAnalyserException {
        loadCensusData(Country.US,"./src/test/resources/USCensusData.csv");
        String populationOfUsSorted = sortForAll(Fields.POPULATION_DENSITY);
        CensusDAO[] SortedBYDensityUs = new Gson().fromJson(populationOfUsSorted, CensusDAO[].class);
        String usMostDensityState = SortedBYDensityUs[0].state;
        Double usMostDensity = (double) SortedBYDensityUs[0].populationDensity;
        loadCensusData(Country.INDIA,"./src/test/resources/IndiaStateCensusData.csv","./src/test/resources/IndiaStateCode.csv");
        String populationOfIndiaSorted = sortForAll(Fields.POPULATION_DENSITY);
        CensusDAO[] SortedBYDensityIndia = new Gson().fromJson(populationOfUsSorted, CensusDAO[].class);
        String indiaMostDensityState = SortedBYDensityUs[0].state;
        Double indiaMostDensity = (double) SortedBYDensityUs[0].populationDensity;
        if(usMostDensity > indiaMostDensity)
            return usMostDensityState;
        else
            return indiaMostDensityState;
    }
 }