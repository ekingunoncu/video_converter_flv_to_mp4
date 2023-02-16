package com.ekingunoncu.converter.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ekingunoncu.converter.exception.VideoConversionException;
import com.ekingunoncu.converter.model.ConvertionOptions;
import com.ekingunoncu.converter.service.FfmpegVideoConverter;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/converter")
public class VideoConverterController {

    private final FfmpegVideoConverter videoConverter;

    @PostMapping
    public ResponseEntity<ByteArrayResource> convertVideo(@RequestParam("file") MultipartFile file,ConvertionOptions convertionOptions) throws IOException, VideoConversionException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
        
        byte[] output = videoConverter.convert(inputStream, convertionOptions);
      
        ByteArrayResource resource = new ByteArrayResource(output);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getOriginalFilename());
    
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(output.length)
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);}
}
