package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.ProductBacklogDTO;
import com.ensa.miniproject.DTO.ProductOwnerDTO;
import com.ensa.miniproject.entities.ProductBacklog;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductBacklogMapper {
    ProductBacklogDTO fromEntity(ProductBacklog productBacklog);
    ProductBacklog toEntity(ProductBacklogDTO productBacklogDTO);
}
