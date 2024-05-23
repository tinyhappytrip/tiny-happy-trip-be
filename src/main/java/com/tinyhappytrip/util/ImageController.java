package com.tinyhappytrip.util;

import jakarta.activation.MimetypesFileTypeMap;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.File;

@RestController
public class ImageController {

    @GetMapping("/image")
    private ResponseEntity<FileSystemResource> getImage(@RequestParam String path) {
        File file = new File(path);
        if (!file.exists() || !file.isFile()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        FileSystemResource resource = new FileSystemResource(file);
        String mimeType = new MimetypesFileTypeMap().getContentType(file);

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, mimeType);
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @GetMapping("/proxy")
    public ResponseEntity<Resource> proxyImage(@RequestParam String url) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            byte[] imageBytes = restTemplate.getForObject(url, byte[].class);

            ByteArrayResource resource = new ByteArrayResource(imageBytes);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", "image/jpeg"); // or the appropriate content type

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
