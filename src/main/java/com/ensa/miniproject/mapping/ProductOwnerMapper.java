package com.ensa.miniproject.mapping;

import com.ensa.miniproject.DTO.ProductOwnerDTO;
import com.ensa.miniproject.entities.ProductBacklog;
import com.ensa.miniproject.entities.ProductOwner;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductOwnerMapper {

    ProductBacklogMapper INSTANCE = Mappers.getMapper(ProductBacklogMapper.class);

    ProductOwnerDTO fromEntity(ProductOwner productOwner);
    ProductOwner toEntity(ProductOwnerDTO productOwnerDTO);
}
