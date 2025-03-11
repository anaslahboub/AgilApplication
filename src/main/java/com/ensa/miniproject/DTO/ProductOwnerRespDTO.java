package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.ProductOwner;

public record ProductOwnerRespDTO(
        Long id,
        String username,
        String prenom,
        String email
) {

    public static ProductOwnerRespDTO toProductOwnerRespDTO(ProductOwner productOwner) {
        return new ProductOwnerRespDTO(
                productOwner.getId(),
                productOwner.getUsername(),
                productOwner.getPrenom(),
                productOwner.getEmail()
        );
    }

    public ProductOwner toEntity() {
        ProductOwner productOwner =  new ProductOwner();
        productOwner.setId(id);
        productOwner.setUsername(username);
        productOwner.setPrenom(prenom);
        productOwner.setEmail(email);
        return productOwner;
    }

}
