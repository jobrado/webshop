package hr.algebra.webshop.mapper;

import hr.algebra.webshop.dto.PhotoDTO;
import hr.algebra.webshop.entity.Photo;

public class PhotoMapper {
    public static PhotoDTO mapToPhotoDTO (Photo photo) {
        return new PhotoDTO(
                photo.getId(),
                photo.getTitle(),
                photo.getImage()
        );
    }
    public static Photo mapToPhoto(PhotoDTO photo) {
        return new Photo(
                photo.getId(),
                photo.getTitle(),
                photo.getImage()
        );
    }
}
