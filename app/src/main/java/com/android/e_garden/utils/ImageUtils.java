package com.android.e_garden.utils;

import java.io.File;
import java.io.IOException;

public class ImageUtils {

    private static ImageUtils instance;

    public static ImageUtils getInstance() {
        if (instance == null) {
            instance = new ImageUtils();
        }
        return instance;
    }

    public File createImageFile(String imageName, File storageDir) {
        try {
            return File.createTempFile(
                    imageName,
                    ".jpg",
                    storageDir
            );
        } catch (IOException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
