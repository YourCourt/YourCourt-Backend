package yourcourt.controller;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.Court;
import yourcourt.model.Facility;
import yourcourt.model.Image;
import yourcourt.model.News;
import yourcourt.model.Product;
import yourcourt.model.dto.Message;
import yourcourt.security.model.User;
import yourcourt.security.service.UserService;
import yourcourt.service.CloudinaryService;
import yourcourt.service.CourtService;
import yourcourt.service.FacilityService;
import yourcourt.service.ImageService;
import yourcourt.service.NewsService;
import yourcourt.service.ProductService;

@RestController
@Api(tags = "Image")
@RequestMapping("/image")
@CrossOrigin
public class ImageController {
  private static final String IMAGEN_NO_VALIDA = "No es una imagen valida";
  private static final String IMAGEN_SUBIDA_STRING =
    "La imagen se ha subido correctamente";
  private static final String IMAGEN_ELIMINADA_STRING =
    "La imagen se ha eliminado correctamente";
  private static final String IMAGEN_NO_ENCONTRADA_STRING =
		    "La imagen seleccionada no ha sido encontrada";
  private static final String IMAGEN_INEXISTENTE_STRING =
		    "La imagen era inexistente";
  
  private static final int DEFAULT_IMAGE =1;
  @Autowired
  private CloudinaryService cloudinaryService;

  @Autowired
  private ImageService imageService;

  @Autowired
  private UserService userService;
  
  @Autowired
  private CourtService courtService;
  
  @Autowired
  private FacilityService facilityService;
  
  @Autowired
  private NewsService newsService;
  
  @Autowired
  private ProductService productService;

  
  @PostMapping("/user/{userId}")
  public ResponseEntity<?> newUserImage(
    @PathVariable("userId") Long userId,
    @RequestParam MultipartFile multipartFile
  )
    throws IOException {
	  try {
		  User user = userService.findUserById(userId);
    if (ImageIO.read(multipartFile.getInputStream()) == null) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new Message(IMAGEN_NO_VALIDA));
    }
    Map<?, ?> result = cloudinaryService.upload(multipartFile);

    Image image = new Image(
      (String) result.get("original_filename"),
      (String) result.get("url"),
      (String) result.get("public_id")
    );
    Image createdImage = imageService.save(image);
    
    Image currentImage = user.getImage();
    user.setImage(createdImage);
    userService.saveUser(user);

    if (
      currentImage != null &&
      currentImage.getCloudinaryId() != null &&
      currentImage.getId() != DEFAULT_IMAGE
    ) {
      cloudinaryService.delete(currentImage.getCloudinaryId());
    }
    if (currentImage != null && currentImage.getId() != DEFAULT_IMAGE) {
      imageService.deleteById(currentImage.getId());
    }

