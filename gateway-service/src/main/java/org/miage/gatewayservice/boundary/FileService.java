package org.miage.gatewayservice.boundary;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class FileService {

    private final Path fileStorageLocation = Paths
            .get(System.getenv("HOME") + "/.episodes/").toAbsolutePath().normalize();

    public FileService() throws IOException {
        Files.createDirectories(fileStorageLocation);
    }

    public URL storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path targetLocation = this.fileStorageLocation.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, REPLACE_EXISTING);
        return targetLocation.getFileName().toUri().toURL();
    }
}