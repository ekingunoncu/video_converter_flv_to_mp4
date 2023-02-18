package com.ekingunoncu.converter.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ekingunoncu.converter.enums.AudioProfile;
import com.ekingunoncu.converter.enums.VideoFormat;
import com.ekingunoncu.converter.enums.VideoProfile;
import com.ekingunoncu.converter.exception.VideoConversionException;
import com.ekingunoncu.converter.model.ConvertionOptions;
import com.ekingunoncu.converter.service.VideoConverter;

import lombok.RequiredArgsConstructor;

/**
 * Controller class for handling video file conversions.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/converter")
public class VideoConverterController {

        private final VideoConverter videoConverter;

        /**
         * Converts a video file synchronously and returns the converted file as a byte
         * array resource.
         * 
         * @param file              The video file to convert.
         * @param convertionOptions The conversion options to use.
         * @return A response entity containing the converted file as a byte array
         *         resource.
         * @throws IOException              If an I/O error occurs.
         * @throws VideoConversionException If an error occurs during the video
         *                                  conversion process.
         */
        @PostMapping
        public ResponseEntity<ByteArrayResource> convertVideo(@RequestParam("file") MultipartFile file,
                        @RequestParam("audioProfile") AudioProfile audioProfile,
                        @RequestParam("videoProfile") VideoProfile videoProfile,
                        @RequestParam("outputVideoFormat") VideoFormat outputVideoFormat)
                        throws IOException, VideoConversionException {

                ConvertionOptions convertionOptions = ConvertionOptions.builder()
                                .audioProfile(audioProfile)
                                .videoProfile(videoProfile)
                                .outputVideoFormat(outputVideoFormat)
                                .build();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());

                byte[] output = videoConverter.convert(inputStream, convertionOptions);

                ByteArrayResource resource = new ByteArrayResource(output);
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getOriginalFilename());
                return ResponseEntity.ok()
                                .headers(headers)
                                .contentLength(output.length)
                                .contentType(MediaType.parseMediaType("application/octet-stream"))
                                .body(resource);
        }

        /**
         * Converts a video file asynchronously and returns a response to the user
         * immediately.
         * 
         * @param file              The video file to convert.
         * @param convertionOptions The conversion options to use.
         * @return A response entity containing a message indicating that the conversion
         *         process has started, or an error message if the thread pool is at
         *         capacity.
         * @throws VideoConversionException If an error occurs during the video
         *                                  conversion process.
         * @throws IOException              If an I/O error occurs.
         */
        @PostMapping("/async")
        public ResponseEntity<String> convertVideoAsync(@RequestParam("file") MultipartFile file,
                        @RequestParam("audioProfile") AudioProfile audioProfile,
                        @RequestParam("videoProfile") VideoProfile videoProfile,
                        @RequestParam("outputVideoFormat") VideoFormat outputVideoFormat)
                        throws IOException, VideoConversionException {
                ConvertionOptions convertionOptions = ConvertionOptions.builder()
                                .audioProfile(audioProfile)
                                .videoProfile(videoProfile)
                                .outputVideoFormat(outputVideoFormat)
                                .build();

                ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
                // Start the conversion in a separate thread using the fileConversionExecutor
                // bean
                videoConverter.asyncConvert(inputStream, convertionOptions);

                // Return a response to the user immediately
                return ResponseEntity.ok("Your convert process started");
        }
}
