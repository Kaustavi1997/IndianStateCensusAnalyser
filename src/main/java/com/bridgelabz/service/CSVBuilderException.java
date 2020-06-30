package com.bridgelabz.service;

import com.bridgelabz.exception.CensusAnalyserException;

public class CSVBuilderException extends Exception {
    public enum ExceptionType {
        CENSUS_FILE_PROBLEM,UNABLE_TO_PARSE,DELIMITER_HEADER_ISSUE
    }
    public ExceptionType type;

    public CSVBuilderException(String message, ExceptionType type) {
        super(message);
        this.type = type;
    }
}
