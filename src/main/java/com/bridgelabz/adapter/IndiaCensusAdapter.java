package com.bridgelabz.adapter;

import com.bridgelabz.adapter.CensusAdapter;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.CensusDAO;
import com.bridgelabz.model.IndianCensusCSV;
import com.bridgelabz.model.IndianStateCSV;
import csvbuilder.CSVBuilderException;
import csvbuilder.CSVBuilderFactory;
import csvbuilder.ICSVBuilder;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.StreamSupport;

public class IndiaCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        Map<String, CensusDAO> censusMap = super.loadCensusData(IndianCensusCSV.class,csvFilePath[0]);
        this.loadIndiaStateData(censusMap,csvFilePath[1]);
        return censusMap;
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
