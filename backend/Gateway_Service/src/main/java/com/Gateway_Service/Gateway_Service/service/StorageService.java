package com.Gateway_Service.Gateway_Service.service;

import com.Gateway_Service.Gateway_Service.config.StorageConfiguration;
import com.Gateway_Service.Gateway_Service.exception.GatewayException;
import com.Import_Service.Import_Service.service.ImportServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * This might need to be converted to an actual service
 * @author Shrey Mandalia
 */

@Service
public class StorageService {

    private final Path rootLocation;

    private static final Logger log = LoggerFactory.getLogger(StorageService.class);

    @Autowired
    public StorageService(StorageConfiguration config) {
        if(config == null) {
            log.warn("Configuration properties is null");
        }
        else {
            log.info("Configuration location: " + config.getLocation());
        }
        this.rootLocation = Paths.get(config.getLocation());
    }

    public void store(MultipartFile file) throws GatewayException {
        log.info("[Storage] Attempting to save file");
        try {
            if (file.isEmpty()) {
                log.error("[Storage] The file is empty");
                throw new GatewayException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                log.error("[Storage] Cannot store file outside current directory");
                throw new GatewayException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
                log.info("[Storage] Successfully saved file");
            }
        }
        catch (IOException | GatewayException e) {
            e.printStackTrace();
            throw new GatewayException("Failed to store file.");
        }
    }


}
