package com.client.service;

import com.client.model.FileAttributes;
import com.client.sharedfileservice.SharedFileService;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;

@Service
public class FileUploadService {

    private SharedFileService sharedFileService;

    public FileUploadService(SharedFileService sharedFileService){
        this.sharedFileService = sharedFileService;
    }

    public void saveFileAttributes(String fileID, FileAttributes fileAttributes) throws FileNotFoundException{

        sharedFileService.saveAttributes(fileID, fileAttributes);
    }

    public FileAttributes getFileAttributes(String fileID) throws FileNotFoundException {

        return sharedFileService.getFileAttributes(fileID);
    }
}
