package com.ensa.miniproject.DTO;

import com.ensa.miniproject.entities.ProductOwner;

public record ProductOwnerCreateDTO(
        String username,
        String prenom,
        String password,
        String email
        ) {

        public ProductOwner toEntity() {
                ProductOwner productOwner = new ProductOwner(this.username, this.prenom, this.email);
                productOwner.setUsername(username);
                productOwner.setPassword(password);
                productOwner.setEmail(email);
                return productOwner;
        }
}
