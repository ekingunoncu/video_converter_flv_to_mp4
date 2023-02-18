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

/**

Tests for {@link FfmpegVideoConverter} class
*/
public class FfmpegVideoConverterTest {
    private FfmpegVideoConverter converter;

    /**
     * Sets up the test fixture
     */
    @BeforeEach
    public void setUp() {
        converter = new FfmpegVideoConverter();
    }

    /**
     * Test for the {@link FfmpegVideoConverter#convert} method
     * <p>
     * Verifies that the video conversion works as expected with different
     * conversion options
     *
     * @throws VideoConversionException if an error occurs during the video
     *                                  conversion process
     * @throws IOException              if an I/O error occurs
     * @throws InterruptedException     if the thread is interrupted while sleeping
     */
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

    /**
     * 
     * Loads the bytes of the file with the specified filename from the classpath,
     * and returns them as a byte array.
     * 
     * @param fileName the name of the file to load from the classpath
     * @return a byte array containing the contents of the file
     * @throws IOException if an I/O error occurs while reading the file
     */
    private byte[] loadInputBytesFromFile(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return FileUtils.readFileToByteArray(file);
    }

    /**
     * 
     * Constructs an output file name using the given VideoProfile, AudioProfile,
     * and VideoFormat objects.
     * 
     * @param videoProfile the VideoProfile object to include in the file name
     * @param audioProfile the AudioProfile object to include in the file name
     * @param videoFormat  the VideoFormat object to include in the file name
     * @return a string containing the constructed file name
     */
    private String getOutputFileName(VideoProfile videoProfile, AudioProfile audioProfile, VideoFormat videoFormat) {
        return "outputs/videoProfile_" + videoProfile.name() + "_audioProfile_" + audioProfile.name() + "_output."
                + videoFormat.getValue();
    }

    /**
     * 
     * Saves the given byte array to a file with the specified name.
     * 
     * @param outputBytes    the byte array to write to the file
     * @param outputFileName the name of the file to write to
     * @throws IOException if an I/O error occurs while writing the file
     */
    private void saveOutputBytesToFile(byte[] outputBytes, String outputFileName) throws IOException {
        File outputFile = new File(outputFileName);
        FileUtils.writeByteArrayToFile(outputFile, outputBytes);
    }
}