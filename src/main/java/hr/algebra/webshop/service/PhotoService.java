package hr.algebra.webshop.service;

import hr.algebra.webshop.entity.Photo;
import hr.algebra.webshop.repository.PhotoRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {
    public Photo addPhoto(String title, MultipartFile file) throws IOException;
    public Photo getPhoto(String id);
}
