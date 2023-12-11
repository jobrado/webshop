package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.entity.Photo;
import hr.algebra.webshop.repository.PhotoRepository;
import hr.algebra.webshop.service.PhotoService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    public  PhotoRepository photoRepository;
    @Override
    public Photo addPhoto(String title, MultipartFile file) throws IOException {
        Photo photo = new Photo(title);
        photo.setImage(
                new Binary(BsonBinarySubType.BINARY, file.getBytes()));
        photo = photoRepository.insert(photo);
        return photo;
    }

    @Override
    public Photo getPhoto(String id) {
        return photoRepository.findById(id).get();
    }
}