    return ResponseEntity.ok(new Message(IMAGEN_SUBIDA_STRING));
  } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
  
  @DeleteMapping("/user/{userId}")
  public ResponseEntity<?> deleteUserImage(@PathVariable("userId") Long userId)
    throws IOException {
    try {
      User user = userService.findUserById(userId);

      Image currentImage = user.getImage();

      Optional<Image> defaultImage = imageService.findById(DEFAULT_IMAGE);
      if (!defaultImage.isPresent()) {
        return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(new Message(IMAGEN_NO_ENCONTRADA_STRING));
      }
      user.setImage(defaultImage.get());
      userService.saveUser(user);

      if (currentImage.getCloudinaryId() != null && currentImage.getId() != DEFAULT_IMAGE) {
        cloudinaryService.delete(currentImage.getCloudinaryId());
      }
      if (currentImage.getId() != DEFAULT_IMAGE) {
        imageService.deleteById(currentImage.getId());
      } else {
        return ResponseEntity
          .status(HttpStatus.NOT_FOUND)
          .body(new Message(IMAGEN_INEXISTENTE_STRING));
      }

      return ResponseEntity.ok(new Message(IMAGEN_ELIMINADA_STRING));
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/court/{courtId}")
  public ResponseEntity<?> newCourtImage(
    @PathVariable("courtId") Long courtId,
    @RequestParam MultipartFile multipartFile
  )
    throws IOException {
	  try {
		    Court court = courtService.findCourtById(courtId);
    if (ImageIO.read(multipartFile.getInputStream()) == null) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(new Message(IMAGEN_NO_VALIDA));
    }
    Map<?, ?> result = cloudinaryService.upload(multipartFile);

    Image image = new Image(
      (String) result.get("original_filename"),
      (String) result.get("url"),
      (String) result.get("public_id")
    );
    Image createdImage = imageService.save(image);
    
    Image currentImage = court.getImage();
    court.setImage(createdImage);
    courtService.saveCourt(court);

    if (
      currentImage != null &&
      currentImage.getCloudinaryId() != null &&
      currentImage.getId() != DEFAULT_IMAGE
    ) {
      cloudinaryService.delete(currentImage.getCloudinaryId());
    }
    if (currentImage != null && currentImage.getId() != DEFAULT_IMAGE) {
      imageService.deleteById(currentImage.getId());
    }

    return ResponseEntity.ok(new Message(IMAGEN_SUBIDA_STRING));
  } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
  
  @PostMapping("/facility/{facilityId}")
  public ResponseEntity<?> newFacilityImage(
		    @PathVariable("facilityId") Long facilityId,
		    @RequestParam MultipartFile multipartFile
		  )
		    throws IOException {
			  try {
				  Facility facility = facilityService.findFacilityById(facilityId);
		    if (ImageIO.read(multipartFile.getInputStream()) == null) {
		      return ResponseEntity
		        .status(HttpStatus.BAD_REQUEST)
		        .body(new Message(IMAGEN_NO_VALIDA));
		    }
		    Map<?, ?> result = cloudinaryService.upload(multipartFile);

		    Image image = new Image(
		      (String) result.get("original_filename"),
		      (String) result.get("url"),
		      (String) result.get("public_id")
		    );
		    Image createdImage = imageService.save(image);
		    
		    Image currentImage = facility.getImage();
		    facility.setImage(createdImage);
		    facilityService.saveFacility(facility);

		    if (
		      currentImage != null &&
		      currentImage.getCloudinaryId() != null &&
		      currentImage.getId() != DEFAULT_IMAGE
		    ) {
		      cloudinaryService.delete(currentImage.getCloudinaryId());
		    }
		    if (currentImage != null && currentImage.getId() != DEFAULT_IMAGE) {
		      imageService.deleteById(currentImage.getId());
		    }

		    return ResponseEntity.ok(new Message(IMAGEN_SUBIDA_STRING));
		  } catch (InexistentEntity e) {
		      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		    }
		  }
  
  @PostMapping("/news/{newsId}")  
  public ResponseEntity<?> newNewsImage(
		    @PathVariable("newsId") Long newsId,
		    @RequestParam MultipartFile multipartFile
		  )
		    throws IOException {
			  try {
				  News news = newsService.findNewsById(newsId);
		    if (ImageIO.read(multipartFile.getInputStream()) == null) {
		      return ResponseEntity
		        .status(HttpStatus.BAD_REQUEST)
		        .body(new Message(IMAGEN_NO_VALIDA));
		    }
		    Map<?, ?> result = cloudinaryService.upload(multipartFile);

		    Image image = new Image(
		      (String) result.get("original_filename"),
		      (String) result.get("url"),
		      (String) result.get("public_id")
		    );
		    Image createdImage = imageService.save(image);
		    
		    Image currentImage = news.getImage();
		    news.setImage(createdImage);
		    newsService.saveNews(news);

		    if (
		      currentImage != null &&
		      currentImage.getCloudinaryId() != null &&
		      currentImage.getId() != DEFAULT_IMAGE
		    ) {
		      cloudinaryService.delete(currentImage.getCloudinaryId());
		    }
		    if (currentImage != null && currentImage.getId() != DEFAULT_IMAGE) {
		      imageService.deleteById(currentImage.getId());
		    }

		    return ResponseEntity.ok(new Message(IMAGEN_SUBIDA_STRING));
		  } catch (InexistentEntity e) {
		      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		    }
		  }
  

  @PostMapping("/product/{productId}")  
  public ResponseEntity<?> newProductImage(
		    @PathVariable("productId") Long productId,
		    @RequestParam MultipartFile multipartFile
		  )
		    throws IOException {
			  try {
				  Product product = productService.findProductById(productId);
		    if (ImageIO.read(multipartFile.getInputStream()) == null) {
		      return ResponseEntity
		        .status(HttpStatus.BAD_REQUEST)
		        .body(new Message(IMAGEN_NO_VALIDA));
		    }
		    Map<?, ?> result = cloudinaryService.upload(multipartFile);

		    Image image = new Image(
		      (String) result.get("original_filename"),
		      (String) result.get("url"),
		      (String) result.get("public_id")
		    );
		    Image createdImage = imageService.save(image);
		    
		    Image currentImage = product.getImage();
		    product.setImage(createdImage);
		    productService.saveProduct(product);

		    if (
		      currentImage != null &&
		      currentImage.getCloudinaryId() != null &&
		      currentImage.getId() != DEFAULT_IMAGE
		    ) {
		      cloudinaryService.delete(currentImage.getCloudinaryId());
		    }
		    if (currentImage != null && currentImage.getId() != DEFAULT_IMAGE) {
		      imageService.deleteById(currentImage.getId());
		    }

		    return ResponseEntity.ok(new Message(IMAGEN_SUBIDA_STRING));
		  } catch (InexistentEntity e) {
		      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
		    } catch (Exception e) {
		      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
		    }
		  }
	
}
