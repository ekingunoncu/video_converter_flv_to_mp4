package com.ekingunoncu.converter.service;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.ekingunoncu.converter.enums.AudioProfile;
import com.ekingunoncu.converter.enums.VideoFormat;
import com.ekingunoncu.converter.enums.VideoProfile;
import com.ekingunoncu.converter.exception.VideoConversionException;

/**
 * Interface for converting video files using FFmpeg.
 */
public interface VideoConverter {

    /**
     * Converts a video file to the specified format and returns the converted file
     * as a byte array.
     * 
     * @param inputStream       The input stream for the video file to be converted.
     * @param audioProfile      The audio profile to use for the conversion.
     * @param videoProfile      The video profile to use for the conversion.
     * @param outputVideoFormat The format to convert the video to.
     * @return A byte array containing the converted video file.
     * @throws VideoConversionException If an error occurs during the video
     *                                  conversion process.
     * @throws FileNotFoundException    If the input file is not found.
     */
    byte[] convert(ByteArrayInputStream inputStream, AudioProfile audioProfile, VideoProfile videoProfile,
            VideoFormat outputVideoFormat) throws VideoConversionException, FileNotFoundException;

    /**
     * Converts a video file asynchronously.
     * 
     * @param multipartFile     The video file to be converted.
     * @param audioProfile      The audio profile to use for the conversion.
     * @param videoProfile      The video profile to use for the conversion.
     * @param outputVideoFormat The format to convert the video to.
     * @throws VideoConversionException If an error occurs during the video
     *                                  conversion process.
     * @throws IOException              If an I/O error occurs.
     */
    public void asyncConvert(MultipartFile multipartFile, AudioProfile audioProfile, VideoProfile videoProfile,
            VideoFormat outputVideoFormat) throws VideoConversionException, IOException;

}
