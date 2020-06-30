package com.bridgelabz.service;

import com.bridgelabz.exception.CensusAnalyserException;

import java.io.Reader;
import java.util.Iterator;

public interface ICSVBuilder<E> {
    public Iterator<E> getCSVFileIterator(Reader reader, Class csvClass) throws CSVBuilderException;
}
