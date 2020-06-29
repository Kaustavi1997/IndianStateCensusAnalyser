package com.bridgelabz.service;

import com.bridgelabz.model.IndianStateCSV;
import com.bridgelabz.exception.CensusAnalyserException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.StreamSupport;

public class StateAnalyser {
    public int loadIndiaStateData(String csvFilePath) throws CensusAnalyserException {
        try {

            Reader reader = Files.newBufferedReader(Paths.get(csvFilePath));
            CsvToBeanBuilder<IndianStateCSV> csvToBeanBuilder = new CsvToBeanBuilder<>(reader);
            csvToBeanBuilder.withType(IndianStateCSV.class);
            csvToBeanBuilder.withIgnoreLeadingWhiteSpace(true);
            CsvToBean<IndianStateCSV> csvToBean = csvToBeanBuilder.build();
            Iterator<IndianStateCSV> censusCSVIterator = csvToBean.iterator();
            Iterable<IndianStateCSV> csvIterable = () -> censusCSVIterator;
            int namOfEateries = (int) StreamSupport.stream(csvIterable.spliterator(), false).count();
            return namOfEateries;
        } catch (FileNotFoundException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.FILE_NOT_FOUND);
        } catch (IllegalStateException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.UNABLE_TO_PARSE);
        }catch (IOException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM);
        }catch (RuntimeException e) {
            throw new CensusAnalyserException(e.getMessage(),
                    CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE);
        }
    }
}
