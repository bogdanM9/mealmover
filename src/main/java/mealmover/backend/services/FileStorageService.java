package mealmover.backend.services;

import mealmover.backend.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileStorageService {
    private final Path imageStoreLocation;
    public  FileStorageService(@Value("${file.upload-dir}") String fileStoragePath) {
        try {
            Path fileStorageLocation = Paths.get(fileStoragePath).toAbsolutePath().normalize();
            this.imageStoreLocation = fileStorageLocation.resolve("images");
            Files.createDirectories(this.imageStoreLocation);
        }
        catch(IOException e) {
            throw new FileStorageException("Could not create the directories: " + e.getMessage());
        }
    }

    public String storeImage(MultipartFile file, String subDirectory, boolean returnFullPath) {
        try {
            String originalFileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

            String fileName = UUID.randomUUID() + "_" + originalFileName;

            Path dir = this.imageStoreLocation.resolve(subDirectory).normalize();

            Files.createDirectories(dir);

            Path targetLocation = dir.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            if(returnFullPath) {
                return targetLocation.toString();
            }

            return subDirectory.isEmpty() ? fileName : subDirectory + "/" + fileName;

        } catch (IOException e) {
            throw new FileStorageException(
                "Could not store image " + file.getOriginalFilename() + ", please try again!"
            );
        }
    }
}
