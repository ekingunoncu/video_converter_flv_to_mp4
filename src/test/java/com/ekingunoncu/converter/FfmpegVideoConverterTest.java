package com.ekingunoncu.converter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ekingunoncu.converter.enums.AudioProfile;
import com.ekingunoncu.converter.enums.VideoFormat;
import com.ekingunoncu.converter.enums.VideoProfile;
import com.ekingunoncu.converter.exception.VideoConversionException;
import com.ekingunoncu.converter.model.ConvertionOptions;
import com.ekingunoncu.converter.service.FfmpegVideoConverter;

public class FfmpegVideoConverterTest {

    private FfmpegVideoConverter converter;

    @BeforeEach
    public void setUp() {
        converter = new FfmpegVideoConverter();
    }

    @Test
    public void testConvert_shouldConvertVideoWithGivenOptions()
            throws VideoConversionException, IOException, InterruptedException {
        // Given
        byte[] inputBytes = loadInputBytesFromFile("input.flv");
        for (AudioProfile audioProfile : AudioProfile.values()) {
            for (VideoProfile videoProfile : VideoProfile.values()) {

                ConvertionOptions conversionOptions = ConvertionOptions.builder()
                        .audioProfile(audioProfile)
                        .videoProfile(videoProfile)
                        .outputVideoFormat(videoProfile.getVideoFormat())
                        .build();

                System.out.println("Converting video with options: " + conversionOptions);
                // When
                byte[] outputBytes = converter.convert(new ByteArrayInputStream(inputBytes), conversionOptions);

                // Then
                assertNotNull(outputBytes, "Output bytes should not be null");
                assertTrue(outputBytes.length > 0, "Output bytes should not be empty");
                String outputFileName = getOutputFileName(videoProfile, audioProfile, videoProfile.getVideoFormat());
                saveOutputBytesToFile(outputBytes, outputFileName);
                // TODO: Add additional assertions to verify the output video
                // For example, you might verify that the output video has the expected codec,
                // dimensions, and bitrate
            }

        }
    }

    private byte[] loadInputBytesFromFile(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return FileUtils.readFileToByteArray(file);
    }

    private String getOutputFileName(VideoProfile videoProfile, AudioProfile audioProfile, VideoFormat videoFormat) {
        return "outputs/videoProfile_" + videoProfile.name() + "_audioProfile_" + audioProfile.name() + "_output."
                + videoFormat.getValue();
    }

    private void saveOutputBytesToFile(byte[] outputBytes, String outputFileName) throws IOException {
        File outputFile = new File(outputFileName);
        FileUtils.writeByteArrayToFile(outputFile, outputBytes);
    }
}
