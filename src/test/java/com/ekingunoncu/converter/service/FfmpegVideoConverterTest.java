package com.ekingunoncu.converter.service;

import static com.ekingunoncu.converter.utils.TestUtils.loadInputBytesFromFile;
import static com.ekingunoncu.converter.utils.TestUtils.saveOutputBytesToFile;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.ekingunoncu.converter.enums.AudioProfile;
import com.ekingunoncu.converter.enums.VideoFormat;
import com.ekingunoncu.converter.enums.VideoProfile;
import com.ekingunoncu.converter.exception.VideoConversionException;

/**
 * 
 * Tests for {@link FfmpegVideoConverter} class
 */
class FfmpegVideoConverterTest {
    private FfmpegVideoConverter converter;

    /**
     * Sets up the test fixture
     */
    @BeforeEach
    public void setUp() {
        converter = new FfmpegVideoConverter(new S3UploadService(AmazonS3ClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withRegion("video-converter-flv-to-mp4")
                .build()), null);
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
    void testConvert_shouldConvertVideoWithGivenOptions()
            throws VideoConversionException, IOException, InterruptedException {
        // Given
        byte[] inputBytes = loadInputBytesFromFile("jimmy_page_solo.flv");
        for (AudioProfile audioProfile : AudioProfile.values()) {
            for (VideoProfile videoProfile : VideoProfile.values()) {
                // When
                byte[] outputBytes = converter.convert(new ByteArrayInputStream(inputBytes), audioProfile, videoProfile);
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

}