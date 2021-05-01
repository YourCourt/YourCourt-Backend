package yourcourt.controller;

import io.swagger.annotations.Api;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.Image;
import yourcourt.model.Product;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.Message;
import yourcourt.model.dto.ProductDto;
import yourcourt.service.ImageService;
import yourcourt.service.ProductService;

@RestController
@Api(tags = "Product")
@RequestMapping("/products")
@CrossOrigin
public class ProductController {
  @Autowired
  private ProductService productService;

  @Autowired
  private ImageService imageService;

  @GetMapping
  public ResponseEntity<?> getAllProducts() {
    return new ResponseEntity<>(productService.findAllProducts(), HttpStatus.OK);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getProduct(@PathVariable("id") Long id) {
    try {
      Product product = productService.findProductById(id);
      return new ResponseEntity<>(product, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping
  public ResponseEntity<?> createProduct(
    @Valid @RequestBody ProductDto productDto,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ValidationUtils.validateDto(bindingResult));
    }

    Product newProduct = new Product();
    BeanUtils.copyProperties(productDto, newProduct);

    Optional<Image> defaultImage = imageService.findById(1);
    if (!defaultImage.isPresent()) {
      return ResponseEntity
        .status(HttpStatus.NOT_FOUND)
        .body(new Message("La imagen seleccionada no ha sido encontrada."));
    }
    newProduct.setImage(defaultImage.get());

    Product productCreated = productService.saveProduct(newProduct);

    return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Object> updateProduct(
    @PathVariable Long id,
    @RequestBody ProductDto productDto,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(ValidationUtils.validateDto(bindingResult));
    }

    try {
      Product productToUpdate = productService.findProductById(id);
      Product productUpdated = productService.updateProduct(productToUpdate, productDto);

      return new ResponseEntity<>(productUpdated, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
    try {
      productService.deleteProductById(id);
      return new ResponseEntity<>(new Message("Producto eliminado"), HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
