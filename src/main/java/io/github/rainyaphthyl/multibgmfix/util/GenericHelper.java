package io.github.rainyaphthyl.multibgmfix.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Static methods
 */
public class GenericHelper {
    public static final Logger LOGGER = LogManager.getLogger();

    public static void copyFile(File src, File dest) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(src)) {
            try (FileOutputStream outputStream = new FileOutputStream(dest);
                 FileChannel inChannel = inputStream.getChannel();
                 FileChannel outChannel = outputStream.getChannel()) {
                outChannel.transferFrom(inChannel, 0, inChannel.size());
            }
        }
    }
}
