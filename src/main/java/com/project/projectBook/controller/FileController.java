package com.project.projectBook.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@CrossOrigin
@RestController
@RequestMapping("api/file")
public class FileController {

    private final Path root = Paths.get("Pics");

    // http://localhost:8082/api/file/getImg?path=ten anhr.jpg
    @GetMapping(
            value = "getImg",
            produces = MediaType.IMAGE_JPEG_VALUE
    )
    public ResponseEntity<byte[]> getImage(@RequestParam String path) throws IOException {

        Resource imgFile = load(path);

        byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
    }

    // http://localhost:8082/api/file/upload
    @PostMapping("upload")
    public ResponseEntity<?> uploadFile(@RequestParam(value = "files", required = false) MultipartFile[] files ) {

        String filename = "";
        for(MultipartFile file:files) {
            save(file);
            filename += file.getOriginalFilename() + ",";
        }
        return ResponseEntity.ok(filename.substring(0, filename.length()-1));
    }

    public void save(MultipartFile file) {
        try {
            if(!this.root.resolve(file.getOriginalFilename()).toFile().exists()) {
                InputStream in = file.getInputStream();
                Files.copy(in, this.root.resolve(file.getOriginalFilename()));
                in.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);

            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new RuntimeException("coundn't not read the file!");
            }
        }
        catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
