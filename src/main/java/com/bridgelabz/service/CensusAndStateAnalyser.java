package com.bridgelabz.service;

import com.bridgelabz.model.IndianCensusCSV;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.IndianStateCSV;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class CensusAndStateAnalyser {
    public int loadIndiaCensusData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            Iterator<IndianCensusCSV> censusCSVIterator = new OpenCSVBuilder().getCSVFileIterator(reader,IndianCensusCSV.class);
            return this.getCount(censusCSVIterator);
        } catch (FileNotFoundException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        } catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE);
        }
    }
    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        try (Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));){
            Iterator<IndianStateCSV> stateCSVIterator = new OpenCSVBuilder().getCSVFileIterator(reader,IndianStateCSV.class);
            return this.getCount(stateCSVIterator);
        } catch (FileNotFoundException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE);
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }
    }
    private <E> int getCount(Iterator<E> iterator){
        Iterable<E> csvIterable = () -> iterator;
        int namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
        return namOfEateries;
    }
}
