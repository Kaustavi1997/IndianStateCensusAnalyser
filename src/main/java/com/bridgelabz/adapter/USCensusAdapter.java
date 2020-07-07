package com.bridgelabz.adapter;

import com.bridgelabz.exception.CensusAnalyserException;
import com.bridgelabz.model.CensusDAO;
import com.bridgelabz.model.UsCensusCSV;

import java.util.Map;

public class USCensusAdapter extends CensusAdapter {
    @Override
    public Map<String, CensusDAO> loadCensusData(String... csvFilePath) throws CensusAnalyserException {
        return super.loadCensusData(UsCensusCSV.class, csvFilePath[0]);
    }
}
