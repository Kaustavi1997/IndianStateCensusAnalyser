package com.bridgelabz;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.service.StateAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateAnalyserTest {
    private static final String INDIA_STATE_CODE_CSV_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_PATH = "./src/main/resources/IndiaStateCode.csv";
    private static final String NOT_STATE_CSV_FILE = "./src/test/resources/IndiaStateCode.txt";
    private static final String DELIMITER_STATE_INCORRECT = "./src/test/resources/DelimiterStateIncorrect.csv";
    private static final String HEADER__STATE_INCORRECT = "./src/test/resources/WrongHeaderState.csv";

    @Test
    public void givenIndianStateCSVFileReturnsCorrectRecords() {
        try {
            StateAnalyser stateAnalyser = new StateAnalyser();
            int numOfRecords = stateAnalyser.loadIndiaStateData(INDIA_STATE_CODE_CSV_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaStateData_WithWrongFile_ShouldThrowException() {
        try {
            StateAnalyser stateAnalyser = new StateAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateAnalyser.loadIndiaStateData(WRONG_STATE_CSV_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaStateData_WithWrongFileExtension_ShouldThrowException() {
        try {
            StateAnalyser stateAnalyser = new StateAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateAnalyser.loadIndiaStateData(NOT_STATE_CSV_FILE);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }
    @Test
    public void givenIndiaStateData_WithWrongFileDelimiter_ShouldThrowException() {
        try {
            StateAnalyser stateAnalyser = new StateAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateAnalyser.loadIndiaStateData(DELIMITER_STATE_INCORRECT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE,e.type);
        }
    }
    @Test
    public void givenIndiaStateData_WithWrongFileHeader_ShouldThrowException() {
        try {
            StateAnalyser stateAnalyser = new StateAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateAnalyser.loadIndiaStateData(HEADER__STATE_INCORRECT);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.DELIMITER_HEADER_ISSUE,e.type);
        }
    }

}
