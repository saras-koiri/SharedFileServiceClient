package com.client.sharedfileservice;

import com.client.model.FileAttributes;
import org.springframework.stereotype.Repository;

import java.io.FileNotFoundException;


public interface SharedFileService {

    public void saveAttributes(String fileID, FileAttributes fileAttributes) throws FileNotFoundException;

    public FileAttributes getFileAttributes(String fileID) throws FileNotFoundException;
}
