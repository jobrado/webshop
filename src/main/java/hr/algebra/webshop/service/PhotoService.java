package hr.algebra.webshop.service;

import hr.algebra.webshop.dto.PhotoDTO;
import hr.algebra.webshop.entity.Photo;

import java.io.IOException;

public interface PhotoService {
     void addPhoto(PhotoDTO photoDTO) throws IOException;
     Photo getPhoto(String id);
     void deletePhoto(String productId);
     void updatePhoto(String productId, PhotoDTO photoDTO);
}
