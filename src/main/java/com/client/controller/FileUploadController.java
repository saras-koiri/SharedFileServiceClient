package com.client.controller;

import com.client.model.FileAttributes;
import com.client.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;


@RestController
public class FileUploadController {

    private static Logger log = LoggerFactory.getLogger(FileUploadController.class);

    private final FileUploadService sharedFileUploadService;

    public FileUploadController(FileUploadService sharedFileUploadService){
        this.sharedFileUploadService = sharedFileUploadService;
    }

    @PostMapping("/api/file/{id}/attributes")
    public ResponseEntity<String> saveAttributes(@PathVariable String id,
            @RequestBody FileAttributes attributes) {

        // Access the attributes
        log.info("Received file ID from path: " + id);
        log.info("Received file name: " + attributes.getName());
        log.info("Description: " + attributes.getDescription());
        log.info("File Type: " + attributes.getFileType());

        // Process custom attributes if provided
        if (attributes.getCustomAttributes() != null) {
            attributes.getCustomAttributes().forEach((key, value) ->
                    log.info("Custom Attribute - " + key + ": " + value));
        }


        try {
            sharedFileUploadService.saveFileAttributes(id, attributes);
            String response = "File attributes saved successfully with ID: " + id;
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }


    }

    @GetMapping("/api/file/{id}/attributes")
    public ResponseEntity<FileAttributesResponse> getAttributes(@PathVariable String id) {

        log.info("Get attributes for file ID: " + id);

        FileAttributesResponse fileAttributesResponse = null;
        try {
            FileAttributes fileAttributes = sharedFileUploadService.getFileAttributes(id);
            fileAttributesResponse = new FileAttributesResponse("Successfully found", fileAttributes);

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new FileAttributesResponse("File not found", null));
        }
        return ResponseEntity.status(HttpStatus.OK).body(fileAttributesResponse);
    }
}
