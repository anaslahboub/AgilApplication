package com.ensa.miniproject.mapping;

import com.ensa.miniproject.dto.ProductOwnerDTO;
import com.ensa.miniproject.entities.ProductOwner;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductOwnerMapper {
    ProductOwnerDTO fromEntity(ProductOwner productOwner);
    ProductOwner toEntity(ProductOwnerDTO productOwnerDTO);
}
