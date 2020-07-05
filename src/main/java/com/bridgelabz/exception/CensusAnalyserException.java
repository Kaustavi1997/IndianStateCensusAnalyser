package com.bridgelabz.exception;

import java.util.List;

public class CensusAnalyserException extends Exception {

    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,DELIMITER_HEADER_ISSUE,NO_CENSUS_DATA,NO_STATE_CODE_DATA
    }
    public ExceptionType type;
    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
    public CensusAnalyserException(String message, String name) {
        super(message);
        this.type = ExceptionType.valueOf(name);
    }
    public static<E> void throwDataException(List list) throws CensusAnalyserException {
        if (list == null || list.size() == 0){
            throw new CensusAnalyserException("No Census Data",CensusAnalyserException.ExceptionType.NO_CENSUS_DATA);
        }
    }

}

