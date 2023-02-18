package com.ekingunoncu.converter.model;

import com.ekingunoncu.converter.enums.AudioProfile;
import com.ekingunoncu.converter.enums.VideoFormat;
import com.ekingunoncu.converter.enums.VideoProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * A model class that represents the conversion options for a video file.
 */
@Data
@Builder
@AllArgsConstructor
public class ConvertionOptions {

    /**
     * 
     * The audio profile to use for the conversion.
     */
    AudioProfile audioProfile;
    /**
     * 
     * The video profile to use for the conversion.
     */
    VideoProfile videoProfile;
    /**
     * 
     * The output video format for the conversion.
     */
    VideoFormat outputVideoFormat;
    
}