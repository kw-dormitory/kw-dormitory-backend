package com.kw.kwdn.domain.image.controller;

import com.kw.kwdn.domain.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {
    private final ImageService imageService;

    @GetMapping("")
    public ResponseEntity<UrlResource> getImage(
            @RequestParam(value = "imagePath") String imagePath
    ) {
        if (imagePath == null) throw new IllegalArgumentException("imagePath에 대한 값이 없습니다.");

        UrlResource resource = imageService.getUrlImageResource(imagePath);
        String contentDeposition = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDeposition)
                .body(resource);
    }
}
