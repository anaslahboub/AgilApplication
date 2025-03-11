package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.entities.Status;

public record ProductBackLogRespDTO(
        Long id,
        String title,
        String description,
        Status status,
        Priorite priority
) {
    public static ProductBackLogRespDTO toProductBackLogRespDTO(ProductBacklog productBacklog) {
        return new ProductBackLogRespDTO(
                productBacklog.getId(),
                productBacklog.getTitle(),
                productBacklog.getDescription(),
                productBacklog.getStatus(),
                productBacklog.getPriority()
        );
    }
    public ProductBacklog toEntity() {
        ProductBacklog productBacklog = new ProductBacklog();
        productBacklog.setId(this.id());
        productBacklog.setTitle(this.title());
        productBacklog.setDescription(this.description());
        productBacklog.setStatus(this.status());
        productBacklog.setPriority(this.priority());
        return productBacklog;
    }
}
