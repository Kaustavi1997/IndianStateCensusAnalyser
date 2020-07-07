package com.bridgelabz.adapter;

import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.CensusDAO;
import com.bridgelabz.service.CensusAndStateAnalyser;

import java.util.Map;

public class CensusAdapterFactory {
    public static Map<String, CensusDAO> getCensusData(CensusAndStateAnalyser.Country country, String... csvFilePath) throws CensusAnalyserException {
        if(country.equals(CensusAndStateAnalyser.Country.INDIA))
            return new IndiaCensusAdapter().loadCensusData(csvFilePath);
        if(country.equals(CensusAndStateAnalyser.Country.US))
            return new USCensusAdapter().loadCensusData(csvFilePath);
        throw new CensusAnalyserException("Unknown Country",CensusAnalyserException.ExceptionType.INVALID_COUNTRY);
    }
}
