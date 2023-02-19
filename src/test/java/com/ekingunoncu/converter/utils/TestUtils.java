package com.ekingunoncu.converter.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class TestUtils {

    public static byte[] loadInputBytesFromFile(String fileName) throws IOException {
        ClassLoader classLoader = TestUtils.class.getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        return FileUtils.readFileToByteArray(file);
    }

    public static void saveOutputBytesToFile(byte[] outputBytes, String outputFileName) throws IOException {
        File outputFile = new File(outputFileName);
        FileUtils.writeByteArrayToFile(outputFile, outputBytes);
    }
}
