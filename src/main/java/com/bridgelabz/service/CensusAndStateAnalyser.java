package com.bridgelabz.service;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.CensusDAO;
import com.google.gson.Gson;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import java.lang.reflect.Field;

public class CensusAndStateAnalyser {
    public enum Country {INDIA, US}
    Map<String, CensusDAO> censusMap = null;
    public int loadCensusData(Country country, String... csvFilePath) throws CensusAnalyserException {
        censusMap = new CensusLoader().loadCensusData(country, csvFilePath);
        return censusMap.size();
    }
    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int namOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false)
                .count();
        return namOfEntries;
    }
    class Sort<T extends Comparable<T>>{
        public int generalCompare(CensusDAO a,CensusDAO b,int fieldIndex,String order) throws IllegalAccessException {
            Field[] fields = CensusDAO.class.getFields();
            T first = (T) fields[fieldIndex].get(a);
            T second = (T) fields[fieldIndex].get(b);
            if(order == "asc"){
                return second.compareTo(first);
            }
            else {
                return first.compareTo(second);
            }
        }
    }
    public String sortForAll(int fieldindex,String order) throws CensusAnalyserException {
        Sort sort = new Sort();
        List<Map.Entry<String, CensusDAO>> sorted = censusMap.entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    int result = 0;
                    try {
                        result = sort.generalCompare(e2.getValue(),e1.getValue(),fieldindex,order);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    return result;
                }).collect(Collectors.toList());
        List<CensusDAO> sortedByGivenField = new ArrayList<>();
        for (Map.Entry<String, CensusDAO> indianCensusEntry : sorted) {
            sortedByGivenField.add(indianCensusEntry.getValue());
        }
        String sortedCensusJson = new Gson().toJson(sortedByGivenField);
        return sortedCensusJson;
    }
    public String mostPopularStateInIndiaAndUs() throws CensusAnalyserException {
        loadCensusData(Country.US,"./src/test/resources/USCensusData.csv");
        String populationOfUsSorted = sortForAll(0,"dsc");
        CensusDAO[] SortedBYDensityUs = new Gson().fromJson(populationOfUsSorted, CensusDAO[].class);
        String usMostDensityState = SortedBYDensityUs[0].state;
        Double usMostDensity = (double) SortedBYDensityUs[0].populationDensity;
        loadCensusData(Country.INDIA,"./src/test/resources/IndiaStateCensusData.csv","./src/test/resources/IndiaStateCode.csv");
        String populationOfIndiaSorted = sortForAll(0,"dsc");
        CensusDAO[] SortedBYDensityIndia = new Gson().fromJson(populationOfUsSorted, CensusDAO[].class);
        String indiaMostDensityState = SortedBYDensityUs[0].state;
        Double indiaMostDensity = (double) SortedBYDensityUs[0].populationDensity;
        if(usMostDensity > indiaMostDensity)
            return usMostDensityState;
        else
            return indiaMostDensityState;
    }

}