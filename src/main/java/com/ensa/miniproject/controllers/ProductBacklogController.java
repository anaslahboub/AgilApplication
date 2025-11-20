package com.ensa.miniproject.controllers;

import com.ensa.miniproject.dto.ProductBacklogDTO;
import com.ensa.miniproject.services.productbacklog.ProductBacklogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/product-backlogs")
public class ProductBacklogController {

    private final ProductBacklogService productBacklogService;

    public ProductBacklogController(ProductBacklogService productBacklogService) {
        this.productBacklogService = productBacklogService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<ProductBacklogDTO> createProductBacklog(@RequestBody ProductBacklogDTO productBacklogDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productBacklogService.addProductBacklog(productBacklogDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<List<ProductBacklogDTO>> getAllProductBacklogs() {
        return ResponseEntity.ok(productBacklogService.getProductBacklogs());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DEVELOPER', 'SCRUM_MASTER', 'PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<ProductBacklogDTO> getProductBacklogById(@PathVariable Long id) {
        return ResponseEntity.ok(productBacklogService.getProductBacklogById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<ProductBacklogDTO> updateProductBacklog(
            @PathVariable Long id,
            @RequestBody ProductBacklogDTO productBacklogDTO) {
        return ResponseEntity.ok(productBacklogService.updateProductBacklog(productBacklogDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProductBacklog(@PathVariable Long id) {
        productBacklogService.deleteProductBacklog(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{productBacklogId}/epics")
    @PreAuthorize("hasAnyRole('PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<ProductBacklogDTO> addEpicsToProductBacklog(
            @PathVariable Long productBacklogId,
            @RequestBody List<Long> epicIds) {
        return ResponseEntity.ok(productBacklogService.addEpicsToProductBacklog(productBacklogId, epicIds));
    }

    @DeleteMapping("/{productBacklogId}/epics/{epicId}")
    @PreAuthorize("hasAnyRole('PRODUCT_OWNER', 'ADMIN')")
    public ResponseEntity<ProductBacklogDTO> removeEpicFromProductBacklog(
            @PathVariable Long productBacklogId,
            @PathVariable Long epicId) {
        return ResponseEntity.ok(productBacklogService.removeEpicFromProductBacklog(productBacklogId, epicId));
    }
}