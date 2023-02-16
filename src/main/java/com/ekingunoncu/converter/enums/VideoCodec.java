/**
 * An enumeration representing common video codecs with their corresponding
 * codec ID in avcodec.
 */
package com.ekingunoncu.converter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bytedeco.ffmpeg.global.avcodec;

@Getter
@AllArgsConstructor
public enum VideoCodec {

    /**
     * H.264 is a popular video codec with high video quality, compression and
     * flexibility.
     * It is commonly used for video streaming and is compatible with most devices
     * and platforms.
     */
    H264(avcodec.AV_CODEC_ID_H264),

    /**
     * MPEG-4 is a widely used video codec that offers good quality with small file
     * sizes.
     * It is commonly used for video streaming and for sharing video on the web.
     */
    MPEG4(avcodec.AV_CODEC_ID_MPEG4);

    /**
     * The integer value representing the codec in the FFmpeg library
     */
    int codec;

}
