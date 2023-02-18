package com.ekingunoncu.converter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bytedeco.ffmpeg.global.avcodec;

/**
 * An enumeration representing common audio codecs with their corresponding
 * codec ID in avcodec.
 */
@Getter
@AllArgsConstructor
public enum AudioCodec {

    /**
     * Advanced Audio Coding (AAC) is a widely used audio codec, known for its
     * high quality and good compression rates.
     */
    AAC(avcodec.AV_CODEC_ID_AAC),

    /**
     * Pulse-code modulation (PCM) is an uncompressed audio codec that is
     * known for its high quality, but also for its large file sizes.
     */
    PCM(avcodec.AV_CODEC_ID_PCM_S16LE);

    /**
     * The integer value representing the codec in the FFmpeg library
     */
    int codec;
}
