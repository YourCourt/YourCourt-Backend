package yourcourt.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yourcourt.model.Image;
import yourcourt.repository.ImageRepository;

@Service
public class ImageService {
  @Autowired
  private ImageRepository imageRepository;

  public Optional<Image> findById(long id) {
    return imageRepository.findById(id);
  }

  public boolean existsById(long id) {
    return imageRepository.existsById(id);
  }

  public Image save(Image image) {
    Image newImage = null;
    try {
      newImage = imageRepository.save(image);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return newImage;
  }

  public void deleteById(long id) {
    imageRepository.deleteById(id);
  }
}
