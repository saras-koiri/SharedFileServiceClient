package com.client.model;

import java.util.Map;

public class FileAttributes {

    private String name;
    private String description;
    private String fileType;
    private Map<String, String> customAttributes;

    public FileAttributes(String name, String description, String fileType, Map<String, String> customAttributes) {

        this.name = name;
        this.description = description;
        this.fileType = fileType;
        this.customAttributes = customAttributes;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public Map<String, String> getCustomAttributes() {
        return customAttributes;
    }

    public void setCustomAttributes(Map<String, String> customAttributes) {
        this.customAttributes = customAttributes;
    }


}
