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
            censusCSVList = csvBuilder.getCSVFileList(reader, IndianCensusCSV.class);
            return censusCSVList.size();
        } catch (IOException e) {
            throw new CensusAnalyserException("Wrong File Path or Wrong Extension",
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Incorrect Header and Delimeter",
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }

    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            stateCSVList = csvBuilder.getCSVFileList(reader, IndianStateCSV.class);
            return stateCSVList.size();
        } catch (RuntimeException e) {
            throw new CensusAnalyserException("Incorrect Header and Delimeter",
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE);
        } catch (IOException e) {
            throw new CensusAnalyserException("Wrong File Path or Wrong Extension",
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (CSVBuilderException e) {
            throw new CensusAnalyserException(e.getMessage(), e.type.name());
        }
    }
    private <E> int getCount(Iterator<E> iterator) {
        Iterable<E> csvIterable = () -> iterator;
        int namOfEntries = (int) StreamSupport.stream(csvIterable.spliterator(), false)
                .count();
        return namOfEntries;
    }
    public <T> void throwDataException(List<T> list) throws CensusAnalyserException {
        if (list == null || list.size() == 0){
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
    }
    public String getStateWiseSortedStateCodeData() throws CensusAnalyserException {
        throwDataException(stateCSVList);
        stateCSVList.sort(((Comparator<IndianStateCSV>)(stateData1, stateData2)
                -> stateData2.stateCode.compareTo(stateData1.stateCode)).reversed());
        String sortedStateCodeCensusJson = new Gson().toJson(stateCSVList);
        return sortedStateCodeCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerState() throws CensusAnalyserException {
        throwDataException(censusCSVList);
        censusCSVList.sort(((Comparator<IndianCensusCSV>)(stateData1, stateData2)
                -> stateData2.state.compareTo(stateData1.state)).reversed());
        String sortedCensusJson = new Gson().toJson(censusCSVList);
        return sortedCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerPopulation() throws CensusAnalyserException {
        throwDataException(censusCSVList);
        censusCSVList.sort(((stateData1, stateData2)
                -> stateData2.population-stateData1.population));
        String sortedCensusJson = new Gson().toJson(censusCSVList);
        return sortedCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerDensity() throws CensusAnalyserException {
        throwDataException(censusCSVList);
        censusCSVList.sort(((stateData1, stateData2)
                -> stateData2.densityPerSqKm-stateData1.densityPerSqKm));
        String sortedCensusJson = new Gson().toJson(censusCSVList);
        return sortedCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerArea() throws CensusAnalyserException {
        throwDataException(censusCSVList);
        censusCSVList.sort(((stateData1, stateData2)
                -> stateData2.areaInSqKm-stateData1.areaInSqKm));
        String sortedCensusJson = new Gson().toJson(censusCSVList);
        return sortedCensusJson;
    }
}

