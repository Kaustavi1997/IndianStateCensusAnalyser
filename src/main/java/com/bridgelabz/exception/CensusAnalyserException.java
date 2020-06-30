package com.bridgelabz.exception;

import com.opencsv.exceptions.CsvException;

public class CensusAnalyserException extends Exception {
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,DELIMITER_HEADER_ISSUE
    }
    public ExceptionType type;
    public CensusAnalyserException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }

}

