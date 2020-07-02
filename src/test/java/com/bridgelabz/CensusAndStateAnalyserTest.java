package com.bridgelabz;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.IndianCensusCSV;
import com.bridgelabz.model.IndianStateCSV;
import com.bridgelabz.model.usCensusCSV;
import com.bridgelabz.service.CensusAndStateAnalyser;
import com.bridgelabz.utility.Utility;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Assert;
import org.junit.Test;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class CensusAndStateAnalyserTest {
    private static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String WRONG_CSV_FILE_PATH = "./src/main/resources/IndiaStateCensusData.csv";
    private static final String NOT_CSV_FILE = "./src/test/resources/IndiaStateCensusData.txt";
    private static final String DELIMITER_HEADER_INCORRECT = "./src/test/resources/DelimiterHeaderIncorrect.csv";

    private static final String INDIA_STATE_CODE_CSV_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String NOT_STATE_CSV_FILE = "./src/test/resources/IndiaStateCode.txt";
    private static final String DELIMITER_HEADER_STATE_INCORRECT = "./src/test/resources/DelimiterHeaderStateIncorrect.csv";

    private static final String US_CENSUS_CSV_PATH = "./src/test/resources/USCensusData.csv";

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
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileExtension_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(NOT_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(DELIMITER_HEADER_INCORRECT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE, e.type);
            System.out.println(e.getMessage());
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
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateData_WithWrongFileExtension_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaStateData(NOT_STATE_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaStateData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaStateData(DELIMITER_HEADER_STATE_INCORRECT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WhenSortedOnState_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateAnalyser.getStateWiseSortedCensusDataAsPerState();
            IndianCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            Assert.assertEquals("West Bengal", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaCensusData_WrongFilePath_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateAnalyser.getStateWiseSortedCensusDataAsPerState();
            IndianCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            Assert.assertEquals("West Bengal", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void givenIndiaStateCodeData_WhenSortedOnState_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaStateData(INDIA_STATE_CODE_CSV_PATH);
            String sortedStateData = censusAndStateAnalyser.getStateWiseSortedStateCodeData();
            IndianStateCSV[] stateCSV = new Gson().fromJson(sortedStateData, IndianStateCSV[].class);
            Assert.assertEquals("AD", stateCSV[0].stateCode);
            Assert.assertEquals("WB", stateCSV[stateCSV.length-1].stateCode);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaStateCodeData_WrongFilePath_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaStateData(WRONG_STATE_CSV_PATH);
            String sortedStateData = censusAndStateAnalyser.getStateWiseSortedStateCodeData();
            IndianStateCSV[] stateCSV = new Gson().fromJson(sortedStateData, IndianStateCSV[].class);
            Assert.assertEquals("AD", stateCSV[0].stateCode);
            Assert.assertEquals("WB", stateCSV[stateCSV.length-1].stateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void givenIndiatateCodeData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaStateData(DELIMITER_HEADER_STATE_INCORRECT);
            String sortedStateData = censusAndStateAnalyser.getStateWiseSortedStateCodeData();
            IndianStateCSV[] stateCSV = new Gson().fromJson(sortedStateData, IndianStateCSV[].class);
            Assert.assertEquals("AD", stateCSV[0].stateCode);
            Assert.assertEquals("WB", stateCSV[stateCSV.length-1].stateCode);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE, e.type);
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateAnalyser.getStateWiseSortedCensusDataAsPerPopulation();
            IndianCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals(199812341, censusCSV[0].population);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaCensusData_WrongFilePathPopulation_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(WRONG_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateAnalyser.getStateWiseSortedCensusDataAsPerPopulation();
            IndianCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals(199812341, censusCSV[0].population);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void givenIndiaCensusData_ReturnSortedDataBasedOnDensity() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateAnalyser.getStateWiseSortedCensusDataAsPerDensity();
            Utility.jsonFileWriter("./src/test/resources/censusSortedByDensity.json", sortedCensusData);
            System.out.println(sortedCensusData);
        } catch (CensusAnalyserException | IOException e) {
        }
    }
    @Test
    public void givenIndiaCensusData_AfterSortSaveInJsonFile() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadIndiaCensusData(INDIA_CENSUS_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateAnalyser.getStateWiseSortedCensusDataAsPerArea();
            Utility.jsonFileWriter("./src/test/resources/censusSortedByArea.json", sortedCensusData);
        } catch (CensusAnalyserException | IOException e) {
        }
    }
    @Test
    public void givenUsCensusData_ReturnSortedDataBasedOnPopulation() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser();
            censusAndStateAnalyser.loadUSCensusData(US_CENSUS_CSV_PATH);
            String sortedUsCensusData = censusAndStateAnalyser.getPopulationWiseSortedUsCensusData();
            Utility.jsonFileWriter("./src/test/resources/UsCensusSortedByPopulation.json", sortedUsCensusData);
            System.out.println(sortedUsCensusData);
        } catch (CensusAnalyserException | IOException e) {
        }
    }
}

