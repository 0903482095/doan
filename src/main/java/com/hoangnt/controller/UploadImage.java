package com.hoangnt.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.multipart.MultipartFile;

public class UploadImage {

    public void store(MultipartFile file, String fileName,Path rootLocation) throws IOException {
        Files.copy(file.getInputStream(), rootLocation.resolve(fileName));

    }
    public Long ramdom() {
        return (long) Math.floor((Math.random() * 1000000));
    }
//    void deleteImg(String image) {
//            String src = "/home/nbthanh/Du-An/Back-end-story/src/main/resources/upload-dir/" + uploadList.get(i).getSrcImg();
//            File file = new File(src);
//            if (file.exists()) {
//                file.delete();
//            }
//    }
}
