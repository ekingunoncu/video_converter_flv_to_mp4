/**
 * An enumeration that defines different video qualities with their associated parameters.
 * These parameters include the width, height, frame rate, and bit rate.
 */
package com.ekingunoncu.converter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VideoProfile {

    /**
     * Low quality with a width of 320 pixels, a height of 240 pixels,
     * a frame rate of 25 frames per second, and a bit rate of 256Kbps.
     */
    H264_LOW(320, 240, 24, 256000, VideoCodec.H264),

    /**
     * Medium quality with a width of 640 pixels, a height of 480 pixels,
     * a frame rate of 24 frames per second, and a bit rate of 512Kbps.
     */
    H264_MEDIUM(640, 480, 24, 512000, VideoCodec.H264),

    /**
     * High quality with a width of 1280 pixels, a height of 720 pixels,
     * a frame rate of 24 frames per second, and a bit rate of 1Mbps.
     */
    H264_HIGH(1280, 720, 24, 1000000, VideoCodec.H264),

    /**
     * Low quality with a width of 320 pixels, a height of 240 pixels,
     * a frame rate of 24 frames per second, and a bit rate of 256Kbps.
     */
    MPEG4_LOW(320, 240, 24, 256000, VideoCodec.MPEG4),

    /**
     * Medium quality with a width of 640 pixels, a height of 480 pixels,
     * a frame rate of 24 frames per second, and a bit rate of 512Kbps.
     */
    MPEG4_MEDIUM(640, 480, 24, 512000, VideoCodec.MPEG4),

    /**
     * High quality with a width of 1280 pixels, a height of 720 pixels,
     * a frame rate of 24 frames per second, and a bit rate of 1Mbps.
     */
    MPEG4_HIGH(1280, 720, 24, 1000000, VideoCodec.MPEG4);

    private final int width;

    private final int height;

    private final int frameRate;

    private final int bitrate;

    private final VideoCodec videoCodec;
    
}
