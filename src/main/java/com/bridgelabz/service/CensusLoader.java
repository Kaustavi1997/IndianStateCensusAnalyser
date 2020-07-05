package com.bridgelabz.service;

import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.CensusDAO;
import com.bridgelabz.model.IndianCensusCSV;
import com.bridgelabz.model.IndianStateCSV;
import com.bridgelabz.model.UsCensusCSV;
import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class CensusLoader {
    /**
     * Used optional for passing file path and passing country to load particular data
     * @param country
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    public Map<String, CensusDAO> loadCensusData(CensusAndStateAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException {
        if(country.equals(CensusAndStateAnalyser.Country.INDIA))
            return this.loadCensusData(IndianCensusCSV.class,csvFilePath);
        else if(country.equals(CensusAndStateAnalyser.Country.US))
            return this.loadCensusData(UsCensusCSV.class,csvFilePath);
        else throw new CensusAnalyserException("Incorrect Country",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
    /**
     * Used for loading Indian Census data and Us Census data in CensusMap
     * @param cenusCSVClass
     * @param csvFilePath
     * @param <E>
     * @return
     * @throws CensusAnalyserException
     */
    private  <E> Map<String, CensusDAO> loadCensusData(Class<E> cenusCSVClass, String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMap = new HashMap<String, CensusDAO>();
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath[0]));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<E> csvFileIterator = csvBuilder.getCSVFileIterator(reader, cenusCSVClass);
            Iterable<E> CSVIterable = () -> csvFileIterator;
            switch(cenusCSVClass.getName()) {
                case "com.bridgelabz.model.IndianCensusCSV":
                    StreamSupport.stream(CSVIterable.spliterator(), false)
                            .map(IndianCensusCSV.class::cast)
                            .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
                case "com.bridgelabz.model.UsCensusCSV":
                    StreamSupport.stream(CSVIterable.spliterator(), false)
                            .map(UsCensusCSV.class::cast)
                            .forEach(censusCSV -> censusMap.put(censusCSV.state, new CensusDAO(censusCSV)));
                }
            if (csvFilePath.length == 1)
                return censusMap;
            this.loadIndiaStateData(censusMap, csvFilePath[1]);
            return censusMap;
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

    /**
     * Used for loading stateCode in Census Map.
     * @param censusMap
     * @param csvFilePath
     * @return
     * @throws CensusAnalyserException
     */
    private int loadIndiaStateData(Map<String, CensusDAO> censusMap, String csvFilePath) throws CensusAnalyserException {
        try {
            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            ICSVBuilder csvBuilder = CSVBuilderFactory.createCSVBuilder();
            Iterator<IndianStateCSV> csvFileIterator = csvBuilder.getCSVFileIterator(reader, IndianStateCSV.class);
            Iterable<IndianStateCSV> CSVIterable = () -> csvFileIterator;
            StreamSupport.stream(CSVIterable.spliterator(), false)
                    .filter(csvStateData -> censusMap.get(csvStateData.state) != null)
                    .forEach(csvStateData -> censusMap.get(csvStateData.state).stateCode = csvStateData.stateCode);
            return censusMap.size();
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


}
