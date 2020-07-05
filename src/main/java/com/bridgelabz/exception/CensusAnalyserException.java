package com.bridgelabz.exception;

public class CensusAnalyserException extends Exception {
    /**
     * declaring enum for Custom ExceptionType
     */
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,DELIMITER_HEADER_ISSUE,INVALID_COUNTRY
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

}

