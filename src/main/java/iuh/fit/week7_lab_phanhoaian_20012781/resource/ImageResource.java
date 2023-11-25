package iuh.fit.week7_lab_phanhoaian_20012781.resource;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;

@RestController
public class ImageResource {
    @GetMapping("/image/{folderName}/{imageName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String folderName, @PathVariable String imageName) throws IOException {
        Resource resource = new ClassPathResource("static/" + folderName + "/" + imageName);
        byte[] imageBytes = Files.readAllBytes(resource.getFile().toPath());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
}
