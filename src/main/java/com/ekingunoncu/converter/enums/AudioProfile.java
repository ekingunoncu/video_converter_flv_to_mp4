/**
An enumeration that defines different audio qualities with their associated parameters.
These parameters include the audio codec, bit rate, audio quality, and number of audio channels.
*/
package com.ekingunoncu.converter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AudioProfile {

    /**
     * Low quality with an AAC codec, bit rate of 64 kbps, quality of 0.2, 1
     * channel, and a sample rate of 22,050 Hz.
     */
    AAC_LOW(64000, 0.2, 1, 22050, AudioCodec.AAC),

    /**
     * Medium quality with an AAC codec, bit rate of 128 kbps, quality of 0.5, 2
     * channels, and a sample rate of 44,100 Hz.
     */
    AAC_MEDIUM(128000, 0.5, 2, 44100, AudioCodec.AAC),

    /**
     * High quality with an AAC codec, bit rate of 256 kbps, quality of 1.0, 2
     * channels, and a sample rate of 96,000 Hz.
     */
    AAC_HIGH(256000, 1.0, 2, 96000, AudioCodec.AAC);

    private final int bitRate;

    private final double quality;

    private final int channels;

    private final int sampleRate;

    private final AudioCodec audioCodec;

}