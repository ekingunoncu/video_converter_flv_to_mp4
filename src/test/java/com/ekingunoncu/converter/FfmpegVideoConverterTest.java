package com.ekingunoncu.converter;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import com.ekingunoncu.converter.enums.AudioProfile;
import com.ekingunoncu.converter.enums.VideoFormat;
import com.ekingunoncu.converter.enums.VideoProfile;
import com.ekingunoncu.converter.exception.VideoConversionException;
import com.ekingunoncu.converter.model.ConvertionOptions;
import com.ekingunoncu.converter.service.FfmpegVideoConverter;

public class FfmpegVideoConverterTest {

    private FfmpegVideoConverter converter;

    @Test
    public void testConvert() throws VideoConversionException, IOException, InterruptedException {
        // Given
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("./input.flv").getFile());
        byte[] inputBytes = FileUtils.readFileToByteArray(file);
        converter = new FfmpegVideoConverter();
        for (AudioProfile audioProfile : AudioProfile.values()) {
            for (VideoProfile videoProfile : VideoProfile.values()) {
                for (VideoFormat videoType : VideoFormat.values()) {
                    ConvertionOptions convertionOptions = ConvertionOptions.builder()
                            .audioProfile(audioProfile)
                            .videoProfile(videoProfile)
                            .outputVideoFormat(videoType)
                            .build();
                    try {
                        // When
                        // Convert the test video using the FfmpegVideoConverter
                        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputBytes);
                        byte[] outputBytes = converter.convert(inputStream, convertionOptions);
                        // Then
                        // Ensure the output video is not empty
                        assertNotNull(outputBytes);
                        assertTrue(outputBytes.length > 0);
                        FileUtils.writeByteArrayToFile(new File(
                                "outputs/videoProfile_" + videoProfile.name()
                                        + "_audioProfile_" + audioProfile.name()
                                        + "_output." + videoType.getValue()),
                                outputBytes);
                        // TODO: Add additional assertions to verify the output video
                        // For example, you might verify that the output video has the expected codec,
                        // dimensions, and bitrate
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

        }
    }
}
