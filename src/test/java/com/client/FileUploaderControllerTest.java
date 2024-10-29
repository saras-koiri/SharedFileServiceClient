package com.client;

import com.client.controller.FileAttributesResponse;
import com.client.controller.FileUploadController;
import com.client.model.FileAttributes;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;
import static org.mockserver.matchers.Times.exactly;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(controllers = FileUploadController.class)
public class FileUploaderControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private static ClientAndServer mockServer;


    @BeforeAll
    public static void init(){

        mockServer = startClientAndServer(1080);
    }

    @AfterAll
    public static void cleanup(){

        mockServer.stop();
    }




    @Test
    public void givenFile_withValidAttributes_whenSaved_shouldReturnSuccess() throws Exception {


        createExpectationForPOSTFileAttributeWithValidFileID();

        Map<String, String> customAttributes = new HashMap<>();
        customAttributes.put("customAttribute1", "customAttribute1Value");
        customAttributes.put("customAttribute2", "customAttribute2Value");
        customAttributes.put("customAttribute3", "customAttribute3Value");

        FileAttributes fileAttributes = new FileAttributes("name", "description", "type", customAttributes);
        String id = "1234";

        mockMvc.perform(MockMvcRequestBuilders.post( "/api/file/"+ id +"/attributes")
                .content(new ObjectMapper().writeValueAsString (fileAttributes))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("File attributes saved successfully with ID: " + id)));
    }

    @Test
    public void givenFile_withInvalidFileID_whenSaved_shouldReturnFileNotFound() throws Exception {


        createExpectationForPOSTFileAttributeWithInvalidFileID();

        Map<String, String> customAttributes = new HashMap<>();
        customAttributes.put("customAttribute1", "customAttribute1Value");
        customAttributes.put("customAttribute2", "customAttribute2Value");
        customAttributes.put("customAttribute3", "customAttribute3Value");

        FileAttributes fileAttributes = new FileAttributes("name", "description", "type", customAttributes);
        String id = "8888";

        mockMvc.perform(MockMvcRequestBuilders.post( "/api/file/"+ id +"/attributes")
                        .content(new ObjectMapper().writeValueAsString (fileAttributes))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("File not found")));
    }



    @Test
    public void givenFile_ID_whenFileAttributesRetrieved_shouldReturnFileAttributes() throws Exception {

        createExpectationForGETFileAttributeWithValidFileID();

        Map<String, String> customAttributes = new HashMap<>();
        customAttributes.put("customAttribute1", "customAttribute1Value");
        customAttributes.put("customAttribute2", "customAttribute2Value");
        customAttributes.put("customAttribute3", "customAttribute3Value");

        FileAttributes fileAttributes = new FileAttributes("name", "description", "type", customAttributes);
        String id = "1234";
        FileAttributesResponse fileAttributesResponse = new FileAttributesResponse("Successfully found", fileAttributes);

        String expectedJson = new ObjectMapper().writeValueAsString(fileAttributesResponse);

        mockMvc.perform(MockMvcRequestBuilders.get( "/api/file/"+ id +"/attributes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }



    @Test
    public void givenInvalidFile_ID_whenRetrieved_shouldReturn404Error() throws Exception {

        createExpectationForGETFileAttributeWithInvalidFileID();

        String id = "8888";
        FileAttributesResponse fileAttributesResponse = new FileAttributesResponse("File not found", null);

        String expectedJson = new ObjectMapper().writeValueAsString(fileAttributesResponse);

        mockMvc.perform(MockMvcRequestBuilders.get( "/api/file/"+ id +"/attributes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string(expectedJson));
    }

    private void createExpectationForPOSTFileAttributeWithValidFileID() throws Exception{

        Map<String, String> customAttributes = new HashMap<>();
        customAttributes.put("customAttribute1", "customAttribute1Value");
        customAttributes.put("customAttribute2", "customAttribute2Value");
        customAttributes.put("customAttribute3", "customAttribute3Value");

        FileAttributes fileAttributes = new FileAttributes("name", "description", "type", customAttributes);
        String id = "1234";

        String expectedJson = new ObjectMapper().writeValueAsString(fileAttributes);

        new MockServerClient("127.0.0.1", 1080)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/sharedfileuploadapi/file/"+id+"/attributes")
                                .withHeader("\"Content-type\", \"application/json\""),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(expectedJson)
                                .withDelay(TimeUnit.SECONDS,1)
                );
    }

    private void createExpectationForPOSTFileAttributeWithInvalidFileID() throws Exception{

        Map<String, String> customAttributes = new HashMap<>();
        customAttributes.put("customAttribute1", "customAttribute1Value");
        customAttributes.put("customAttribute2", "customAttribute2Value");
        customAttributes.put("customAttribute3", "customAttribute3Value");

        FileAttributes fileAttributes = new FileAttributes("name", "description", "type", customAttributes);
        String id = "8888";

        String expectedJson = new ObjectMapper().writeValueAsString(fileAttributes);

        new MockServerClient("127.0.0.1", 1080)
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/sharedfileuploadapi/file/"+id+"/attributes")
                                .withHeader("\"Content-type\", \"application/json\""),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(404)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody("File not found")
                                .withDelay(TimeUnit.SECONDS,1)
                );
    }


    private void createExpectationForGETFileAttributeWithValidFileID() throws Exception{

        Map<String, String> customAttributes = new HashMap<>();
        customAttributes.put("customAttribute1", "customAttribute1Value");
        customAttributes.put("customAttribute2", "customAttribute2Value");
        customAttributes.put("customAttribute3", "customAttribute3Value");

        FileAttributes fileAttributes = new FileAttributes("name", "description", "type", customAttributes);
        String id = "1234";

        String expectedJson = new ObjectMapper().writeValueAsString(fileAttributes);

        new MockServerClient("127.0.0.1", 1080)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/sharedfileuploadapi/file/"+id+"/attributes")
                                .withHeader("\"Content-type\", \"application/json\""),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(expectedJson)
                                .withDelay(TimeUnit.SECONDS,1)
                );
    }

    private void createExpectationForGETFileAttributeWithInvalidFileID() throws Exception{



        FileAttributesResponse fileAttributesResponse = new FileAttributesResponse("File not found", null);

        String expectedJson = new ObjectMapper().writeValueAsString(fileAttributesResponse);
        String id = "8888";

        new MockServerClient("127.0.0.1", 1080)
                .when(
                        request()
                                .withMethod("GET")
                                .withPath("/sharedfileuploadapi/file/"+id+"/attributes")
                                .withHeader("\"Content-type\", \"application/json\""),
                        exactly(1))
                .respond(
                        response()
                                .withStatusCode(404)
                                .withHeaders(
                                        new Header("Content-Type", "application/json; charset=utf-8"),
                                        new Header("Cache-Control", "public, max-age=86400"))
                                .withBody(expectedJson)
                                .withDelay(TimeUnit.SECONDS,1)
                );
    }


}
