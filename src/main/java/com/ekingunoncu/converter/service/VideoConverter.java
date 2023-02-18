package com.ekingunoncu.converter.service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;

import com.ekingunoncu.converter.exception.VideoConversionException;
import com.ekingunoncu.converter.model.ConvertionOptions;

/**
 * An interface for a video converter service that can convert video files and
 * start conversions asynchronously.
 */
public interface VideoConverter {

    /**
     * Converts a video file and returns the result as a byte array.
     * 
     * @param inputStream       The input stream of the video file to convert.
     * @param convertionOptions The conversion options to use.
     * @return The converted video file as a byte array.
     * @throws VideoConversionException If an error occurs during the conversion
     *                                  process.
     * @throws FileNotFoundException    If the input file cannot be found.
     */
    byte[] convert(ByteArrayInputStream inputStream, ConvertionOptions convertionOptions)
            throws VideoConversionException, FileNotFoundException;

    /**
     * Starts a video file conversion asynchronously, using the provided input
     * stream and conversion options.
     * 
     * @param inputStream       The input stream of the video file to convert.
     * @param convertionOptions The conversion options to use.
     * @throws VideoConversionException If an error occurs during the conversion
     *                                  process.
     * @throws FileNotFoundException    If the input file cannot be found.
     */
    public void asyncConvert(ByteArrayInputStream inputStream, ConvertionOptions convertionOptions)
            throws FileNotFoundException, VideoConversionException;

}