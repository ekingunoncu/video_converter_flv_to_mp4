package com.ekingunoncu.converter.model;

import com.ekingunoncu.converter.enums.AudioProfile;
import com.ekingunoncu.converter.enums.VideoFormat;
import com.ekingunoncu.converter.enums.VideoProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ConvertionOptions {

    AudioProfile audioProfile;

    VideoProfile videoProfile;

    VideoFormat outputVideoFormat;

}
