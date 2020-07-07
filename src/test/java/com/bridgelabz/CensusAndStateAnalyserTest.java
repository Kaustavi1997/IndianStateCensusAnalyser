package com.bridgelabz;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.IndianCensusCSV;
import com.bridgelabz.model.UsCensusCSV;
import com.bridgelabz.service.CensusAndStateAnalyser;
import com.bridgelabz.utility.Utility;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import java.io.IOException;

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
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            int numOfRecords = censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH,WRONG_STATE_CSV_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileExtension_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA,NOT_CSV_FILE,NOT_STATE_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void givenIndiaCensusData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA,DELIMITER_HEADER_INCORRECT,DELIMITER_HEADER_STATE_INCORRECT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE, e.type);
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void givenIndiaCensusData_WhenSortedOnState_First_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_PATH);
            String sortedCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.STATE);
            IndianCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            System.out.println(sortedCensusData);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenIndiaCensusData_WhenSortedOnState_Last_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_PATH);
            String sortedCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.STATE);
            IndianCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals("West Bengal", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenIndiaCensusData_WrongFilePath_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH);
            String sortedCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.STATE);
            IndianCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusCSV[].class);
            Assert.assertEquals("Andhra Pradesh", censusCSV[0].state);
            Assert.assertEquals("West Bengal", censusCSV[censusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM, e.type);
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void givenIndiaCensusData_WhenSortedOnPopulation_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_PATH);
            String sortedCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.POPULATION);
            IndianCensusCSV[] censusCSV = new Gson().fromJson(sortedCensusData, IndianCensusCSV[].class);
            System.out.println(sortedCensusData);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaCensusData_WrongFilePathPopulation_ShouldThrowException() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA,WRONG_CSV_FILE_PATH,WRONG_STATE_CSV_PATH);
            String sortedCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.POPULATION);
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
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA,INDIA_CENSUS_CSV_FILE_PATH,INDIA_STATE_CODE_CSV_PATH);
            String sortedCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.POPULATION_DENSITY);
            Utility.jsonFileWriter("./src/test/resources/censusSortedByDensity.json", sortedCensusData);
        } catch (CensusAnalyserException | IOException e) {
        }
    }
    @Test
    public void givenIndiaCensusData_AfterSortSaveInJsonFile() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.INDIA);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            String sortedCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.TOTAL_AREA);
            Utility.jsonFileWriter("./src/test/resources/censusSortedByArea.json", sortedCensusData);
        } catch (CensusAnalyserException | IOException e) {
        }
    }
    @Test
    public void givenUsCensusData_ReturnSortedDataBasedOnPopulation() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.US);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.US,US_CENSUS_CSV_PATH);
            String sortedUsCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.POPULATION);
            Utility.jsonFileWriter("./src/test/resources/UsCensusSortedByPopulation.json", sortedUsCensusData);
        } catch (CensusAnalyserException | IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenUsCensusData_WhenSortedOnPopulation_Highest_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.US);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.US,US_CENSUS_CSV_PATH);
            String sortedUSCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.POPULATION);
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortedUSCensusData, UsCensusCSV[].class);
            Assert.assertEquals(37253956, usCensusCSV[0].population);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUsCensusData_WhenSortedOnPopulation_Lowest_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.US);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.US,US_CENSUS_CSV_PATH);
            String sortedUSCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.POPULATION);
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortedUSCensusData, UsCensusCSV[].class);
            Assert.assertEquals(563626, usCensusCSV[usCensusCSV.length-1].population);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUsCensusData_WhenSortedOnState_Highest_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.US);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.US,US_CENSUS_CSV_PATH);
            String sortedUSCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.STATE);
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortedUSCensusData, UsCensusCSV[].class);
            Assert.assertEquals("Alabama", usCensusCSV[0].state);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUsCensusData_WhenSortedOnState_Lowest_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.US);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.US,US_CENSUS_CSV_PATH);
            String sortedUSCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.STATE);
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortedUSCensusData, UsCensusCSV[].class);
            Assert.assertEquals("Wyoming", usCensusCSV[usCensusCSV.length-1].state);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUsCensusData_WhenSortedOnPopulationDensity_Highest_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.US);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.US,US_CENSUS_CSV_PATH);
            String sortedUSCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.POPULATION_DENSITY);
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortedUSCensusData, UsCensusCSV[].class);
            Assert.assertEquals((Double)3805.61, (Double)usCensusCSV[0].populationDensity);
            System.out.println(sortedUSCensusData);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUsCensusData_WhenSortedOnPopulationDensity_Lowest_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.US);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.US,US_CENSUS_CSV_PATH);
            String sortedUSCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.POPULATION_DENSITY);
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortedUSCensusData, UsCensusCSV[].class);
            Assert.assertEquals((Double)0.46, (Double)usCensusCSV[usCensusCSV.length-1].populationDensity);
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void givenUsCensusData_WhenSortedOnTotalArea_Highest_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.US);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.US,US_CENSUS_CSV_PATH);
            String sortedUSCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.TOTAL_AREA);
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortedUSCensusData, UsCensusCSV[].class);
            Assert.assertEquals((Double)1723338.01, (Double)usCensusCSV[0].totalArea);
            System.out.println(sortedUSCensusData);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenUsCensusData_WhenSortedOnTotalArea_Lowest_ShouldReturnSortedValue() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.US);
            censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.US,US_CENSUS_CSV_PATH);
            String sortedUSCensusData = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.TOTAL_AREA);
            UsCensusCSV[] usCensusCSV = new Gson().fromJson(sortedUSCensusData, UsCensusCSV[].class);
            Assert.assertEquals((Double)177.0, (Double)usCensusCSV[usCensusCSV.length-1].totalArea);
            System.out.println(sortedUSCensusData);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void mostPopulousState() {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(CensusAndStateAnalyser.Country.US);
            System.out.println(censusAndStateAnalyser.mostPopularStateInIndiaAndUs());
        } catch (CensusAnalyserException e) {
            e.printStackTrace();
        }
    }
}
