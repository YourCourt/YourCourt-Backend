package yourcourt.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import yourcourt.exceptions.user.InexistentEntity;
import yourcourt.model.Product;
import yourcourt.model.ProductPurchase;
import yourcourt.model.ProductPurchaseLine;
import yourcourt.model.ValidationUtils;
import yourcourt.model.dto.Message;
import yourcourt.model.dto.ProductPurchaseDto;
import yourcourt.model.dto.ProductPurchaseLineDto;
import yourcourt.model.projections.ProductPurchaseProjection;
import yourcourt.security.model.User;
import yourcourt.security.service.UserService;
import yourcourt.service.ProductPurchaseService;
import yourcourt.service.ProductService;

@RestController
@Api(tags = "ProductPurchase")
@RequestMapping("/purchases")
@CrossOrigin
public class ProductPurchaseController {
  @Autowired
  private ProductPurchaseService productPurchaseService;

  @Autowired
  private UserService userService;

  @Autowired
  private ProductService productService;

  @GetMapping
  public ResponseEntity<?> getAllProductPurchases() {
    return new ResponseEntity<>(productPurchaseService.findAllProductPurchases(), HttpStatus.OK);
  }

  // @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
  @GetMapping("/{id}")
  public ResponseEntity<?> getProductPurchase(@PathVariable("id") Long id) {
    try {
      ProductPurchaseProjection productPurchase = productPurchaseService.findProductPurchaseById(id);

      return new ResponseEntity<>(productPurchase, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/user")
  public ResponseEntity<?> getProductPurchasesByUser(@RequestParam("username") String username) {

    try {
      Iterable<ProductPurchaseProjection> productPurchases = productPurchaseService
          .findProductPurchasesFromUser(username);

      return new ResponseEntity<>(productPurchases, HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping
  public ResponseEntity<?> createProductPurchase(@Valid @RequestBody ProductPurchaseDto productPurchaseDto,
      BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {

      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ValidationUtils.validateDto(bindingResult));
    }
    try {
      // ProductPurchase
      LocalDate creationDate = LocalDate.now();
      ProductPurchase newProductPurchase = new ProductPurchase(creationDate);
      User user = userService.findUserById(productPurchaseDto.getUser());
      newProductPurchase.setUser(user);

      ProductPurchase productPurchaseCreated = productPurchaseService.saveProductPurchase(newProductPurchase);

      List<ProductPurchaseLine> purchaseLines = new ArrayList<ProductPurchaseLine>();
      // ProductPurchaseLine
      for (ProductPurchaseLineDto line : productPurchaseDto.getLines()) { // Update stock before creating purchase

        ProductPurchaseLine productPurchaseLine = new ProductPurchaseLine(line.getQuantity(), line.getDiscount());

        Long productId = line.getProductId();
        Product product = productService.findProductById(productId);
        Integer quantity = line.getQuantity();
        Integer currentStock = product.getStock();
        if (currentStock < quantity) { // Check whether the product's stock is enough or not
          return ResponseEntity.status(HttpStatus.BAD_REQUEST)
              .body(ValidationUtils.throwError("cantidad", "La cantidad debe ser menor o igual al stock disponible"));
        }
        product.setStock(currentStock - quantity);
        productService.saveProduct(product);

        productPurchaseLine.setProduct(product);
        productPurchaseLine.setProductPurchase(productPurchaseCreated);
        ProductPurchaseLine newProductPurchaseLine = productPurchaseService.saveProductBookingLine(productPurchaseLine);
        purchaseLines.add(newProductPurchaseLine);
      }

      productPurchaseCreated.setLines(purchaseLines);

      ProductPurchase productPurchaseFinal = productPurchaseService.saveProductPurchase(productPurchaseCreated);

      return new ResponseEntity<>(productPurchaseService.findProductPurchaseById(productPurchaseFinal.getId()),
          HttpStatus.CREATED);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteProductPurchase(@PathVariable("id") Long id) {
    try {
      productPurchaseService.deleteProductPurchaseById(id);
      return new ResponseEntity<>(new Message("Compra eliminada"), HttpStatus.OK);
    } catch (InexistentEntity e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(new Message(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
  }
}
