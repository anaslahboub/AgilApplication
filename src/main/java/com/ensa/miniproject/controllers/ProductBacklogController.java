package com.ensa.miniproject.controllers;

import com.ensa.miniproject.DTO.ProductBacklogDTO;
import com.ensa.miniproject.services.productBacklog.ProductBacklogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ProductBacklogDTO> createProductBacklog(@RequestBody ProductBacklogDTO productBacklogDTO) {
        ProductBacklogDTO newBacklog = productBacklogService.addProductBacklog(productBacklogDTO);
        return new ResponseEntity<>(newBacklog, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductBacklogDTO>> getAllProductBacklogs() {
        List<ProductBacklogDTO> backlogs = productBacklogService.getProductBacklogs();
        return new ResponseEntity<>(backlogs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductBacklogDTO> getProductBacklogById(@PathVariable Long id) {
        try {
            ProductBacklogDTO backlogDTO = productBacklogService.getProductBacklogById(id);
            return ResponseEntity.ok(backlogDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductBacklogDTO> updateProductBacklog(@PathVariable Long id,
                                                                  @RequestBody ProductBacklogDTO productBacklogDTO) {
        ProductBacklogDTO updatedBacklog = productBacklogService.updateProductBacklog(productBacklogDTO);
        return new ResponseEntity<>(updatedBacklog, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductBacklog(@PathVariable Long id) {
        productBacklogService.deleteProductBacklog(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    /// //////////////////////
    /// /////METIER///////////
    /// /////////////////////
    @PostMapping("/{productBacklogId}/epics")
    public ResponseEntity<ProductBacklogDTO> addEpicsToProductBacklog(
            @PathVariable Long productBacklogId,
            @RequestBody List<Long> epicIds) {
        ProductBacklogDTO updatedBacklog = productBacklogService.addEpicsToProductBacklog(productBacklogId, epicIds);
        return ResponseEntity.ok(updatedBacklog);
    }

    @DeleteMapping("/{productBacklogId}/epics/{epicId}")
    public ResponseEntity<ProductBacklogDTO> removeEpicFromProductBacklog(
            @PathVariable Long productBacklogId,
            @PathVariable Long epicId) {
        ProductBacklogDTO updatedBacklog = productBacklogService.removeEpicFromProductBacklog(productBacklogId, epicId);
        return ResponseEntity.ok(updatedBacklog);
    }
}