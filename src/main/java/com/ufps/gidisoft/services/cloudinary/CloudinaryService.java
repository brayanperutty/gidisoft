package com.ufps.gidisoft.services.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.api.ApiResponse;
import com.cloudinary.api.exceptions.ApiException;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    private static final Logger log = LoggerFactory.getLogger(CloudinaryService.class);

    public String upload(MultipartFile file, String folder) throws IOException {
        File newFile = convert(file);
        try {
            return cloudinary.uploader()
                    .upload(newFile, ObjectUtils.asMap("folder", folder))
                    .get("url").toString();
        } finally {
            try {
                Files.delete(newFile.toPath());
            } catch (IOException e) {
                log.warn("No se pudo eliminar el archivo temporal: {}", newFile.getAbsolutePath(), e);
            }
        }
    }

    public String delete(String id) throws IOException{
        return cloudinary.uploader().destroy(id, ObjectUtils.emptyMap()).toString();
    }

    public void getImage(String url) throws Exception {
        ApiResponse resources = cloudinary.api().resources(ObjectUtils.asMap("type", "upload"));
        Object resourceObj = resources.get("resources");

        if (!(resourceObj instanceof List<?> resourceList)) {
            throw new IllegalStateException("No se pudo obtener la lista de recursos.");
        }

        resourceList.stream()
                .filter(item -> item instanceof Map<?, ?> map &&
                        url.equals(String.valueOf(map.get("url"))))
                .findFirst()
                .ifPresent(item -> {
                    String publicId = String.valueOf(((Map<?, ?>) item).get("public_id"));
                    try {
                        delete(publicId);
                    } catch (IOException e) {
                        throw new IllegalArgumentException(e.getMessage());
                    }
                });
    }

    private File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fo = new FileOutputStream(file)) {
            fo.write(multipartFile.getBytes());
        }
        return file;
    }
}