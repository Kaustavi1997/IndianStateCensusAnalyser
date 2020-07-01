package com.bridgelabz.service;

import com.bridgelabz.model.IndianCensusCSV;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.IndianStateCSV;
import com.google.gson.Gson;
import csvbuilder.ICSVBuilder;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.CSVBuilderException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAndStateAnalyser {
    List<IndianCensusCSV> censusCSVList = null;
    List<IndianStateCSV> stateCSVList = null;
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            censusCSVList = csvBuilder.getCSVFileList(reader,IndianCensusCSV.class);
            return censusCSVList.size();
        } catch (FileNotFoundException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCSVList = csvBuilder.getCSVFileList(reader,IndianStateCSV.class);
            return stateCSVList.size();
        } catch (FileNotFoundException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE);
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int namOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false)
                                .count();
        return namOfEntries;
    }
    public String getStateWiseSortedStateCodeData() throws CensusAnalyserException {
        if (stateCSVList == null || stateCSVList.size() == 0){
            throw new CensusAnalyserException("No State Code Data",CensusAnalyserException.ExceptionType.NO_STATE_CODE_DATA);
        }
        Comparator<IndianStateCSV> stateCodeComparator = Comparator.comparing(census -> census.stateCode);
        this.sortGeneric(stateCodeComparator,stateCSVList,"asc");
        String sortedStateCodeCensusJson = new Gson().toJson(stateCSVList);
        return sortedStateCodeCensusJson;
    }
    public String getSortedCensusData(String stateOrPopulation) throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        if (stateOrPopulation == "state"){
            Comparator<IndianCensusCSV> censusComparatorForstate = Comparator.comparing(census -> census.state);
            this.sortGeneric(censusComparatorForstate,censusCSVList,"asc");
        }
        else if (stateOrPopulation == "population"){
            Comparator<IndianCensusCSV> censusComparatorForPopulation = Comparator.comparing(census -> census.population);
            this.sortGeneric(censusComparatorForPopulation,censusCSVList,"dsc");
        }
        else{
            Comparator<IndianCensusCSV> censusComparatorForPopulation = Comparator.comparing(census -> census.densityPerSqKm);
            this.sortGeneric(censusComparatorForPopulation,censusCSVList,"dsc");
        }
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    private <T> void sortGeneric(Comparator<T> censusComparator, List<T> list, String order) {
        for(int i=0; i<list.size(); i++){
            for(int j=0; j<list.size()-1; j++){
                T census1 = (T) list.get(j);
                T census2 = (T) list.get(j+1);
                if (order.compareTo("asc") == 0) {
                    if (censusComparator.compare(census1, census2) > 0) {
                        list.set(j, census2);
                        list.set(j + 1, census1);
                    }
                }
                else{
                    if (censusComparator.compare(census1, census2) < 0) {
                        list.set(j, census2);
                        list.set(j + 1, census1);
                    }
                }
            }
        }
    }
}
