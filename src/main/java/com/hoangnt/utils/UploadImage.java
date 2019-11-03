package com.hoangnt.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.web.multipart.MultipartFile;

public class UploadImage {

    public void store(MultipartFile file, String fileName,Path rootLocation) throws IOException {
        Files.copy(file.getInputStream(), rootLocation.resolve(fileName));

    }
    public Long ramdom() {
        return (long) Math.floor((Math.random() * 1000000));
    }
    public void deleteImg(String image) {
        File file = new File(image);
        if (file.exists()) {
           file.delete();
        }
    }
}
