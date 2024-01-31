package hr.algebra.webshop.serviceImplementation;

import hr.algebra.webshop.Exception.ResourceNotFoundException;
import hr.algebra.webshop.dto.PhotoDTO;
import hr.algebra.webshop.dto.ProductDTO;
import hr.algebra.webshop.entity.Photo;
import hr.algebra.webshop.mapper.PhotoMapper;
import hr.algebra.webshop.repository.PhotoRepository;
import hr.algebra.webshop.service.PhotoService;
import hr.algebra.webshop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
@AllArgsConstructor
public class PhotoServiceImpl implements PhotoService {
    public  PhotoRepository photoRepository;
    public ProductService productService;
    @Override
    public void addPhoto(PhotoDTO photoDTO) throws IOException {
        photoRepository.save(PhotoMapper.mapToPhoto(photoDTO));
    }

    @Override
    public Photo getPhoto(String id) {
        return  photoRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Photo does not exist with a given id"));

    }

    @Override
    public void deletePhoto(String productId) {
        ProductDTO productById = productService.getProductById(productId);
        photoRepository.deleteById(productById.getProductPhotos().getId());

    }

    @Override
    public void updatePhoto(String productId, PhotoDTO photoDTO) {
       String photoID= productService.getProductById(productId).get_id();
        Photo photo = photoRepository.findById(photoID).orElseThrow(()
                -> new ResourceNotFoundException("Product does not exist with a given id"));
        photo.setTitle(photoDTO.getTitle());
        photo.setImage(photoDTO.getImage());
        photo.setContentType(photoDTO.getContentType());
        photo.setOriginalFileName(photoDTO.getOriginalFileName());
        photoRepository.save(photo);

    }
}
