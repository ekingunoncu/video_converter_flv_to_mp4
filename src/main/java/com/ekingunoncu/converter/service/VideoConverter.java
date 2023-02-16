package com.ekingunoncu.converter.service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import com.ekingunoncu.converter.enums.VideoFormat;
import com.ekingunoncu.converter.exception.VideoConversionException;
import com.ekingunoncu.converter.model.ConvertionOptions;

public interface VideoConverter {
    byte[] convert(ByteArrayInputStream inputStream, ConvertionOptions convertionOptions)
            throws VideoConversionException, FileNotFoundException;
}