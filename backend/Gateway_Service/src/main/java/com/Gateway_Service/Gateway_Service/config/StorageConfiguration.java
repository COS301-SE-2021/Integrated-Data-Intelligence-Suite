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
    //String userDirectory = System.getProperty("user.dir");
    //String userDirectory =  new File(this.location).getAbsolutePath();
    //Path userDirectory = Paths.get("../upload");
    //Path resolvedPath = path.resolveSibling(path2);
    //String userDirectory = new File(".").getCanonicalPath();

    File resource = new ClassPathResource(".").getFile();
    String userDirectory = resource.getAbsolutePath();

    //new ClassPathResource("upload", this.getClass().getClassLoader()).;


    //URL fileUrl = StorageConfiguration.class.getClassLoader().getResource("src/main/java/com/Gateway_Service/Gateway_Service/upload");
    //String userDirectory = fileUrl.getPath();

    //ServletContext context = getServletContext();
    //String fullPath = context.getRealPath("/data");

    //new File("src/main/java/com/Gateway_Service/Gateway_Service/upload").getAbsolutePath();//

    private String location =  userDirectory;

    public StorageConfiguration() throws IOException {
    }

    public String getLocation() {

        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
