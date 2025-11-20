package com.ensa.miniproject.controllers;

import com.ensa.miniproject.dto.ProductOwnerDTO;
import com.ensa.miniproject.services.productowner.ProductOwnerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductOwnerDTO> createProductOwner(@RequestBody ProductOwnerDTO productOwnerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productOwnerService.addProductOwner(productOwnerDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SCRUM_MASTER', 'PRODUCT_OWNER')")
    public ResponseEntity<List<ProductOwnerDTO>> getAllProductOwners() {
        return ResponseEntity.ok(productOwnerService.getProductOwners());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SCRUM_MASTER', 'PRODUCT_OWNER')")
    public ResponseEntity<ProductOwnerDTO> getProductOwnerById(@PathVariable Long id) {
        return ResponseEntity.ok(productOwnerService.getProductOwnerById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'PRODUCT_OWNER')")
    public ResponseEntity<ProductOwnerDTO> updateProductOwner(
            @PathVariable Long id,
            @RequestBody ProductOwnerDTO productOwnerDTO) {
        return ResponseEntity.ok(productOwnerService.updateProductOwner(productOwnerDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProductOwner(@PathVariable Long id) {
        productOwnerService.deleteProductOwner(id);
        return ResponseEntity.noContent().build();
    }
}