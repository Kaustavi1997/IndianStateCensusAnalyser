package com.bridgelabz;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.service.StateAnalyser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class StateAnalyserTest {
    private static final String INDIA_STATE_CODE_CSV_PATH = "./src/test/resources/IndiaStateCode.csv";
    private static final String WRONG_STATE_CSV_PATH = "./src/main/resources/IndiaStateCode.csv";

    @Test
    public void givenIndianCensusCSVFileReturnsCorrectRecords() {
        try {
            StateAnalyser stateAnalyser = new StateAnalyser();
            int numOfRecords = stateAnalyser.loadIndiaStateData(INDIA_STATE_CODE_CSV_PATH);
            Assert.assertEquals(29, numOfRecords);
        } catch (CensusAnalyserException e) {
        }
    }
    @Test
    public void givenIndiaCensusData_WithWrongFile_ShouldThrowException() {
        try {
            StateAnalyser stateAnalyser = new StateAnalyser();
            ExpectedException exceptionRule = ExpectedException.none();
            exceptionRule.expect(CensusAnalyserException.class);
            stateAnalyser.loadIndiaStateData(WRONG_STATE_CSV_PATH);
        } catch (CensusAnalyserException e) {
            Assert.assertEquals(CensusAnalyserException.ExceptionType.CENSUS_FILE_PROBLEM,e.type);
        }
    }

}
