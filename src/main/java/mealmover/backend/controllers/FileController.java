package mealmover.backend.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller("/api/files")
public class FileController {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @GetMapping("/**")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(HttpServletRequest request) throws MalformedURLException {
        String requestPath = request.getRequestURI();
        String contextPath = request.getContextPath();
        String uploadPathPrefix = contextPath + "/uploads/";

        String relativePath = requestPath.substring(uploadPathPrefix.length());

        Path filePath = Paths.get(uploadDir).resolve(relativePath).normalize();
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists()) {
            MediaType contentType = determineContentType(filePath.toString());

            HttpHeaders headers = new HttpHeaders();
            if (shouldForceDownload(contentType)) {
                headers.add(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + resource.getFilename() + "\""
                );
            }

            return ResponseEntity.ok()
                    .contentType(contentType)
                    .headers(headers)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Helper method to determine content type
    private MediaType determineContentType(String filename) {
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (filename.endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (filename.endsWith(".pdf")) {
            return MediaType.APPLICATION_PDF;
        } else {
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }

    // Helper method to decide if file should be forced to download
    private boolean shouldForceDownload(MediaType contentType) {
        // Images and PDFs typically open in browser, other types download
        return !contentType.equals(MediaType.IMAGE_JPEG) &&
                !contentType.equals(MediaType.IMAGE_PNG) &&
                !contentType.equals(MediaType.APPLICATION_PDF);
    }
}