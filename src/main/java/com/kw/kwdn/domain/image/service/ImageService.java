package com.kw.kwdn.domain.image.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private String IMAGE_RESOURCE_PATH = "src/main/resources/image/";

    public File getImageFile(String imagePath) {
        return new File(IMAGE_RESOURCE_PATH + imagePath);
    }

    public UrlResource getUrlImageResource(String imagePath) {
        File file = getImageFile(imagePath);
        try {
            return new UrlResource("file:" + file.getAbsolutePath());
        } catch (MalformedURLException e) {
            throw new IllegalStateException("이미지를 처리하는 과정에서 문제가 발생하였습니다.");
        }
    }

    public String save(MultipartFile file, String fileName) {
        Path path = Paths.get(IMAGE_RESOURCE_PATH + fileName);
        try {
            Files.write(path, file.getBytes());
        } catch (IOException e) {
            throw new IllegalStateException("이미지를 저장하는 도중에 문제가 발생하였습니다.");
        }
        return fileName;
    }
}
