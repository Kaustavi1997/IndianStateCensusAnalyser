package com.bridgelabz;

import com.bridgelabz.adapter.CensusAdapter;
import com.bridgelabz.adapter.CensusAdapterFactory;
import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.CensusDAO;
import com.bridgelabz.model.IndianCensusCSV;
import com.bridgelabz.service.CensusAndStateAnalyser;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.HashMap;
import java.util.Map;

public class CensusAndStateAnalyserMockitoTest {
    public static final String INDIA_CENSUS_CSV_FILE_PATH = "./src/test/resources/IndiaStateCensusData.csv";
    private static final String INDIA_STATE_CODE_CSV_PATH = "./src/test/resources/IndiaStateCode.csv";
    @Mock
    private CensusAdapterFactory censusAdapterFactory;

//    @InjectMocks
//    private CensusAndStateAnalyser censusAndStateAnalyser;

    Map<String, CensusDAO> censusMapIndia;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Before
    public void set() throws CensusAnalyserException{
        this.censusMapIndia = new HashMap<>();
        this.censusMapIndia.put("Bihar",new CensusDAO("Bihar",103804637,94163,1102,"BH"));
        this.censusMapIndia.put("Haryana",new CensusDAO("Haryana",25353081,44212,573,"HR"));
        this.censusMapIndia.put("Goa",new CensusDAO("Goa",1457723,3702,394,"GA"));
        this.censusMapIndia.put("Assam",new CensusDAO("Assam",31169272,78438,397,"AS"));
    }
    @Test
    public void givenIndiaCensusFile_ShouldReturnCorrectRecords() throws CensusAnalyserException {
        try {
            CensusAndStateAnalyser censusAndStateAnalyser = mock(CensusAndStateAnalyser.class);
            when(censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH))
                    .thenReturn(this.censusMapIndia.size());
            int records = censusAndStateAnalyser.loadCensusData(CensusAndStateAnalyser.Country.INDIA, INDIA_CENSUS_CSV_FILE_PATH, INDIA_STATE_CODE_CSV_PATH);
            Assert.assertEquals(4, records);
        }catch (CensusAnalyserException e){
            e.printStackTrace();
        }
    }
    @Test
    public void givenIndiaCensusFile_Sort() throws CensusAnalyserException {
        try {
            CensusAdapter censusAdapter = mock(CensusAdapter.class);
            when(censusAdapter.loadCensusData(IndianCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH))
                    .thenReturn(this.censusMapIndia);
            CensusAndStateAnalyser censusAndStateAnalyser = new CensusAndStateAnalyser(censusAdapter.loadCensusData(IndianCensusCSV.class,INDIA_CENSUS_CSV_FILE_PATH), CensusAndStateAnalyser.Country.INDIA);
            String sortedByState = censusAndStateAnalyser.sortForAll(CensusAndStateAnalyser.Fields.STATE);
            IndianCensusCSV[] censusCSV = new Gson().fromJson(sortedByState, IndianCensusCSV[].class);
            Assert.assertEquals("Assam", censusCSV[0].state);
        }catch (CensusAnalyserException e){
            e.printStackTrace();
        }
    }

}
