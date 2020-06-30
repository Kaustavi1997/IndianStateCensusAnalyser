package com.bridgelabz;

import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.service.CensusAndStateAnalyser;
import org.junit.Assert;
import org.junit.Test;

public class CensusAndStateAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String NOT_CSV_FILE = "./src/test/resources/IndiaStateCensusData.txt";
    private static final String DELIMITER_HEADER_INCORRECT = "./src/test/resources/DelimiterHeaderIncorrect.csv";

    private static final String INDIA_STATE_CODE_CSV_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String NOT_STATE_CSV_FILE = "./src/test/resources/IndiaStateCode.txt";
    private static final String DELIMITER_HEADER_STATE_INCORRECT = "./src/test/resources/DelimiterHeaderStateIncorrect.csv";


    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            int numOfRecords = censusAndStateAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFileExtension_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(NOT_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(DELIMITER_HEADER_INCORRECT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE,e.type);
        }
    }
    @Test
    public void givenIndianStateCSVFileReturnsCorrectRecords() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            int numOfRecords = censusAndStateAnalyser.loadIndiaStateData(INDIA_STATE_CODE_CSV_PATH);
            Assert.assertEquals(37, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaStateData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaStateData(WRONG_STATE_CSV_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaStateData_WithWrongFileExtension_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaStateData(NOT_STATE_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaStateData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaStateData(DELIMITER_HEADER_STATE_INCORRECT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE,e.type);
        }
    }
}
