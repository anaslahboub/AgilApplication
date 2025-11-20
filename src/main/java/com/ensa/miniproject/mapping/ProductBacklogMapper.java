package com.ensa.miniproject.mapping;

import com.ensa.miniproject.dto.ProductBacklogDTO;
import com.ensa.miniproject.entities.ProductBacklog;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductBacklogMapper {
    ProductBacklogDTO fromEntity(ProductBacklog productBacklog);
    ProductBacklog toEntity(ProductBacklogDTO productBacklogDTO);
}
