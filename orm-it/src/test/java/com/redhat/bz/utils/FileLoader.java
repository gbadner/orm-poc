package com.redhat.bz.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public final class FileLoader {

    public static InputStream loadAsString(final String filename) {
       return load(filename);
    }

    public static String loadAsString(final InputStream inputStream) {
        final String fileAsString = new Scanner(inputStream).useDelimiter("\\A").next();
        close(inputStream);
        return fileAsString;
    }

    public static void close(final InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException("Unable to close input stream.", e);
            }
        }
    }

    public static InputStream load(final String filename) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
    }

}
