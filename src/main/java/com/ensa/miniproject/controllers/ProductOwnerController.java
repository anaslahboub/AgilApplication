package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.ProductOwnerDTO;
import com.ensa.miniproject.services.productOwner.ProductOwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-owners")
public class ProductOwnerController {

    private final ProductOwnerService productOwnerService;

    public ProductOwnerController(ProductOwnerService productOwnerService) {
        this.productOwnerService = productOwnerService;
    }

    @PostMapping
    public ResponseEntity<ProductOwnerDTO> createProductOwner(@RequestBody ProductOwnerDTO productOwnerDTO) {
        ProductOwnerDTO newProductOwner = productOwnerService.addProductOwner(productOwnerDTO);
        return new ResponseEntity<>(newProductOwner, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductOwnerDTO>> getAllProductOwners() {
        List<ProductOwnerDTO> productOwners = productOwnerService.getProductOwners();
        return new ResponseEntity<>(productOwners, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductOwnerDTO> getProductOwnerById(@PathVariable Long id) {
        try {
            ProductOwnerDTO productOwnerDTO = productOwnerService.getProductOwnerById(id);
            return ResponseEntity.ok(productOwnerDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductOwnerDTO> updateProductOwner(@PathVariable Long id,
                                                              @RequestBody ProductOwnerDTO productOwnerDTO) {
        ProductOwnerDTO updatedProductOwner = productOwnerService.updateProductOwner(productOwnerDTO);
        return new ResponseEntity<>(updatedProductOwner, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductOwner(@PathVariable Long id) {
        productOwnerService.deleteProductOwner(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}