package mealmover.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@RestController
@RequestMapping("/api/files")
public class FileController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/uploads/**")
    public ResponseEntity<Resource> serveFile(HttpServletRequest request) {
        try {
            // Extract the file path after /api/files/uploads/
            String requestPath = request.getRequestURI();
            String prefix = request.getContextPath() + "/api/files/uploads/";

            if (!requestPath.startsWith(prefix)) {
                log.warn("Invalid request path: {}", requestPath);
                return ResponseEntity.badRequest().build();
            }

            String relativePath = requestPath.substring(prefix.length());

            // Resolve and normalize the path
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(relativePath).normalize();

            // Security check: ensure the resolved path is within upload directory
            if (!filePath.startsWith(uploadPath)) {
                log.warn("Attempted path traversal attack: {}", relativePath);
                return ResponseEntity.badRequest().build();
            }

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                log.warn("File not found or not readable: {}", filePath);
                return ResponseEntity.notFound().build();
            }

            // Determine content type
            MediaType contentType = determineContentType(filePath);

            // Build response
            ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.ok()
                    .contentType(contentType);

            // Add content-disposition header if needed
            if (shouldForceDownload(contentType)) {
                responseBuilder.header(
                    HttpHeaders.CONTENT_DISPOSITION,
        "attachment; filename=\"" + resource.getFilename() + "\""
                );
            } else {
                responseBuilder.header(
                    HttpHeaders.CONTENT_DISPOSITION,
                "inline; filename=\"" + resource.getFilename() + "\""
                );
            }

            // Add cache headers for better performance
            responseBuilder.header(HttpHeaders.CACHE_CONTROL, "max-age=3600");

            return responseBuilder.body(resource);

        } catch (MalformedURLException e) {
            log.error("Malformed URL error", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error serving file", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Path filePath = uploadPath.resolve(filename).normalize();

            // Security check
            if (!filePath.startsWith(uploadPath)) {
                return ResponseEntity.badRequest().build();
            }

            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            MediaType contentType = determineContentType(filePath);

            return ResponseEntity.ok()
                    .contentType(contentType)
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (Exception e) {
            log.error("Error downloading file: {}", filename, e);
            return ResponseEntity.internalServerError().build();
        }
    }

    private MediaType determineContentType(Path filePath) {
        try {
            // Try to detect content type using Files.probeContentType
            String contentType = Files.probeContentType(filePath);
            if (contentType != null) {
                return MediaType.parseMediaType(contentType);
            }
        } catch (IOException e) {
            log.warn("Could not determine content type for: {}", filePath, e);
        }

        // Fallback to manual detection based on extension
        String filename = filePath.getFileName().toString().toLowerCase();

        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else if (filename.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else if (filename.endsWith(".txt")) {
            return MediaType.TEXT_PLAIN;
        } else if (filename.endsWith(".html")) {
            return MediaType.TEXT_HTML;
        } else if (filename.endsWith(".json")) {
            return MediaType.APPLICATION_JSON;
        } else if (filename.endsWith(".xml")) {
            return MediaType.APPLICATION_XML;
        } else if (filename.endsWith(".zip")) {
            return new MediaType("application", "zip");
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    private boolean shouldForceDownload(MediaType contentType) {
        // Define which content types should be displayed inline
        return !contentType.equals(MediaType.IMAGE_JPEG) &&
            !contentType.equals(MediaType.IMAGE_PNG) &&
            !contentType.equals(MediaType.IMAGE_GIF) &&
            !contentType.equals(MediaType.APPLICATION_PDF) &&
            !contentType.equals(MediaType.TEXT_PLAIN) &&
            !contentType.equals(MediaType.TEXT_HTML);
    }
}