package com.Gateway_Service.Gateway_Service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

@ConfigurationProperties("storage")
public class StorageConfiguration {

    /**
     * Folder location for storing files
     */

    private String location;

    public StorageConfiguration() throws IOException {
        location =  "upload";
        File uploadDir = new File("upload");
        uploadDir.mkdir();
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
