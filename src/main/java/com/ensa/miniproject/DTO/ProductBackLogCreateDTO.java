package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.Priorite;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.entities.Status;


public record ProductBackLogCreateDTO(
         String title,
         String description,
         Status status,
         Priorite priority
) {
    public ProductBacklog toEntity() {
        ProductBacklog backlog = new ProductBacklog();
        backlog.setTitle(title);
        backlog.setDescription(description);
        backlog.setStatus(status);
        backlog.setPriority(priority);
        return backlog;
    }


}
