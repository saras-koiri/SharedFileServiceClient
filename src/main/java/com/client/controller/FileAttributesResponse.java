package com.client.controller;

import com.client.model.FileAttributes;

public class FileAttributesResponse {

    private String message;
    private FileAttributes fileAttributes;

    public FileAttributesResponse(String message, FileAttributes fileAttributes) {
        this.message = message;
        this.fileAttributes = fileAttributes;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FileAttributes getFileAttributes() {
        return fileAttributes;
    }

    public void setFileAttributes(FileAttributes fileAttributes) {
        this.fileAttributes = fileAttributes;
    }


}
