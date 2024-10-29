package com.client.sharedfileservice;

import com.client.controller.FileAttributesResponse;
import com.client.model.FileAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.io.FileNotFoundException;

@Repository
public class SharedFileServiceImpl implements SharedFileService {

    private static Logger log = LoggerFactory.getLogger(SharedFileServiceImpl.class);

    private final RestTemplate restTemplate;
    private final String baseURL;

    public SharedFileServiceImpl(@Value("${sharedFileServiceBaseURL}") String sharedFileServiceBaseURL) {

        baseURL = sharedFileServiceBaseURL;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setUriTemplateHandler(new DefaultUriBuilderFactory(sharedFileServiceBaseURL));

        this.restTemplate = restTemplate;
    }

    @Override
    public void saveAttributes(String fileID, FileAttributes fileAttributes) throws FileNotFoundException{


        String url = baseURL + String.format("/sharedfileuploadapi/file/%s/attributes", fileID);

        ResponseEntity<FileAttributes> responseEntity = null;
        try {
           String response = restTemplate.postForObject(url, fileAttributes, String.class);
           log.info(response);
        } catch (HttpClientErrorException.NotFound e) {

            throw new FileNotFoundException();
        }



    }

    @Override
    public FileAttributes getFileAttributes(String fileID) throws FileNotFoundException{

        String url = baseURL + String.format("/sharedfileuploadapi/file/%s/attributes", fileID);

        HttpEntity<String> entity = new HttpEntity<String>(new HttpHeaders());

        ResponseEntity<FileAttributes> responseEntity = null;
        try {
            responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, FileAttributes.class);
        } catch (HttpClientErrorException.NotFound e) {

            throw new FileNotFoundException();
        }

        return responseEntity.getBody();
    }
}
