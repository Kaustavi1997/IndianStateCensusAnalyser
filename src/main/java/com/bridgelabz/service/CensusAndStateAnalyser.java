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
            List<IndianStateCSV> stateCSVList = csvBuilder.getCSVFileList(reader,IndianStateCSV.class);
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
    public String getStateWiseSortedCensusData() throws CensusAnalyserException {
        if (censusCSVList == null || censusCSVList.size() == 0){
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
        Comparator<IndianCensusCSV> censusComparator = Comparator.comparing(census -> census.state);
        this.sort(censusComparator);
        String sortedStateCensusJson = new Gson().toJson(censusCSVList);
        return sortedStateCensusJson;
    }

    private void sort(Comparator<IndianCensusCSV> censusComparator) {
        for(int i=0; i<censusCSVList.size()-1; i++){
            for(int j=0; j<censusCSVList.size()-i-1; j++){
                IndianCensusCSV census1 = censusCSVList.get(i);
                IndianCensusCSV census2 = censusCSVList.get(j+1);
                if(censusComparator.compare(census1,census2)>0){
                    censusCSVList.set(i,census2);
                    censusCSVList.set(j+1,census1);
                }
            }
    }
}
}
