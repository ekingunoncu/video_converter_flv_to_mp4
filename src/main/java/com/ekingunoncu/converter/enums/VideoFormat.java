/**
An enumeration representing common video formats.
*/
package com.ekingunoncu.converter.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VideoFormat {

    /**
     * The MP4 video format.
     */
    MP4("mp4"),

    /**
     * The FLV video format.
     */
    FLV("flv");

    /**
     * The string value representing the video format.
     */
    private final String value;

}