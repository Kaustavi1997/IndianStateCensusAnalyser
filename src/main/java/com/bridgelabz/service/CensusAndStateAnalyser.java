package com.bridgelabz.service;
import com.bridgelabz.model.IndianCensusCSV;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.CensusDAO;
import com.bridgelabz.model.IndianStateCSV;
import com.bridgelabz.model.UsCensusCSV;
import com.google.gson.Gson;
import csvbuilder.ICSVBuilder;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.CSVBuilderException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CensusAndStateAnalyser {
    List<CensusDAO> usCensusList = null;
    Map<String, CensusDAO> censusMap = null;
    Map<String, CensusDAO> stateMap = null;
    public CensusAndStateAnalyser(){
        this.usCensusList = new ArrayList<CensusDAO>();
        this.censusMap = new HashMap<String, CensusDAO> ();
        this.stateMap = new HashMap<String, CensusDAO> ();
    }

    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndianCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndianCensusCSV.class);
            Iterable<IndianCensusCSV> CSVIterable = () -> csvFileIterator;
            StreamSupport.stream(CSVIterable.spliterator(),false)
                    .forEach(censusDAO -> censusMap.put(censusDAO.state, new CensusDAO(censusDAO)));
            return this.censusMap.size();
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
            Iterable<IndianStateCSV> CSVIterable = () -> csvFileIterator;
            StreamSupport.stream(CSVIterable.spliterator(),false)
                    .forEach(censusDAOStateData -> stateMap.put(censusDAOStateData.stateName, new CensusDAO(censusDAOStateData)));
            StreamSupport.stream(CSVIterable.spliterator(),false)
                    .filter(censusDAOStateData -> censusMap.get(censusDAOStateData.stateName) != null)
                    .forEach(censusDAOStateData -> censusMap.get(censusDAOStateData.stateName).stateCode = censusDAOStateData.stateCode);
            return this.stateMap.size();
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
            Iterator<UsCensusCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, UsCensusCSV.class);
            while(csvFileIterator.hasNext()){
                this.usCensusList.add(new CensusDAO(csvFileIterator.next()));
            }
            return this.usCensusList.size();
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
    public void throwDataException() throws CensusAnalyserException {
        if (usCensusList == null || usCensusList.size() == 0){
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
    }
    public String getStateCodeWiseSortedStateCodeData() throws CensusAnalyserException {
        List<Map.Entry<String, CensusDAO>> sorted = stateMap.entrySet()
            .stream()
            .sorted((e1, e2) -> e1.getValue().stateCode.compareTo(e2.getValue().stateCode)).collect(Collectors.toList());
        List<CensusDAO> sortedByStateCodeDao = new ArrayList<>();
        for (Map.Entry<String, CensusDAO> stringIndianCensusDAOEntry : sorted) {
            sortedByStateCodeDao.add(stringIndianCensusDAOEntry.getValue());
        }
        String sortedCensusJson = new Gson().toJson(sortedByStateCodeDao);
        return sortedCensusJson;
    }

    public String getStateWiseSortedCensusDataAsPerState() throws CensusAnalyserException {
        List<Map.Entry<String, CensusDAO>> sorted = censusMap.entrySet()
                .stream()
                .sorted((e1, e2) -> e1.getValue().state.compareTo(e2.getValue().state)).collect(Collectors.toList());
        List<CensusDAO> sortedByStateDao = new ArrayList<>();
        for (Map.Entry<String, CensusDAO> stringIndianCensusDAOEntry : sorted) {
            sortedByStateDao.add(stringIndianCensusDAOEntry.getValue());
        }
        String sortedCensusJson = new Gson().toJson(sortedByStateDao);
        return sortedCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerPopulation() throws CensusAnalyserException {
        List<Map.Entry<String, CensusDAO>> sorted = censusMap.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().population - e1.getValue().population).collect(Collectors.toList());
        List<CensusDAO> sortedByPopulationDao = new ArrayList<>();
        for (Map.Entry<String, CensusDAO> stringIndianCensusDAOEntry : sorted) {
            sortedByPopulationDao.add(stringIndianCensusDAOEntry.getValue());
        }
        String sortedCensusJson = new Gson().toJson(sortedByPopulationDao);
        return sortedCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerDensity() throws CensusAnalyserException {
        List<Map.Entry<String, CensusDAO>> sorted = censusMap.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().densityPerSqKm - e1.getValue().densityPerSqKm).collect(Collectors.toList());
        List<CensusDAO> sortedByDensityDao = new ArrayList<>();
        for (Map.Entry<String, CensusDAO> stringIndianCensusDAOEntry : sorted) {
            sortedByDensityDao.add(stringIndianCensusDAOEntry.getValue());
        }
        String sortedCensusJson = new Gson().toJson(sortedByDensityDao);
        return sortedCensusJson;
    }
    public String getStateWiseSortedCensusDataAsPerArea() throws CensusAnalyserException {
        List<Map.Entry<String, CensusDAO>> sorted = censusMap.entrySet()
                .stream()
                .sorted((e1, e2) -> e2.getValue().areaInSqKm - e1.getValue().areaInSqKm).collect(Collectors.toList());
        List<CensusDAO> sortedByAreaDao = new ArrayList<>();
        for (Map.Entry<String, CensusDAO> stringIndianCensusDAOEntry : sorted) {
            sortedByAreaDao.add(stringIndianCensusDAOEntry.getValue());
        }
        String sortedCensusJson = new Gson().toJson(sortedByAreaDao);
        return sortedCensusJson;
    }
    public String getPopulationWiseSortedUsCensusData() throws CensusAnalyserException {
        throwDataException();
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.population - usCensusData1.population));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getStateWiseSortedUsCensusData() throws CensusAnalyserException {
        throwDataException();
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.state.compareTo(usCensusData1.state)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getPopulationDensityWiseSortedUsCensusData() throws CensusAnalyserException {
        throwDataException();
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.populationDensity.compareTo(usCensusData1.populationDensity)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getHousingDensityWiseSortedUsCensusData() throws CensusAnalyserException {
        throwDataException();
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.housingDensity.compareTo(usCensusData1.housingDensity)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getHousingTotalAreaSortedUsCensusData() throws CensusAnalyserException {
        throwDataException();
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.totalArea.compareTo(usCensusData1.totalArea)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getHousingWaterAreaSortedUsCensusData() throws CensusAnalyserException {
        throwDataException();
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.waterArea.compareTo(usCensusData1.waterArea)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String getHousingLandAreaSortedUsCensusData() throws CensusAnalyserException {
        throwDataException();
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.landArea.compareTo(usCensusData1.landArea)));
        String sortedUsCensusJson = new Gson().toJson(usCensusList);
        return sortedUsCensusJson;
    }
    public String mostPopularStateInIndiaAndUs() throws CensusAnalyserException {
        throwDataException();
        usCensusList.sort(((usCensusData1, usCensusData2)
                -> usCensusData2.populationDensity.compareTo(usCensusData1.populationDensity)));
        String usMostDensityState = usCensusList.get(0).state;
        Double usMostDensity = usCensusList.get(0).populationDensity;

        String  censusDAOString = getStateWiseSortedCensusDataAsPerDensity();
        CensusDAO[] censusDAOSortedBYDensity = new Gson().fromJson(censusDAOString, CensusDAO[].class);

        String indiaMostDensityState = censusDAOSortedBYDensity[0].state;
        Double indiaMostDensity = (double) censusDAOSortedBYDensity[0].densityPerSqKm;

        if(usMostDensity > indiaMostDensity)
            return usMostDensityState;
        else
            return indiaMostDensityState;
    }
}