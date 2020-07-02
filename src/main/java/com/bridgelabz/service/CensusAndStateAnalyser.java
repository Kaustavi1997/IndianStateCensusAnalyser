package com.bridgelabz.service;
import com.bridgelabz.model.IndianCensusCSV;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.IndianCensusDAO;
import com.bridgelabz.model.IndianStateCSV;
import com.bridgelabz.model.usCensusCSV;
import com.google.gson.Gson;
import csvbuilder.ICSVBuilder;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.CSVBuilderException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.StreamSupport;

public class CensusAndStateAnalyser {
    List<IndianCensusDAO> censusList = null;
    List<IndianCensusDAO> stateList = null;
    List<IndianCensusCSV> censusCSVList = null;
    List<IndianStateCSV> stateCSVList = null;
    List<usCensusCSV> usCensusList = null;
    public CensusAndStateAnalyser(){
        this.censusList = new ArrayList<IndianCensusDAO>();
        this.stateList = new ArrayList<IndianCensusDAO>();
    }
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndianCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndianCensusCSV.class);
            while(csvFileIterator.hasNext()){
                this.censusList.add(new IndianCensusDAO(csvFileIterator.next()));
            }
            return this.censusList.size();
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
            Iterator<IndianStateCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndianStateCSV.class);
            while(csvFileIterator.hasNext()){
                this.stateList.add(new IndianCensusDAO(csvFileIterator.next()));
            }
            return this.stateList.size();
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
    public int loadUSCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            usCensusList = csvBuilder.getCSVFileList(reader, usCensusCSV.class);
            return usCensusList.size();
        }catch (IOException e) {
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
        throwDataException(stateList);
        stateList.sort(((Comparator<IndianCensusDAO>)(stateData1, stateData2)
                -> stateData2.stateCode.compareTo(stateData1.stateCode)).reversed());
        String sortedStateCodeCensusJson = new Gson().toJson(stateList);
        return sortedStateCodeCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerState() throws CensusAnalyserException {
        throwDataException(censusList);
        censusList.sort(((Comparator<IndianCensusDAO>)(stateData1, stateData2)
                -> stateData2.state.compareTo(stateData1.state)).reversed());
        String sortedCensusJson = new Gson().toJson(censusList);
        return sortedCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerPopulation() throws CensusAnalyserException {
        throwDataException(censusList);
        censusList.sort(((stateData1, stateData2)
                -> stateData2.population-stateData1.population));
        String sortedCensusJson = new Gson().toJson(censusList);
        return sortedCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerDensity() throws CensusAnalyserException {
        throwDataException(censusList);
        censusList.sort(((stateData1, stateData2)
                -> stateData2.densityPerSqKm-stateData1.densityPerSqKm));
        String sortedCensusJson = new Gson().toJson(censusList);
        return sortedCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerArea() throws CensusAnalyserException {
        throwDataException(censusList);
        censusList.sort(((stateData1, stateData2)
                -> stateData2.areaInSqKm - stateData1.areaInSqKm));
        String sortedCensusJson = new Gson().toJson(censusList);
        return sortedCensusJson;
    }
    public String getPopulationWiseSortedUsCensusData() throws CensusAnalyserException {
        throwDataException(usCensusList);
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.population - usCensusData1.population));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getStateWiseSortedUsCensusData() throws CensusAnalyserException {
        throwDataException(usCensusList);
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.state.compareTo(usCensusData1.state)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getPopulationDensityWiseSortedUsCensusData() throws CensusAnalyserException {
        throwDataException(usCensusList);
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.populationDensity.compareTo(usCensusData1.populationDensity)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getHousingDensityWiseSortedUsCensusData() throws CensusAnalyserException {
        throwDataException(usCensusList);
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.housingDensity.compareTo(usCensusData1.housingDensity)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getHousingTotalAreaSortedUsCensusData() throws CensusAnalyserException {
        throwDataException(usCensusList);
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.totalArea.compareTo(usCensusData1.totalArea)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getHousingWaterAreaSortedUsCensusData() throws CensusAnalyserException {
        throwDataException(usCensusList);
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.waterArea.compareTo(usCensusData1.waterArea)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getHousingLandAreaSortedUsCensusData() throws CensusAnalyserException {
        throwDataException(usCensusList);
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.landArea.compareTo(usCensusData1.landArea)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String mostPopularStateInIndiaAndUs() throws CensusAnalyserException {
        throwDataException(censusList);
        throwDataException(usCensusList);
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.populationDensity.compareTo(usCensusData1.populationDensity)));
        String usMostDensityState = usCensusList.get(0).state;
        Double usMostDensity = usCensusList.get(0).populationDensity;
        censusList.sort(((stateData1, stateData2)
                -> stateData2.densityPerSqKm-stateData1.densityPerSqKm));
        String indiaMostDensityState = censusList.get(0).state;
        Double indiaMostDensity = (double) censusList.get(0).densityPerSqKm;
        if(usMostDensity > indiaMostDensity)
            return usMostDensityState;
        else
            return indiaMostDensityState;
    }
}

